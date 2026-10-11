# 07 — Recipe: add a CRUD feature end-to-end

Worked example: a **Product** list in the `maku-boot-new` module (the module meant for your business code). Model every file on the `sys_post` feature — it is the simplest full slice in the codebase.

> Shortcut: the built-in code generator (`maku-module-generator`, UI under the "Online development / Code generator" menu) can produce all of this from a table. Still read this once so you understand what it generates.

## 0. Checklist

| # | Layer | File | Copy from |
|---|---|---|---|
| 1 | Table | SQL migration (keep it in `db/`) | `sys_post` DDL |
| 2 | Entity | `net/maku/module/entity/ProductEntity.java` | `SysPostEntity` |
| 3 | DAO | `.../dao/ProductDao.java` (+ XML only if you need joins) | `SysPostDao` |
| 4 | Query | `.../query/ProductQuery.java extends Query` | `SysPostQuery` |
| 5 | VO | `.../vo/ProductVO.java` | `SysPostVO` |
| 6 | Service | `.../service/ProductService.java` + `impl/ProductServiceImpl.java` | `SysPostServiceImpl` |
| 7 | Controller | `.../controller/ProductController.java` | `SysPostController` |
| 8 | Menu + perms | `INSERT INTO sys_menu …` (menu + buttons) | `maku.sql` menu rows |
| 9 | API (FE) | `src/api/biz/product.ts` | `src/api/sys/post.ts` |
| 10 | Pages (FE) | `src/views/biz/product/index.vue` + `add-or-update.vue` | `src/views/sys/post/*` |
| 11 | Grant | Role Management → tick the new menu/buttons | — |

## 1. Table — include the standard columns

```sql
CREATE TABLE biz_product (
  id          bigint NOT NULL AUTO_INCREMENT,
  name        varchar(100) COMMENT 'Name',
  price       decimal(10,2) COMMENT 'Price',
  status      tinyint COMMENT 'Status  0: disabled  1: enabled',
  org_id      bigint COMMENT 'Org (for data scope)',
  version     int, deleted tinyint,
  creator     bigint, create_time datetime,
  updater     bigint, update_time datetime,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Product';
```

## 2–3. Entity + DAO

```java
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("biz_product")
public class ProductEntity extends BaseEntity {      // id, creator, createTime, updater, updateTime, version, deleted
    private String name;
    private BigDecimal price;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private Long orgId;                                // auto-filled from the current user
}

@Mapper
public interface ProductDao extends BaseDao<ProductEntity> { }
```

## 4–6. Query, VO, Service

```java
@Data @EqualsAndHashCode(callSuper = false)
public class ProductQuery extends Query { private String name; private Integer status; }

@Service
@AllArgsConstructor
public class ProductServiceImpl extends BaseServiceImpl<ProductDao, ProductEntity> implements ProductService {
    @Override
    public PageResult<ProductVO> page(ProductQuery query) {
        LambdaQueryWrapper<ProductEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getName()), ProductEntity::getName, query.getName());
        wrapper.eq(query.getStatus() != null, ProductEntity::getStatus, query.getStatus());
        dataScopeWrapper(wrapper);                     // ← opt into row-level security (org_id / creator)

        IPage<ProductEntity> page = baseMapper.selectPage(getPage(query), wrapper);
        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), ProductVO.class), page.getTotal());
    }

    @Override
    public void save(ProductVO vo) {
        if (vo.getPrice().signum() < 0) throw new ServerException("Price must be ≥ 0");
        baseMapper.insert(BeanUtil.copyProperties(vo, ProductEntity.class));
    }
    // update → updateById(...) ; delete → removeByIds(idList)  (logic delete)
}
```

## 7. Controller — permission codes are the contract

```java
@RestController
@RequestMapping("biz/product")
@Tag(name = "Product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("page") @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('biz:product:page')")
    public Result<PageResult<ProductVO>> page(@ParameterObject @Valid ProductQuery query) { … }

    @PostMapping @Operation(summary = "Save")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('biz:product:save')")
    public Result<String> save(@RequestBody @Valid ProductVO vo) { … }

    // GET {id} → biz:product:info · PUT → biz:product:update · DELETE (@RequestBody List<Long>) → biz:product:delete
}
```

The super admin automatically gets these codes (they're scanned from `@PreAuthorize`); everyone else needs them in `sys_menu.authority`.

## 8. Menu rows

```sql
-- Parent menu (type 0). url = path under src/views without ".vue"
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time)
VALUES (NULL, 'Products', 'biz/product/index', NULL, 0, 0, 'icon-appstore', 10, 0, 0, 10000, now(), 10000, now());
SET @pid = LAST_INSERT_ID();
-- Buttons (type 1). authority = comma-separated codes
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES
 (@pid, 'Query',  '', 'biz:product:page',                  1, 0, '', 0, 0, 0, 10000, now(), 10000, now()),
 (@pid, 'Add',    '', 'biz:product:save',                  1, 0, '', 1, 0, 0, 10000, now(), 10000, now()),
 (@pid, 'Edit',   '', 'biz:product:update,biz:product:info',1, 0, '', 2, 0, 0, 10000, now(), 10000, now()),
 (@pid, 'Delete', '', 'biz:product:delete',                1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
```
(Or add them through **System Settings → Menu Management** in the UI.)

## 9–10. Frontend

`src/api/biz/product.ts`
```ts
import service from '@/utils/request'
export const useProductApi = (id: number) => service.get('/biz/product/' + id)
export const useProductSubmitApi = (form: any) => form.id ? service.put('/biz/product', form) : service.post('/biz/product', form)
```

`src/views/biz/product/index.vue` — copy `views/sys/post/index.vue`, then change:
```ts
const state: IHooksOptions = reactive({
  dataListUrl: '/biz/product/page',
  deleteUrl: '/biz/product',
  queryForm: { name: '', status: '' }
})
```
and the `v-auth` codes (`'biz:product:save'`, …), the `name` in `<script setup name="BizProductIndex">`, and the columns.

## 11. Verify

1. Restart backend; reload frontend (menus are fetched at boot).
2. As `admin`: the menu appears, buttons visible.
3. Create a role with only *Query*; assign it to a test user → *Add/Edit/Delete* buttons disappear, and `POST /biz/product` returns `{"code":403}`.
4. Set that role's data scope to *Own data* → the user only sees rows they created.
5. Check **Operation Log** for your INSERT/UPDATE/DELETE entries (allow ~10 s).
