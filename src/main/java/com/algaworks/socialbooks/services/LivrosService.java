package com.algaworks.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {
	
	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	public List<Livro> listar() {
		return livrosRepository.findAll();		
	}
	
	public Livro buscar(Long id) {
		Livro livro = livrosRepository.findOne(id);
		
		if(livro == null) {
			throw new LivroNaoEncontradoException();
		}		
		
		return livro;
	}

	public Livro save(Livro livro) {
		//Garantindo que irá salvar em vez de atualizar
		livro.setId(null);		
		return livrosRepository.save(livro);		
		
	}
	
	public void deletar(Long id) {
		
		try {
			livrosRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {			
			throw new LivroNaoEncontradoException();
		}		
	}
	
	public void atualizar(Livro livro) {
		Boolean livroExiste = verificarExistencia(livro);
		if(livroExiste) {
			livrosRepository.save(livro);
		} else {
			throw new LivroNaoEncontradoException();
		}
	}
	
	private Boolean verificarExistencia(Livro livro) {
		Livro livroSaved = buscar(livro.getId());
		return livroSaved != null;
	}
	
	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscar(livroId);
		
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);		
	}
	

}
