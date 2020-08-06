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

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> findAll() {
		List<Pessoa> list = pessoaRepository.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSave = pessoaRepository.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(pessoaSave.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(pessoaSave);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Pessoa>> findById(@PathVariable Long id) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		return pessoa.isPresent() ? ResponseEntity.ok().body(pessoa) : ResponseEntity.notFound().build() ;
	}
}
