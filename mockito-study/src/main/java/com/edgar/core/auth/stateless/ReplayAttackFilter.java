package com.edgar.core.auth.stateless;

import com.edgar.core.auth.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重放攻击的Filter.
 */
public class ReplayAttackFilter extends AccessControlFilter {

    /**
     * 随机数
     */
    private static final String NONCE = "nonce";

    /**
     * 时间戳
     */
    private static final String TIMESTAMP = "timestamp";

    @Autowired
    private AuthService authService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String nonce = request.getParameter(NONCE);
        long timestamp = NumberUtils.toLong(request.getParameter(TIMESTAMP));
        if (StringUtils.isNotBlank(nonce) && authService.isNewRequest(nonce, timestamp)) {
            return true;
        }
        onTimeout(response);
        return false;
    }

    /**
     * 校验失败，返回401状态码
     *
     * @param response ServletResponse
     * @throws java.io.IOException IOException
     */
    private void onTimeout(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Request timed out!");
    }
}
