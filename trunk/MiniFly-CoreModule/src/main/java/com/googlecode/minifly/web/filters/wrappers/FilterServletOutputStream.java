/**
 * 
 */
package com.googlecode.minifly.web.filters.wrappers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Gerton
 *
 */
public class FilterServletOutputStream extends ServletOutputStream {

	private DataOutputStream stream; 

	public FilterServletOutputStream(OutputStream output) { 
		stream = new DataOutputStream(output); 
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException  { 
		stream.write(b); 
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte[] b) throws IOException  { 
		stream.write(b); 
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte[] b, int off, int len) throws IOException  { 
		stream.write(b,off,len); 
	} 

}
