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
package com.googlecode.minifly.web.filters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.minifly.compress.CompressOptions;
import com.googlecode.minifly.services.IResourceService;
import com.googlecode.minifly.services.impl.ResourceService;
import com.googlecode.minifly.web.filters.wrappers.CompressorResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YUICompressorFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(YUICompressorFilter.class);
	
	// YUI parameters
	private CompressOptions options;
	
	IResourceService rs;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
    	options = new CompressOptions();
    	rs = new ResourceService();
    	
        String lineBreak = filterConfig.getInitParameter("line-break");
        if (lineBreak != null) {
        	options.setLineBreakPos(Integer.parseInt(lineBreak));
        }

        String warnString = filterConfig.getInitParameter("warn");
        if (warnString != null) {
        	options.setWarn(Boolean.parseBoolean(warnString));
        }

        String noMungeString = filterConfig.getInitParameter("nomunge");
        if (noMungeString != null) {
        	options.setMunge(Boolean.parseBoolean(noMungeString) ? false : true);
        }

        String preserveAllSemiColonsString = filterConfig.getInitParameter("preserve-semi");
        if (preserveAllSemiColonsString != null) {
        	options.setPreserveAllSemiColons(Boolean.parseBoolean(preserveAllSemiColonsString));
        }
        
        String disableOptimizationsString = filterConfig.getInitParameter("preserve-semi");
        if (disableOptimizationsString != null) {
        	options.setDisableOptimizations(Boolean.parseBoolean(disableOptimizationsString));
        }
	}
    
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String requestURI = request.getRequestURI();
		String cacheOverride = request.getParameter("cache-override");
		
		ServletOutputStream servletOutputStream = response.getOutputStream();
        
        if (cacheOverride != null && cacheOverride.equalsIgnoreCase("debug")) {
        	filterChain.doFilter(servletRequest, response);
        } else {
        	CompressorResponseWrapper wrapperResponse = new CompressorResponseWrapper(response);
        	filterChain.doFilter(request, wrapperResponse);
        
        	ByteArrayInputStream bais = new ByteArrayInputStream(wrapperResponse.getResponseData());
        	writeFileToServletOutputStream(requestURI, bais, servletOutputStream);
        }
	}

	public void destroy() {		
	}
	
	/**
	 * @param requestURI
	 * @param inputStream
	 * @param servletOutputStream
	 * @throws IOException
	 */
	private void writeFileToServletOutputStream(String requestURI, InputStream inputStream, ServletOutputStream servletOutputStream) throws IOException {
		
		String s;
	    if (requestURI.endsWith(".js")) {
            s = rs.getCompressedJavaScript(inputStream, options);
        } else if (requestURI.endsWith(".css")) {
            s = rs.getCompressedCss(inputStream, options);
        } else {
            s = "This file format is not supported by this filter. Only .css and .js are supported";
        }
        rs.write(s, servletOutputStream);
		
    }
	
}
