/**
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

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * The SimplePageCachingExtFilter extends {@link net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter}
 * by adding a cache override functionality.
 * 
 * Add the following request header parameter:
 * 
 * cache-override=true
 * 
 * @author Gerton
 *
 */
public class SimplePageCachingExtFilter extends SimplePageCachingFilter {

	private static final Logger logger = LoggerFactory.getLogger(SimplePageCachingExtFilter.class);
	
	/**
     * Build page info either using the cache or building the page directly.
     * <p/>
     * Some requests are for page fragments which should never be gzipped, or for
     * other pages which are not gzipped.
     */
    protected PageInfo buildPageInfo(final HttpServletRequest request, final HttpServletResponse response,
                                     final FilterChain chain) throws Exception {
        final String cacheOverride = request.getParameter("cache-override");
    	// Look up the cached page
        final String key = calculateKey(request);
        PageInfo pageInfo = null;
        String originalThreadName = Thread.currentThread().getName();
        try {
            checkNoReentry(request);
            Element element = blockingCache.get(key);
            if (element == null || element.getObjectValue() == null) {
                try {
                    // Page is not cached - build the response, cache it, and send to client
                    pageInfo = buildPage(request, response, chain);
                    if (pageInfo.isOk()) {
                        if (logger.isDebugEnabled()) {
                        	logger.debug("PageInfo ok. Adding to cache " + blockingCache.getName() + " with key " + key);
                        }
                        blockingCache.put(new Element(key, pageInfo));
                    } else {
                        if (logger.isDebugEnabled()) {
                        	logger.debug("PageInfo was not ok(200). Putting null into cache " + blockingCache.getName()
                                    + " with key " + key);
                        }
                        blockingCache.put(new Element(key, null));
                    }
                } catch (final Throwable throwable) {
                    // Must unlock the cache if the above fails. Will be logged at Filter
                    blockingCache.put(new Element(key, null));
                    throw new Exception(throwable);
                }
            } else if (cacheOverride != null) {
            	// Override cache. Build page from response
            	pageInfo = buildPage(request, response, chain);
            } else {
                pageInfo = (PageInfo) element.getObjectValue();
            }
        } catch (LockTimeoutException e) {
            //do not release the lock, because you never acquired it
            throw e;
        } finally {
            Thread.currentThread().setName(originalThreadName);
        }
        return pageInfo;
    }
}
