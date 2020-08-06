package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoryRepository;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoryRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria category, HttpServletResponse response) {
		Categoria categorySave = categoryRepository.save(category);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(categorySave.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(categorySave);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Categoria>> findById(@PathVariable Long id) {
		Optional<Categoria> category = categoryRepository.findById(id);
		return category.isPresent() ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
	}
	
}
