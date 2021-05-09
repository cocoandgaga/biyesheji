package com.zxj.finalexam.interceptor;

import com.alibaba.fastjson.JSON;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.CategoryDao;
import com.zxj.finalexam.dao.StuUserDao;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.entity.SysUserTokenEntity;
import com.zxj.finalexam.inf.ITelnetLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private ITelnetLoginService telnetLoginService;
    @Autowired
    private StuUserDao stuUserDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //获取改token用户
                    R r= telnetLoginService.queryByToken(token);
                    SysUserTokenEntity tokenEntity = JSON.parseObject(JSON.toJSONString(r.get("tokenEntity")), SysUserTokenEntity.class);
                    if (tokenEntity.getStuId()!=null) {
                        StuUserEntity stuUserEntity = stuUserDao.queryByUserId(tokenEntity.getStuId());
                        if (Objects.nonNull(stuUserEntity)) {
                            request.getSession().setAttribute("user", stuUserEntity);
                        }
                    }else if (tokenEntity.getUserId()!=null){
                        request.getSession().setAttribute("creator",tokenEntity.getUserId());
                    }
                    break;
                }
            }
        }
        return true;
    }
}
