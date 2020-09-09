package dev.marcel.application.common.feign.exception;

import feign.Response.Body;

@SuppressWarnings("serial")
public class FeignServerException extends RuntimeException {

	private int status;
	private String reason;
	private Body body;

	public FeignServerException() {
	}

	public FeignServerException(String message) {
		super(message);
	}

	public FeignServerException(Throwable cause) {
		super(cause);
	}

	public FeignServerException(int status, String reason) {
		super(status + " - " + reason);
		this.status = status;
		this.reason = reason;
	}

	public FeignServerException(int status, String reason, final Body body) {
		super(status + " - " + reason);
		this.status = status;
		this.reason = reason;
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public String getReason() {
		return reason;
	}

	public Body body() {
		return body;
	}
}