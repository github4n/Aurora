package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.User;
import me.aurora.domain.UserOnline;
import me.aurora.service.SessionService;
import me.aurora.util.AddressUtils;
import me.aurora.util.HttpContextUtils;
import me.aurora.util.PageUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Shiro Session 对象管理
 * @author 郑杰
 * @date 2018/09/21 15:43:02
 */
@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    ObjectMapper mapper;

    @Override
    public Map getOnlineInfo() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            User user;
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (User) principalCollection.getPrimaryPrincipal();
                userOnline.setUsername(user.getUsername());
                userOnline.setUserId(user.getId().toString());
            }
            userOnline.setId((String) session.getId());
            userOnline.setHost(session.getHost());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            Long timeout = session.getTimeout();
            if (timeout == 0L) {
                userOnline.setStatus("离线");
            } else {
                userOnline.setStatus("在线");
            }
            String address = AddressUtils.getCityInfo(userOnline.getHost());
            userOnline.setLocation(address);
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return PageUtil.buildPage(list,Long.parseLong(list.size()+""));
    }

    @Override
    public boolean forceLogout(String sessionId) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String id = request.getSession().getId();
        if(id.equals(sessionId)){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"不能踢出自己！");
        }
        try {
            Session session = sessionDAO.readSession(sessionId);
            session.setTimeout(0);
            session.stop();
            sessionDAO.delete(session);
        } catch (Exception e) {
            log.error("踢出用户失败", e);
            throw new AuroraException(HttpStatus.HTTP_INTERNAL_ERROR,"踢出用户失败！");
        }
        return true;
    }

}
