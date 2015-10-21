package com.edgar.core.view;

import com.edgar.core.exception.SystemException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * rest返回的视图.
 *
 * @author 张雨舟
 * @version 1.0
 */
public final class ResponseMessage {

    private ResponseMessage() {
        super();
    }

    /**
     * 根据SystemException返回视图
     *
     * @param e SystemException
     * @return 视图
     */
    public static ModelAndView asModelAndView(SystemException e) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setExtractValueFromSingleKeyModel(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", e.getErrorCode().getNumber());
        if (e.getMessage() != null) {
            map.put("message", e.getMessage());
        } else {
            map.put("message", "System Error");
        }
        map.put("exMessage", e.getPropertyMap());
        return new ModelAndView(jsonView, map);
    }

    /**
     * 根据参数返回视图
     *
     * @param message 对象
     * @return 视图
     */
    public static ModelAndView asModelAndView(Object message) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setExtractValueFromSingleKeyModel(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        // map.put("code", 500);
        return new ModelAndView(jsonView, map);
    }

    /**
     * 根据参数返回视图
     *
     * @return 视图
     */
    public static ModelAndView success() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setExtractValueFromSingleKeyModel(true);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "success");
        // map.put("code", 500);
        return new ModelAndView(jsonView, map);
    }
}