package dev.marcel.application.common.feign.exception;

import feign.Response.Body;

@SuppressWarnings("serial")
public class FeignClientException extends RuntimeException {

	private int status;
	private String reason;
	private Body body;

	public FeignClientException() {
	}

	public FeignClientException(String message) {
		super(message);
	}

	public FeignClientException(Throwable cause) {
		super(cause);
	}

	public FeignClientException(int status, String reason) {
		super(status + " - " + reason);
		this.status = status;
		this.reason = reason;
	}

	public FeignClientException(int status, String reason, final Body body) {
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