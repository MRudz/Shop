package by.epam.shop.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;

public class UserFilter implements Filter {
	private final static String USER = "login";
	private final static String MESSAGE = "message";
	private static final String BLOCKED_USERS = "blockedUsers";
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String page = null;
		List<String> blockedUsers = null;
		User user = (User) request.getSession().getAttribute(USER);
		blockedUsers = (List<String>) request.getSession().getServletContext().getAttribute(BLOCKED_USERS);
		if (user != null && blockedUsers != null) {
			for (String login : blockedUsers) {
				if (login.equals(user.getLogin())) {
					request.setAttribute(MESSAGE, MessageManager.BLACKLIST);
					request.getSession().setAttribute(USER, null);
					page = PageName.REGISTER_PAGE;
					RequestDispatcher dispatcher = request.getRequestDispatcher(page);
					dispatcher.forward(request, response);
				}
			}
		}
		filterChain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
