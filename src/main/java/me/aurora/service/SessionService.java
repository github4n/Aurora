package me.aurora.service;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/09/21 15:42:27
 */
public interface SessionService {

	/**
	 * 得到在线的用户
	 * @return
	 */
	Map getOnlineInfo();

	/**
	 * 踢出
	 * @param sessionId
	 * @return
	 */
	boolean forceLogout(String sessionId);
}
