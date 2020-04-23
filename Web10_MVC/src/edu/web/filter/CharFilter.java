package edu.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/* request, response 객체에 한글 인코딩("UTF-8")을 적용시키는 역할 */
public class CharFilter implements Filter {

	public CharFilter() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("필터 작동 on");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String ipAddress = request.getRemoteAddr();
		
		System.out.println("IP : " + ipAddress);
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response);
	}

	public void destroy() {
	}

} // end CharFilter