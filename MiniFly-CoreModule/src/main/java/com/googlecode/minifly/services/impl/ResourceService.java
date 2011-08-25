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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.googlecode.minifly.compress.CompressOptions;
import com.googlecode.minifly.services.IResourceService;
import com.googlecode.minifly.web.filters.YUICompressorErrorReporter;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

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
		
		write(resourceInputStream, os);
//		int bytes;
//		while ((bytes = resourceInputStream.read()) != -1) {
//			os.write(bytes);
//		}
	}

	public String getCompressedJavaScript(InputStream inputStream,
			CompressOptions options) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
        JavaScriptCompressor compressor = new JavaScriptCompressor(isr, new YUICompressorErrorReporter());
        inputStream.close();

        StringWriter out = new StringWriter();
        compressor.compress(out, options.getLineBreakPos(), options.isMunge(), options.isWarn(), options.isPreserveAllSemiColons(), options.isDisableOptimizations());
        out.flush();

        StringBuffer buffer = out.getBuffer();
        return buffer.toString();
	}

	public String getCompressedCss(InputStream inputStream, CompressOptions options)
			throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
        CssCompressor compressor = new CssCompressor(isr);
        inputStream.close();

        StringWriter out = new StringWriter();
        compressor.compress(out, options.getLineBreakPos());
        out.flush();

        StringBuffer buffer = out.getBuffer();
        return buffer.toString();
	}

	/* (non-Javadoc)
	 * @see com.googlecode.minifly.services.IResourceService#write(java.io.InputStream, java.io.OutputStream)
	 */
	public void write(InputStream inputstream, OutputStream outputstream) throws IOException {

		int bytes;
		while ((bytes = inputstream.read()) != -1) {
			outputstream.write(bytes);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.googlecode.minifly.services.IResourceService#write(java.lang.String, javax.servlet.ServletOutputStream)
	 */
	public void write(String input, ServletOutputStream outputstream) throws IOException {

		outputstream.print(input);
		
	}

}
