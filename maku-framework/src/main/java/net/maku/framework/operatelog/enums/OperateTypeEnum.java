package net.maku.framework.operatelog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Operation type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum {
    /**
     * Query
     */
    GET(1),
    /**
     * Add
     */
    INSERT(2),
    /**
     * Edit
     */
    UPDATE(3),
    /**
     * Delete
     */
    DELETE(4),
    /**
     * Export
     */
    EXPORT(5),
    /**
     * Import
     */
    IMPORT(6),
    /**
     * Other
     */
    OTHER(0);

    private final int value;
}
