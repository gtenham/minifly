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
package com.googlecode.minifly.web.filters.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Gerton
 *
 */
public class CompressorResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream baos;
	private HttpServletResponse originalResponse;
	/**
	 * @param response
	 * @throws IOException 
	 */
	public CompressorResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		originalResponse = response;
		baos = new ByteArrayOutputStream();
	}
	
	public ServletOutputStream getOutputStream() { 
	    return new FilterServletOutputStream(baos); 
	}
	
	public PrintWriter getWriter() { 
	    return new PrintWriter(getOutputStream(),true); 
	}
	
	public byte[] getResponseData() { 
	    return baos.toByteArray(); 
	} 
	
	
}
