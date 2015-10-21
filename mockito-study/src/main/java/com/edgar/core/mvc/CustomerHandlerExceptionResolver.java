package com.edgar.core.mvc;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import com.edgar.core.mail.MailService;
import com.edgar.core.util.Constants;
import com.edgar.core.view.ResponseMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * 异常处理，继承Spring的<code>AbstractHandlerExceptionResolver</code>， 返回符合REST风格的错误信息.
 *
 * @author 张雨舟
 * @version 1.0
 */
@Service
public class CustomerHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHandlerExceptionResolver.class);

    @Autowired
    private MailService mailService;

    @Value("${mail.alert.from}")
    private String alertFrom;

    @Value("${mail.alert.to}")
    private String alertTo;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof SystemException) {
            SystemException e = (SystemException) ex;
            response.setStatus(403);
            if (e.getErrorCode().getNumber() != 30) {
                LOGGER.error("System Exception[errorCode: {}, desc: {}, detail: {}]", e.getErrorCode()
                                .getNumber(), e.getMessage(), e.getPropertyMap(),
                        ex);
            }
            return ResponseMessage.asModelAndView(e);
        }
        LOGGER.error("Exception: {}", ex.getMessage(), ex);
        errorReport(request, ex);
        if (ex instanceof ConstraintViolationException) {
            SystemException systemException = createSystemException(ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ResponseMessage.asModelAndView(systemException);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ResponseMessage.asModelAndView(ex.getMessage());
        }
    }

    /**
     * 根据方法参数的校验返回异常
     *
     * @param ex 异常
     * @return SystemException
     */
    private SystemException createSystemException(Exception ex) {
        ConstraintViolationException e = (ConstraintViolationException) ex;
        SystemException systemException = new SystemException(BusinessCode.APP_ERROR,
                "Illegal Argument");
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            systemException.setProperty(constraintViolation.getPropertyPath()
                    .toString(), constraintViolation.getPropertyPath()
                    + constraintViolation.getMessage());
        }
        return systemException;
    }

    /**
     * 发送错误邮件
     *
     * @param request HttpServletRequest
     * @param ex      异常信息
     */
    private void errorReport(HttpServletRequest request, Exception ex) {
        try {
            LOGGER.debug("send error mail");
            final Map<String, Object> model = new HashMap<String, Object>();
            model.put("req", request);
            model.put("t", ToStringBuilder.reflectionToString(ex));
            model.put("headers", addHeaders(request));
            model.put("params", addParams(request));
            model.put("properties", addSystemProperties());
            model.put("ex", exceptionToString(ex));
            final List<String> to = new ArrayList<String>();
            to.add(alertTo);
            final List<String> cc = new ArrayList<String>();
            Constants.EXEC.submit(new Runnable() {

                @Override
                public void run() {
                    mailService.sendTplLocationEmail(alertFrom, to, cc, "Application Error", "tpl/500.vm",
                            model);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将异常的printStackTrace转换为String对象
     *
     * @param ex 异常
     * @return printStackTrace的字符串
     * @throws java.io.IOException IO异常
     */
    private String exceptionToString(Exception ex) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ex.printStackTrace(new PrintStream(bos));
        } finally {
            bos.close();
        }
        return bos.toString();
    }

    /**
     * 返回系统变量
     *
     * @return map
     */
    private Map<Object, Object> addSystemProperties() {
        Map<Object, Object> propertiesMap = new HashMap<Object, Object>();
        Set<Object> props = System.getProperties().keySet();
        for (Object prop : props) {
            propertiesMap.put(prop, System.getProperty((String) prop));
        }
        return propertiesMap;
    }

    /**
     * 返回请求参数
     *
     * @param request HttpServletRequest
     * @return map
     */
    private Map<String, String> addParams(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            paramsMap.put(key, request.getParameter(key));
        }
        return paramsMap;
    }

    /**
     * 返回header参数
     *
     * @param request HttpServletRequest
     * @return map
     */
    private Map<String, String> addHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            headerMap.put(key, request.getHeader(key));
        }
        return headerMap;
    }
}
