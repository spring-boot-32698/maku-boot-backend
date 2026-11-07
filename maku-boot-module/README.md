## Overview
maku-boot uses a component model to extend business features, so various requirements are easy to implement without bloating the system; include a component when you need it, and leave it out otherwise.

## Including a component
To use a component such as `maku-boot-quartz`, add it to `maku-boot/maku-server/pom.xml` as follows:

```xml
<dependency>
    <groupId>net.maku</groupId>
    <artifactId>maku-boot-quartz</artifactId>
    <version>${revision}</version>
</dependency>
```

## SQL scripts
When including a component, also run the matching SQL file to initialize its tables, menus, etc.
If you use MySQL, run the following SQL file:

```
maku-boot/db/mysql/module/maku-module-quartz.sql
```