package br.com.emanuelgabriel.projeto01.services.exception;

public class RegraNegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegraNegocioException(String msg) {
		super(msg);
	}

}
