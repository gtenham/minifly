/**
 * 
 */
package com.googlecode.minifly.services;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Gerton
 *
 */
public interface IResourceService {

	void writeResourceToStream(String docroot, HttpServletRequest request, ServletOutputStream os ) throws IOException;
}
