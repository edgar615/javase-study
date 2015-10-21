package com.edgar.core.mvc;

import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.AuthType;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.core.view.ResponseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据容器的错误码，返回对应的视图
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/error")
public class ErrorResource {

    /**
     * 400
     *
     * @return 视图
     */
    @AuthHelper(value = "400", type = AuthType.ANON)
    @RequestMapping(value = "/400", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView error400() {
        return ResponseMessage.asModelAndView(ExceptionFactory.badRequest());
    }

    /**
     * 404
     *
     * @return 视图
     */
    @AuthHelper(value = "404", type = AuthType.ANON)
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView error404() {
        return ResponseMessage.asModelAndView(ExceptionFactory.notFound());
    }

    /**
     * 405
     *
     * @return 视图
     */
    @AuthHelper(value = "405", type = AuthType.ANON)
    @RequestMapping(value = "/405", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView error405() {
        return ResponseMessage.asModelAndView(ExceptionFactory.unSupportMethod());
    }

    /**
     * 500
     *
     * @return 视图
     */
    @AuthHelper(value = "500", type = AuthType.ANON)
    @RequestMapping(value = "/500", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView error500() {
        return ResponseMessage.asModelAndView(ExceptionFactory.appError());
    }
}
