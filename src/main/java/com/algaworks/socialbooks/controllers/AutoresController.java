package com.algaworks.socialbooks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.services.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresController {
	
	@Autowired
	private AutoresService autoresService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Autor>> listar() {		
		return ResponseEntity.status(HttpStatus.OK).body(autoresService.listar());
		//return ResponseEntity.ok(autoresService.listar());		
	}
	
	

}
