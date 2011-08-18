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
import java.io.InputStreamReader;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.minifly.web.filters.wrappers.CompressorResponseWrapper;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YUICompressorFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(YUICompressorFilter.class);
	
	// YUI parameters
	private int lineBreakPos = -1;   //Insert a line break after the specified column number
    private boolean warn = false; //Display possible errors in the code
    private boolean munge = true; //Minify only, do not obfuscate
    private boolean preserveAllSemiColons = false; //Preserve unnecessary semicolons
    private boolean disableOptimizations = true; // Additional optimizations
    
    public void init(FilterConfig filterConfig) throws ServletException {
		
        String lineBreak = filterConfig.getInitParameter("line-break");
        if (lineBreak != null) {
        	lineBreakPos = Integer.parseInt(lineBreak);
        }

        String warnString = filterConfig.getInitParameter("warn");
        if (warnString != null) {
        	warn = Boolean.parseBoolean(warnString);
        }

        String noMungeString = filterConfig.getInitParameter("nomunge");
        if (noMungeString != null) {
        	munge = Boolean.parseBoolean(noMungeString) ? false : true; //swap values because it's nomunge
        }

        String preserveAllSemiColonsString = filterConfig.getInitParameter("preserve-semi");
        if (preserveAllSemiColonsString != null) {
        	preserveAllSemiColons = Boolean.parseBoolean(preserveAllSemiColonsString);
        }
        
        String disableOptimizationsString = filterConfig.getInitParameter("preserve-semi");
        if (disableOptimizationsString != null) {
        	disableOptimizations = Boolean.parseBoolean(disableOptimizationsString);
        }
	}
    
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		
        ServletOutputStream servletOutputStream = response.getOutputStream();
        
        CompressorResponseWrapper wrapperResponse = new CompressorResponseWrapper(response);
		filterChain.doFilter(request, wrapperResponse);

        String requestURI = request.getRequestURI();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(wrapperResponse.getResponseData());
        writeFileToServletOutputStream(requestURI, bais, servletOutputStream);
		
	}

	public void destroy() {		
	}
	
	private void writeFileToServletOutputStream(String requestURI, InputStream inputStream, ServletOutputStream servletOutputStream) throws IOException {
		String s;
	    if (requestURI.endsWith(".js")) {
            s = getCompressedJavaScript(inputStream);
        } else if (requestURI.endsWith(".css")) {
            s = getCompressedCss(inputStream);
        } else {
            s = "This file format is not supported by this filter. Only .css and .js are supported";
        }
        write(s, servletOutputStream);
    }
	/**
     * Write s to servletOutputStream
     *
     * @param s
     * @param servletOutputStream
     */
    private void write(String s, ServletOutputStream servletOutputStream) {
        try {
            servletOutputStream.print(s);
        } catch (IOException e) {
            log.error("error writing String to servletOutputStream: " + e.getMessage());
        }
    }
    
	/**
     * Note that the inputStream is closed!
     *
     * @param inputStream
     * @throws IOException
     */
    private String getCompressedJavaScript(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        JavaScriptCompressor compressor = new JavaScriptCompressor(isr, new YUICompressorErrorReporter());
        inputStream.close();

        StringWriter out = new StringWriter();
        compressor.compress(out, lineBreakPos, munge, warn, preserveAllSemiColons, disableOptimizations);
        out.flush();

        StringBuffer buffer = out.getBuffer();
        return buffer.toString();
    }

    /**
     * Note that the inputStream is closed!
     *
     * @param inputStream
     * @throws IOException
     */
    private String getCompressedCss(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        CssCompressor compressor = new CssCompressor(isr);
        inputStream.close();

        StringWriter out = new StringWriter();
        compressor.compress(out, lineBreakPos);
        out.flush();

        StringBuffer buffer = out.getBuffer();
        return buffer.toString();
    }
}
