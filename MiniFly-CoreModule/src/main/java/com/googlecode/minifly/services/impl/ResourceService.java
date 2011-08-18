/**
 * 
 */
package com.googlecode.minifly.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.googlecode.minifly.services.IResourceService;

/**
 * @author Gerton
 *
 */
public class ResourceService implements IResourceService {

	/* (non-Javadoc)
	 * @see com.googlecode.minifly.services.IResourceService#writeResourceToStream(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.ServletOutputStream)
	 */
	public void writeResourceToStream(String docroot,
			HttpServletRequest request, ServletOutputStream os) throws IOException {
		
		String requestURI = request.getServletPath();
		
		String filename = docroot + requestURI;
        File resource = new File(filename);
		
		FileInputStream fileInputStream = new FileInputStream(resource);
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			os.write(bytes);
		}
	}

}
