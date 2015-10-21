package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysMenuRes;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysMenuRes is a Querydsl query type for SysMenuRes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysMenuRes extends com.mysema.query.sql.RelationalPathBase<SysMenuRes> {

    private static final long serialVersionUID = 2114396955;

    public static final QSysMenuRes sysMenuRes = new QSysMenuRes("sys_menu_res");

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final NumberPath<Integer> menuResId = createNumber("menuResId", Integer.class);

    public final NumberPath<Integer> resourceId = createNumber("resourceId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysMenuRes> primary = createPrimaryKey(menuResId);

    public QSysMenuRes(String variable) {
        super(SysMenuRes.class, forVariable(variable), "null", "sys_menu_res");
        addMetadata();
    }

    public QSysMenuRes(String variable, String schema, String table) {
        super(SysMenuRes.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysMenuRes(Path<? extends SysMenuRes> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_menu_res");
        addMetadata();
    }

    public QSysMenuRes(PathMetadata<?> metadata) {
        super(SysMenuRes.class, metadata, "null", "sys_menu_res");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(menuId, ColumnMetadata.named("menu_id").ofType(4).withSize(10));
        addMetadata(menuResId, ColumnMetadata.named("menu_res_id").ofType(4).withSize(10).notNull());
        addMetadata(resourceId, ColumnMetadata.named("resource_id").ofType(4).withSize(10));
    }

}

