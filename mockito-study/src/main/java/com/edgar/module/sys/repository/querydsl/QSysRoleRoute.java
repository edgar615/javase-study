package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysRoleRoute;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysRoleRoute is a Querydsl query type for SysRoleRoute
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysRoleRoute extends com.mysema.query.sql.RelationalPathBase<SysRoleRoute> {

    private static final long serialVersionUID = -2015931059;

    public static final QSysRoleRoute sysRoleRoute = new QSysRoleRoute("sys_role_route");

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final NumberPath<Integer> roleRouteId = createNumber("roleRouteId", Integer.class);

    public final NumberPath<Integer> routeId = createNumber("routeId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysRoleRoute> primary = createPrimaryKey(roleRouteId);

    public QSysRoleRoute(String variable) {
        super(SysRoleRoute.class, forVariable(variable), "null", "sys_role_route");
        addMetadata();
    }

    public QSysRoleRoute(String variable, String schema, String table) {
        super(SysRoleRoute.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysRoleRoute(Path<? extends SysRoleRoute> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_role_route");
        addMetadata();
    }

    public QSysRoleRoute(PathMetadata<?> metadata) {
        super(SysRoleRoute.class, metadata, "null", "sys_role_route");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(roleId, ColumnMetadata.named("role_id").ofType(4).withSize(10));
        addMetadata(roleRouteId, ColumnMetadata.named("role_route_id").ofType(4).withSize(10).notNull());
        addMetadata(routeId, ColumnMetadata.named("route_id").ofType(4).withSize(10));
    }

}

