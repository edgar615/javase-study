package com.edgar.module.sys.init;

import com.edgar.core.exception.SystemException;
import com.edgar.core.init.AppInitializer;
import com.edgar.core.init.Initialization;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.service.SysRouteService;
import com.edgar.module.sys.vo.AngularRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 路由的初始化.
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class RouteLoader implements Initialization {
    private static final List<AngularRoute> ANGULAR_ROUTES = new ArrayList<AngularRoute>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    @Autowired
    private SysRouteService sysRouteService;

    public static synchronized List<AngularRoute> getRoutes() {
        return Collections.unmodifiableList(ANGULAR_ROUTES);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public synchronized void init() throws SystemException {
        LOGGER.info("/*****load route start*****/");
        List<SysRoute> routes = sysRouteService.findAll();
        for (SysRoute sysRoute : routes) {
            AngularRoute angularRoute = new AngularRoute();
            angularRoute.setUrl(sysRoute.getUrl());
            angularRoute.setName(sysRoute.getName());
            ANGULAR_ROUTES.add(angularRoute);
        }
        LOGGER.info("/*****load route finished*****/");
    }

}
