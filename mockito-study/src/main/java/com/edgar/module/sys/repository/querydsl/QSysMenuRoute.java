package com.edgar.module.sys.repository.querydsl;

import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QSysMenuRoute is a Querydsl query type for SysMenuRoute
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSysMenuRoute extends com.mysema.query.sql.RelationalPathBase<SysMenuRoute> {

    private static final long serialVersionUID = 416246276;

    public static final QSysMenuRoute sysMenuRoute = new QSysMenuRoute("sys_menu_route");

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final NumberPath<Integer> menuRouteId = createNumber("menuRouteId", Integer.class);

    public final NumberPath<Integer> routeId = createNumber("routeId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<SysMenuRoute> primary = createPrimaryKey(menuRouteId);

    public QSysMenuRoute(String variable) {
        super(SysMenuRoute.class, forVariable(variable), "null", "sys_menu_route");
        addMetadata();
    }

    public QSysMenuRoute(String variable, String schema, String table) {
        super(SysMenuRoute.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSysMenuRoute(Path<? extends SysMenuRoute> path) {
        super(path.getType(), path.getMetadata(), "null", "sys_menu_route");
        addMetadata();
    }

    public QSysMenuRoute(PathMetadata<?> metadata) {
        super(SysMenuRoute.class, metadata, "null", "sys_menu_route");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(menuId, ColumnMetadata.named("menu_id").ofType(4).withSize(10));
        addMetadata(menuRouteId, ColumnMetadata.named("menu_route_id").ofType(4).withSize(10).notNull());
        addMetadata(routeId, ColumnMetadata.named("route_id").ofType(4).withSize(10));
    }

}

