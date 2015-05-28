package dei.uc.pt.aor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/pages/*" })
public class LoginFilter implements Filter {

	public LoginFilter() {

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession ses = (HttpSession) req.getSession();
		if (ses.getAttribute("email") == null) {
			resp.sendRedirect(req.getContextPath() + "/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {

	}

}
