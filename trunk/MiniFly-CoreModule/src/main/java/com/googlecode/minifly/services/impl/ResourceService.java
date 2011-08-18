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
package com.googlecode.minifly.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.googlecode.minifly.services.IResourceService;

/**
 * Resource service implementation
 * 
 * @author Gerton
 *
 */
public class ResourceService implements IResourceService {

	/* (non-Javadoc)
	 * @see com.googlecode.minifly.services.IResourceService#writeResourceToStream(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.ServletOutputStream)
	 */
	public void writeResourceToStream(String docroot,
			HttpServletRequest request, ServletOutputStream os) throws IOException {
		
		InputStream resourceInputStream;
		String requestURI = request.getServletPath();
		
		if (docroot != null) {
			String filename = docroot + requestURI;
			File resource = new File(filename);
		
			resourceInputStream = new FileInputStream(resource);
		} else {
			// Get resource from servlet context
			resourceInputStream = request.getServletContext().getResourceAsStream(requestURI);
		}
		
		if (resourceInputStream == null) {
			throw new IOException();
		}
		
		int bytes;
		while ((bytes = resourceInputStream.read()) != -1) {
			os.write(bytes);
		}
	}

}
