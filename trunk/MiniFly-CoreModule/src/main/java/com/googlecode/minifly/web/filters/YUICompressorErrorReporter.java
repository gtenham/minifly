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
