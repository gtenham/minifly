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

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YUICompressorErrorReporter implements ErrorReporter {
	private static final Logger log = LoggerFactory.getLogger(YUICompressorErrorReporter.class);
	
	public void warning(String message, String sourceName,
            int line, String lineSource, int lineOffset) {
		if (line < 0) {
			log.warn(message);
		} else {
			log.warn(line + ':' + lineOffset + ':' + message);
		}
	}
	
	public void error(String message, String sourceName,
	          int line, String lineSource, int lineOffset) {
		if (line < 0) {
			log.error(message);
		} else {
			log.error(line + ':' + lineOffset + ':' + message);
		}
	}
	
	public EvaluatorException runtimeError(String message, String sourceName,
	                               int line, String lineSource, int lineOffset) {
		error(message, sourceName, line, lineSource, lineOffset);
		return new EvaluatorException(message);
	}

}
