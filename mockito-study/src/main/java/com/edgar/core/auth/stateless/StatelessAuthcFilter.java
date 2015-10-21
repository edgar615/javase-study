package com.edgar.core.auth.stateless;

import com.edgar.core.auth.AuthService;
import com.edgar.core.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 根据token校验用户的filter
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String DIGEST = "digest";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StatelessAuthcFilter.class);

    @Autowired
    private AuthService authService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse response) throws Exception {
        String accessToken = servletRequest.getParameter(ACCESS_TOKEN);
        String clientDigest = servletRequest.getParameter(DIGEST);
        String queryString = getQueryString(servletRequest);
        String baseString = "";
        if (servletRequest instanceof HttpServletRequest) {
            baseString = getBaseString((HttpServletRequest) servletRequest, queryString);
        }

        // 4、生成无状态Token
        StatelessToken token = new StatelessToken(accessToken, baseString,
                clientDigest);

        try {
            // 5、委托给Realm进行登录
            getSubject(servletRequest, response).login(token);
        } catch (Exception e) {
            LOGGER.error("Access Denied : {}", accessToken);
            onLoginFail(response);
            return false;
        }
        StatelessUser user = authService.getUser(accessToken);
        RequestContextHolder.currentRequestAttributes().setAttribute(Constants.USER_KEY, user, RequestAttributes.SCOPE_REQUEST);
        return true;
    }

    /**
     * 校验失败，返回401状态码
     *
     * @param response ServletResponse
     * @throws java.io.IOException IOException
     */
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Invalid Token!");
    }

    private String getBaseString(HttpServletRequest request, String queryString) {
        String method = request.getMethod();
        String servletPath = request.getServletPath();
        if (StringUtils.startsWith(servletPath, "/")) {
            servletPath = StringUtils.substringAfter(servletPath, "/");
        }
        StringBuilder baseString = new StringBuilder(method).append(servletPath);
        if (StringUtils.isNotBlank(queryString)) {
            baseString.append("?").append(queryString);
        }
        return baseString.toString();
    }

    private String getQueryString(ServletRequest request) {
        List<String> paramNameList = new ArrayList<String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (!"digest".equals(name)) {
                paramNameList.add(name);
            }
        }
        Collections.sort(paramNameList);
        List<String> paramStringList = new ArrayList<String>(paramNameList.size());

        for (String paramName : paramNameList) {
            String[] parameterValues = request.getParameterValues(paramName);
            List<String> parameterValueList = new ArrayList<String>();
            Collections.addAll(parameterValueList, parameterValues);
            Collections.sort(parameterValueList);
            paramStringList.add(paramName + "=" + StringUtils.join(parameterValueList, ","));
        }
        return StringUtils.join(paramStringList, "&");
    }
}