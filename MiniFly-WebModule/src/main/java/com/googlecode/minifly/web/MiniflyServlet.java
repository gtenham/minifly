/*
 * Copyright 2011 Gerton ten Ham
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.googlecode.minifly.web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.minifly.services.IResourceService;
import com.googlecode.minifly.services.impl.ResourceService;

/**
 * The <code>MiniflyServlet</code> is the servlet which provide the resource
 * as requested in the servletPath.
 * 
 * Example: http://localhost:8080/js/main.js will look for the resource <code>/js/main.js</code>
 * 
 * The servlet will look for a context-param named <code>x-minifly-doc-root</code>
 * holding the document root for the requested resource.
 * 
 * Example web.xml:
 * <servlet>
 *  	<servlet-name>minifly</servlet-name>
 *  	<servlet-class>com.googlecode.minifly.web.MiniflyServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 *  	<servlet-name>minifly</servlet-name>
 *  	<url-pattern>/</url-pattern>
 * </servlet-mapping>
 *	
 * <context-param>
 *  	<param-name>x-minifly-doc-root</param-name>
 *  	<param-value>D:/www/public</param-value>
 * </context-param>
 * 
 * @author Gerton
 *
 */
public class MiniflyServlet extends HttpServlet {

	private static final long serialVersionUID = 7986474589664415111L;

	private String documentRoot;
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    documentRoot = getServletContext().getInitParameter("x-minifly-doc-root");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String docRootHeader = request.getHeader("x-minifly-doc-root");
		if (docRootHeader != null) {
			documentRoot = docRootHeader;
		}
		
		IResourceService rs = new ResourceService();
		
		try {
			rs.writeResourceToStream(documentRoot, request, response.getOutputStream());
		} catch (IOException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}
}
