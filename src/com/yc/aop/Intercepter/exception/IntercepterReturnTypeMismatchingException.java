package com.yc.aop.Intercepter.exception;

import java.lang.Exception;

public class IntercepterReturnTypeMismatchingException extends Exception {
	private static final long serialVersionUID = -7357914628413338970L;

	public IntercepterReturnTypeMismatchingException(String message) {
		super(message);
	}

	public IntercepterReturnTypeMismatchingException(Throwable cause) {
		super(cause);
	}

	public IntercepterReturnTypeMismatchingException(String message, Throwable cause) {
		super(message, cause);
	}

	public IntercepterReturnTypeMismatchingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
