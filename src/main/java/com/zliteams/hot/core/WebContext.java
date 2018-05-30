package com.zliteams.hot.core;

import javax.servlet.http.HttpServletRequest;

import com.zliteams.hot.web.model.User;

/**
 * 用户Web上下文
 *
 */
public class WebContext {

	protected final String jsessionid;
	
	private User currentUser;

	public WebContext(HttpServletRequest request) {
		jsessionid = request.getSession(true).getId();
		setCurrentUser((User) request.getSession().getAttribute("userInfo"));
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
