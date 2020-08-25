package com.example.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义Session获取规则，采用http请求头authToken携带sessionId的方式
 * 登录成功后，会返回会话的sessionId，前端需要在请求头中加入该sessionId
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

    public final static String HEADER_TOKEN_NAME = "token";

    public ShiroSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);
        if (StringUtils.isEmpty(id)) {
            // 按照默认规则从cookie中获取SessionId
            return super.getSessionId(request, response);
        } else {
            // 从Header头中获取sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }
}
