package com.zliteams.hot.web.security.session;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.stereotype.Component;

import com.zliteams.hot.web.dao.SessionMapper;

@Component("sessionDAO")
public class DBSessionDao extends CachingSessionDAO {
	@Resource
	SessionMapper sessionMapper;

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return; // 如果会话过期/停止 没必要再更新了
		}
	}

	@Override
	protected void doDelete(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Serializable doCreate(Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
