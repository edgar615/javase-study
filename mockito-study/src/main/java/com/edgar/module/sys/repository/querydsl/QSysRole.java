package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysRole;
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
 * QSysRole is a Querydsl query type for SysRole
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysRole extends com.mysema.query.sql.RelationalPathBase<SysRole> {

    private static final long serialVersionUID = 1714554460;

    public static final QSysRole sysRole = new QSysRole("sys_role");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final StringPath roleCode = createString("roleCode");

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final StringPath roleName = createString("roleName");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<SysRole> primary = createPrimaryKey(roleId);

    public QSysRole(String variable) {
        super(SysRole.class, forVariable(variable), "null", "sys_role");
        addMetadata();
    }

    public QSysRole(String variable, String schema, String table) {
        super(SysRole.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysRole(Path<? extends SysRole> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_role");
        addMetadata();
    }

    public QSysRole(PathMetadata<?> metadata) {
        super(SysRole.class, metadata, "null", "sys_role");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7));
        addMetadata(roleCode, ColumnMetadata.named("role_code").ofType(12).withSize(32).notNull());
        addMetadata(roleId, ColumnMetadata.named("role_id").ofType(4).withSize(10).notNull());
        addMetadata(roleName, ColumnMetadata.named("role_name").ofType(12).withSize(32).notNull());
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
    }

}

