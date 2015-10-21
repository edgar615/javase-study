package com.edgar.core.mvc;

import com.edgar.core.auth.AccessToken;
import com.edgar.core.auth.AuthService;
import com.edgar.core.auth.LoginCommand;
import com.edgar.core.auth.RefreshVo;
import com.edgar.core.auth.stateless.StatelessUser;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.AuthType;
import com.edgar.core.util.Constants;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.core.view.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录、注销的rest类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/auth")
public class AuthenticatedResource {

    @Autowired
    private AuthService authService;

    /**
     * 未登录用户
     *
     * @return 未登录的视图
     */
    @AuthHelper(value = "UnLogin User", type = AuthType.ANON)
    @RequestMapping(method = RequestMethod.GET, value = "/unlogin")
    public ModelAndView unLogin() {
        throw ExceptionFactory.unLogin();
    }

    /**
     * 未授权用户
     *
     * @return 未授权的视图
     */
    @AuthHelper(value = "UnAuthorized User", type = AuthType.ANON)
    @RequestMapping(method = RequestMethod.GET, value = "/unauthorized")
    public ModelAndView unAuthorized() {
        throw ExceptionFactory.unAuthorized();
    }

    /**
     * 用户登录
     *
     * @param loginCommand 用户名 密码对象
     * @return 登录成功的视图
     */
    @AuthHelper(value = "Login", type = AuthType.SSL)
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ModelAndView login(@RequestBody LoginCommand loginCommand) {
        AccessToken token = authService.login(loginCommand);
        return ResponseMessage.asModelAndView(token);
    }

    /**
     * 刷新Token
     *
     * @param accessToken  accessToken
     * @param refreshToken refreshToken
     * @return 登录成功的视图
     */
    @AuthHelper(value = "Refresh Token", type = AuthType.SSL)
    @RequestMapping(method = RequestMethod.GET, value = "/refresh")
    public ModelAndView refresh(@RequestParam("accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken) {
        RefreshVo refreshCommand = new RefreshVo();
        refreshCommand.setAccessToken(accessToken);
        refreshCommand.setRefreshToken(refreshToken);
        AccessToken token = authService.refresh(refreshCommand);
        return ResponseMessage.asModelAndView(token);
    }


    /**
     * 用户注销
     *
     * @return 注销成功的视图
     */
    @AuthHelper(value = "Logout", type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout() {
        StatelessUser user = (StatelessUser) RequestContextHolder.currentRequestAttributes().getAttribute(Constants.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        authService.logout(user.getAccessToken());
        return ResponseMessage.asModelAndView("Logout Success");
    }
}
