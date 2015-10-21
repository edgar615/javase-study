package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysResource;
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
 * QSysResource is a Querydsl query type for SysResource
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysResource extends com.mysema.query.sql.RelationalPathBase<SysResource> {

    private static final long serialVersionUID = 1384572660;

    public static final QSysResource sysResource = new QSysResource("sys_resource");

    public final StringPath authType = createString("authType");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final StringPath method = createString("method");

    public final StringPath permission = createString("permission");

    public final NumberPath<Integer> resourceId = createNumber("resourceId", Integer.class);

    public final StringPath resourceName = createString("resourceName");

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final StringPath url = createString("url");

    public final com.mysema.query.sql.PrimaryKey<SysResource> primary = createPrimaryKey(resourceId);

    public QSysResource(String variable) {
        super(SysResource.class, forVariable(variable), "null", "sys_resource");
        addMetadata();
    }

    public QSysResource(String variable, String schema, String table) {
        super(SysResource.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysResource(Path<? extends SysResource> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_resource");
        addMetadata();
    }

    public QSysResource(PathMetadata<?> metadata) {
        super(SysResource.class, metadata, "null", "sys_resource");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(authType, ColumnMetadata.named("auth_type").ofType(12).withSize(32).notNull());
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7).notNull());
        addMetadata(method, ColumnMetadata.named("method").ofType(12).withSize(32).notNull());
        addMetadata(permission, ColumnMetadata.named("permission").ofType(12).withSize(64));
        addMetadata(resourceId, ColumnMetadata.named("resource_id").ofType(4).withSize(10).notNull());
        addMetadata(resourceName, ColumnMetadata.named("resource_name").ofType(12).withSize(64));
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
        addMetadata(url, ColumnMetadata.named("url").ofType(12).withSize(128).notNull());
    }

}

