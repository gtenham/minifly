/**
 * 
 */
package com.googlecode.minifly.web.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gerton
 *
 */
public class ResponseHeaderFilter implements Filter {

	private FilterConfig fc;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// set the provided HTTP response parameters
	    for (Enumeration<String> e=fc.getInitParameterNames(); e.hasMoreElements();) {
	      String headerName = (String)e.nextElement();
	      response.addHeader(headerName,
	                 fc.getInitParameter(headerName));
	    }
	    // pass the request/response on
	    filterChain.doFilter(servletRequest, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		fc = null;
	}

}
