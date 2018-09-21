package me.aurora.service;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/09/21 15:42:27
 */
public interface SessionService {

	Map getOnlineInfo();

	boolean forceLogout(String sessionId);
}
