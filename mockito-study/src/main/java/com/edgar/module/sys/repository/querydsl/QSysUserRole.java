package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysUserRole;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysUserRole is a Querydsl query type for SysUserRole
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysUserRole extends com.mysema.query.sql.RelationalPathBase<SysUserRole> {

    private static final long serialVersionUID = 1459103175;

    public static final QSysUserRole sysUserRole = new QSysUserRole("sys_user_role");

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> userRoleId = createNumber("userRoleId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysUserRole> primary = createPrimaryKey(userRoleId);

    public QSysUserRole(String variable) {
        super(SysUserRole.class, forVariable(variable), "null", "sys_user_role");
        addMetadata();
    }

    public QSysUserRole(String variable, String schema, String table) {
        super(SysUserRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysUserRole(Path<? extends SysUserRole> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_user_role");
        addMetadata();
    }

    public QSysUserRole(PathMetadata<?> metadata) {
        super(SysUserRole.class, metadata, "null", "sys_user_role");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(roleId, ColumnMetadata.named("role_id").ofType(4).withSize(10));
        addMetadata(userId, ColumnMetadata.named("user_id").ofType(4).withSize(10));
        addMetadata(userRoleId, ColumnMetadata.named("user_role_id").ofType(4).withSize(10).notNull());
    }

}

