package org.serratec.api.h2banco.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.api.h2banco.model.Livro;
import org.serratec.api.h2banco.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
@Validated
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;

	@GetMapping
	public List<Livro> listarTodos() {
		return livroRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
		Optional<Livro> livro = livroRepository.findById(id);
		return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Livro criar(@Valid @RequestBody Livro livro) {
		return livroRepository.save(livro);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
		if (!livroRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		livro.setId(id);
		livro = livroRepository.save(livro);
		return ResponseEntity.ok(livro);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!livroRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		livroRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}