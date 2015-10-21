package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysRoleMenu;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysRoleMenu is a Querydsl query type for SysRoleMenu
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysRoleMenu extends com.mysema.query.sql.RelationalPathBase<SysRoleMenu> {

    private static final long serialVersionUID = 1458831835;

    public static final QSysRoleMenu sysRoleMenu = new QSysRoleMenu("sys_role_menu");

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final NumberPath<Integer> roleMenuId = createNumber("roleMenuId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysRoleMenu> primary = createPrimaryKey(roleMenuId);

    public QSysRoleMenu(String variable) {
        super(SysRoleMenu.class, forVariable(variable), "null", "sys_role_menu");
        addMetadata();
    }

    public QSysRoleMenu(String variable, String schema, String table) {
        super(SysRoleMenu.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysRoleMenu(Path<? extends SysRoleMenu> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_role_menu");
        addMetadata();
    }

    public QSysRoleMenu(PathMetadata<?> metadata) {
        super(SysRoleMenu.class, metadata, "null", "sys_role_menu");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(menuId, ColumnMetadata.named("menu_id").ofType(4).withSize(10));
        addMetadata(roleId, ColumnMetadata.named("role_id").ofType(4).withSize(10));
        addMetadata(roleMenuId, ColumnMetadata.named("role_menu_id").ofType(4).withSize(10).notNull());
    }

}

