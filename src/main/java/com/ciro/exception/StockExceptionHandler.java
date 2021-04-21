package com.ciro.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErroException.class)
	public final ResponseEntity<Erro> negocioException(ErroException ex, WebRequest request) {
		String mensagemUsuario = (ex.getMessage());
		String mensagemDesenvolvedor = ex.toString();
		Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);
		return new ResponseEntity<Erro>(erro, ex.getHttpStatus());
	}

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}
}
