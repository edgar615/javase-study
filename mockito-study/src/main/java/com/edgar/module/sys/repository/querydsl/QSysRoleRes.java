package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysRoleRes;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysRoleRes is a Querydsl query type for SysRoleRes
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysRoleRes extends com.mysema.query.sql.RelationalPathBase<SysRoleRes> {

    private static final long serialVersionUID = -1754051420;

    public static final QSysRoleRes sysRoleRes = new QSysRoleRes("sys_role_res");

    public final NumberPath<Integer> resourceId = createNumber("resourceId", Integer.class);

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final NumberPath<Integer> roleResId = createNumber("roleResId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysRoleRes> primary = createPrimaryKey(roleResId);

    public QSysRoleRes(String variable) {
        super(SysRoleRes.class, forVariable(variable), "null", "sys_role_res");
        addMetadata();
    }

    public QSysRoleRes(String variable, String schema, String table) {
        super(SysRoleRes.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysRoleRes(Path<? extends SysRoleRes> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_role_res");
        addMetadata();
    }

    public QSysRoleRes(PathMetadata<?> metadata) {
        super(SysRoleRes.class, metadata, "null", "sys_role_res");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(resourceId, ColumnMetadata.named("resource_id").ofType(4).withSize(10));
        addMetadata(roleId, ColumnMetadata.named("role_id").ofType(4).withSize(10));
        addMetadata(roleResId, ColumnMetadata.named("role_res_id").ofType(4).withSize(10).notNull());
    }

}

