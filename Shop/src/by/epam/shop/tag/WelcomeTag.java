package by.epam.shop.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class WelcomeTag extends TagSupport {
	private String locale;
	private static final String EN = "en";
	private static final String EN_WELCOME = "Welcome!";
	private static final String RU_WELCOME = "Добро пожаловать!";

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String hello;
			if (EN.equalsIgnoreCase(locale) || locale.isEmpty()) {

				hello = EN_WELCOME;
			} else {
				hello = RU_WELCOME;
			}
			pageContext.getOut().write(hello);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
