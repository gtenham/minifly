/**
 * 
 */
package com.googlecode.minifly.web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.minifly.services.impl.ResourceService;

/**
 * @author Gerton
 *
 */
public class MiniflyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7986474589664415111L;

	private String documentRoot;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    documentRoot = getServletContext().getInitParameter("x-minifly-doc-root");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String docRootHeader = request.getHeader("x-minifly-doc-root");
		if (docRootHeader != null) {
			documentRoot = docRootHeader;
		}
		
		ResourceService rs = new ResourceService();
		rs.writeResourceToStream(documentRoot, request, response.getOutputStream());
	}
}
