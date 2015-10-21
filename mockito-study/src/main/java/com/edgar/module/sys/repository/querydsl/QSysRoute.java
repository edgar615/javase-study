package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysRoute;
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
 * QSysRoute is a Querydsl query type for SysRoute
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysRoute extends com.mysema.query.sql.RelationalPathBase<SysRoute> {

    private static final long serialVersionUID = 1611589923;

    public static final QSysRoute sysRoute = new QSysRoute("sys_route");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final BooleanPath isRoot = createBoolean("isRoot");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> routeId = createNumber("routeId", Integer.class);

    public final DateTimePath<java.sql.Timestamp> updatedTime = createDateTime("updatedTime", java.sql.Timestamp.class);

    public final StringPath url = createString("url");

    public final com.mysema.query.sql.PrimaryKey<SysRoute> primary = createPrimaryKey(routeId);

    public QSysRoute(String variable) {
        super(SysRoute.class, forVariable(variable), "null", "sys_route");
        addMetadata();
    }

    public QSysRoute(String variable, String schema, String table) {
        super(SysRoute.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysRoute(Path<? extends SysRoute> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_route");
        addMetadata();
    }

    public QSysRoute(PathMetadata<?> metadata) {
        super(SysRoute.class, metadata, "null", "sys_route");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdTime, ColumnMetadata.named("created_time").ofType(93).withSize(19).notNull());
        addMetadata(isRoot, ColumnMetadata.named("is_root").ofType(-7));
        addMetadata(name, ColumnMetadata.named("name").ofType(12).withSize(32).notNull());
        addMetadata(routeId, ColumnMetadata.named("route_id").ofType(4).withSize(10).notNull());
        addMetadata(updatedTime, ColumnMetadata.named("updated_time").ofType(93).withSize(19).notNull());
        addMetadata(url, ColumnMetadata.named("url").ofType(12).withSize(128).notNull());
    }

}

