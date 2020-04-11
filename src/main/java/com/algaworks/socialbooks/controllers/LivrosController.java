package com.algaworks.socialbooks.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
/**
 * 
 * @author Rodrigo
 * @apiNote quando acessar http://localhost:8080/h2-console/, colocar jdbc:h2:mem:testdb para JDBC URL.
 */
@RestController
@RequestMapping("/livros")
public class LivrosController {
	
	@Autowired
	private LivrosRepository livrosRepository;
	
	/**
	 * Obtém todos os livros cadastrados
	 * @implNote O método GET obtém um ou mais recursos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Livro> listar() {
		
//		Livro l1 = new Livro("Rest Aplicado");
//		Livro l2 = new Livro("Git passo a passo");
//		
//		Livro[] livros = {l1,l2};
//		
//		return Arrays.asList(livros);
		
		return livrosRepository.findAll();
		
	}
	
	/**
	 * Criar um novo livro no BD
	 * @implNote O método POST é responsável por criar um novo recurso
	 * @param livro
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {		
		
		livro = livrosRepository.save(livro);
		//Criando um URI para acrescentar no cabeçalho de retorno através da variável Location,
		//a uri para se obter o recurso criado. 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Livro livro = livrosRepository.findOne(id);
		
		if(livro == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(livro);
		//return ResponseEntity.status(HttpStatus.OK).body(livro);		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deletar(@PathVariable("id") Long id) {
		livrosRepository.delete(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Livro atualizar(@PathVariable("id") Long id, @RequestBody Livro livro) {		
		livro.setId(id);
		return livrosRepository.save(livro);
	}
	
	

}
