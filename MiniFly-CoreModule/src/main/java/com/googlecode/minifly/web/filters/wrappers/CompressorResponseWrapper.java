/**
 * 
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
