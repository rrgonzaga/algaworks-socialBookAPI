package com.algaworks.socialbooks.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.LivrosService;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
/**
 * 
 * @author Rodrigo
 * @apiNote Quando acessar http://localhost:8080/h2-console/, colocar jdbc:h2:mem:testdb para JDBC URL.
 * @apiNote Para informação adicional sobre os métodos e respostas HTTP, acessar: https://tools.ietf.org/html/rfc7231#section-6.3.5 
 */
@RestController
@RequestMapping("/livros")
public class LivrosController {
	
	@Autowired
	private LivrosService livrosServices;
	
	/**
	 * Obtém todos os livros cadastrados
	 * @implNote O método GET obtém um ou mais recursos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Livro>> listar() {
		
//		Livro l1 = new Livro("Rest Aplicado");
//		Livro l2 = new Livro("Git passo a passo");
//		
//		Livro[] livros = {l1,l2};
//		
//		return Arrays.asList(livros);
		
		List<Livro> livros = livrosServices.listar();	
		return ResponseEntity.status(HttpStatus.OK).body(livros);		
	}
	
	/**
	 * Criar um novo livro no BD
	 * @implNote O método POST é responsável por criar um novo recurso
	 * @param livro
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {		
		
		livro = livrosServices.save(livro);
		//Criando um URI para acrescentar no cabeçalho de retorno através da variável Location,
		//a uri para se obter o recurso criado. 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		
		try {
			Livro livro = livrosServices.buscar(id);	
			return ResponseEntity.ok(livro);
			//return ResponseEntity.status(HttpStatus.OK).body(livro);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}			
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		
		try {			
			livrosServices.deletar(id);		
			return ResponseEntity.noContent().build();
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();						
		}
		catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@PathVariable("id") Long id, @RequestBody Livro livro) {		
		
		try {
			livro.setId(id);
			livrosServices.atualizar(livro);		
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();						
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	

}
