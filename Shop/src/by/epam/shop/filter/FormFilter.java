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

public class FormFilter implements Filter {
	private final static String NUMBERS = "numbers";
	private final static String NUMBER = "number";

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
		List<Integer> numbers = (List<Integer>) request.getServletContext().getAttribute(NUMBERS);
		Integer number = (Integer) request.getAttribute(NUMBER);
		if (number != null && numbers != null) {
			for (Integer i : numbers) {
				if (i.equals(number)) {
					page = PageName.INDEX_PAGE;
					request.setAttribute("message", "In filter");
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
