package br.com.emanuelgabriel.projeto01.services.exception;

public class ObjNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjNaoEncontradoException(String msg) {
		super(msg);
	}

}
