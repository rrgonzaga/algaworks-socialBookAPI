package com.algaworks.socialbooks.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.socialbooks.domain.DetalhesErro;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

@ControllerAdvice
public class GeneralExceptionHandler {
	
	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException(LivroNaoEncontradoException e, HttpServletRequest request){
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(404L);
		erro.setTitulo(e.getMessage()); //O livro não foi encontrado
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
		erro.setTimeStamp(System.currentTimeMillis());		
		
		//Para retornar um simples código de 404 - Not found de recurso não encontrado. 
		//return ResponseEntity.notFound().build();
		
		//Para retornar um código de 404 - Not found de recurso não encontrado e detalhes da exceção no corpo do response.
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		
	}
	
	//Tratando qualquer exceção desconhecida
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleUnknownException(Exception e, HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
