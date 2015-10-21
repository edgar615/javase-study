package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysMenu;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysMenu is a Querydsl query type for SysMenu
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysMenu extends com.mysema.query.sql.RelationalPathBase<SysMenu> {

    private static final long serialVersionUID = 1714395973;

    public static final QSysMenu sysMenu = new QSysMenu("sys_menu");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final StringPath icon = createString("icon");

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final StringPath menuName = createString("menuName");

    public final StringPath menuPath = createString("menuPath");

    public final StringPath menuType = createString("menuType");

    public final NumberPath<Integer> parentId = createNumber("parentId", Integer.class);

    public final StringPath permission = createString("permission");

    public final NumberPath<Integer> sorted = createNumber("sorted", Integer.class);

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<SysMenu> primary = createPrimaryKey(menuId);

    public QSysMenu(String variable) {
        super(SysMenu.class, forVariable(variable), "null", "sys_menu");
        addMetadata();
    }

    public QSysMenu(String variable, String schema, String table) {
        super(SysMenu.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysMenu(Path<? extends SysMenu> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_menu");
        addMetadata();
    }

    public QSysMenu(PathMetadata<?> metadata) {
        super(SysMenu.class, metadata, "null", "sys_menu");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(icon, ColumnMetadata.named("icon").ofType(12).withSize(32));
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7));
        addMetadata(menuId, ColumnMetadata.named("menu_id").ofType(4).withSize(10).notNull());
        addMetadata(menuName, ColumnMetadata.named("menu_name").ofType(12).withSize(32).notNull());
        addMetadata(menuPath, ColumnMetadata.named("menu_path").ofType(12).withSize(128));
        addMetadata(menuType, ColumnMetadata.named("menu_type").ofType(12).withSize(32));
        addMetadata(parentId, ColumnMetadata.named("parent_id").ofType(4).withSize(10).notNull());
        addMetadata(permission, ColumnMetadata.named("permission").ofType(12).withSize(64));
        addMetadata(sorted, ColumnMetadata.named("sorted").ofType(4).withSize(10));
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

