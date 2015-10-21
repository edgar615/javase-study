package com.edgar.core.mvc;

import com.edgar.module.sys.init.RouteLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring MVC的布局拦截器。默认返回layoutView的视图。 并将原来返回的视图作为属性保存在request中。 如果不需要使用布局，则在返回的视图前加上
 * <code>noLayout:</code>或者 <code>redirect:</code>前缀
 *
 * @author 张雨舟
 * @version 1.0
 */
@Deprecated
public class LayoutInterceptor extends HandlerInterceptorAdapter {
    private static final String NO_LAYOUT = "noLayout:";

    private String layoutView;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        if (modelAndView != null) {
            String originalView = modelAndView.getViewName();

            if (originalView != null && !originalView.startsWith("redirect:")) {
                includeLayout(modelAndView, originalView);
            }
        }
    }

    /**
     * 将返回的视图做布局处理
     *
     * @param modelAndView Spring视图对象
     * @param originalView 需要返回的视图
     */
    private void includeLayout(ModelAndView modelAndView, String originalView) {
        boolean noLayout = originalView.startsWith(NO_LAYOUT);
        String realViewName = originalView;
        if (noLayout) {
            realViewName = originalView.substring(NO_LAYOUT.length());
        }

        if (noLayout) {
            modelAndView.setViewName(realViewName);
        } else {
            modelAndView.addObject("menus", RouteLoader.getRoutes());

            modelAndView.addObject("view", realViewName);
            modelAndView.setViewName(layoutView);
        }
    }

    public void setLayoutView(String layoutView) {
        this.layoutView = layoutView;
    }
}