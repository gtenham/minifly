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
package com.googlecode.minifly.services;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.googlecode.minifly.compress.YUIOptions;

/**
 * Resource service interface
 * 
 * @author Gerton
 *
 */
public interface IResourceService {

	/**
	 * Write resource from request to the servlet output stream.
	 * Use docroot as the basepath for the resource
	 * 
	 * @param String docroot
	 * @param HttpServletRequest request
	 * @param ServletOutputStream os
	 * @throws IOException
	 */
	void writeResourceToStream(String docroot, HttpServletRequest request, ServletOutputStream os ) throws IOException;
	
	/**
	 * Compress javascript content using YUICompressor.
	 * 
	 * @param  InputStream inputStream
	 * @param  YUIOptions options
	 * @return String Compressed javascript content
	 * @throws IOException
	 */
	String getCompressedJavaScript(InputStream inputStream, YUIOptions options) throws IOException;
	
	/**
	 * Compress css content using YUICompressor.
	 * 
	 * @param  InputStream inputStream
	 * @param  YUIOptions options
	 * @return String Compressed css content
	 * @throws IOException
	 */
	String getCompressedCss(InputStream inputStream, YUIOptions options) throws IOException;
}
