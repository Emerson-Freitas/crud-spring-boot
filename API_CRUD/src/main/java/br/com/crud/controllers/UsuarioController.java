package br.com.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.models.UsuarioModel;
import br.com.crud.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/lista")
	@ResponseBody
	public ResponseEntity<List<UsuarioModel>> usuarios() {

		return new ResponseEntity<List<UsuarioModel>>(usuarioService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<UsuarioModel> cadastraUsuario(@RequestBody UsuarioModel usuarioModel) throws Exception {

		return new ResponseEntity<UsuarioModel>(usuarioService.save(usuarioModel), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> deletarUsuario(@PathVariable(name = "id") Long id) throws Exception{
		
		return new ResponseEntity<String>(usuarioService.delete(id), HttpStatus.OK);
	}
	

	@PutMapping("/")
	@ResponseBody
	public ResponseEntity<UsuarioModel> atualizaUsuario(@RequestBody UsuarioModel usuario) throws Exception {

		return new ResponseEntity<UsuarioModel>(usuarioService.atualizaUsuario(usuario), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<UsuarioModel> pesquisaUsuario(@PathVariable(name = "id") Long id) throws Exception {

		return new ResponseEntity<UsuarioModel>(usuarioService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/buscarPorNome/{nome}")
	@ResponseBody
	public ResponseEntity<List<UsuarioModel>> buscarPorNome(@PathVariable(name = "nome") String nome){
		
		return new ResponseEntity<List<UsuarioModel>>(usuarioService.buscarPorNome(nome), HttpStatus.OK);
		
	}

	@DeleteMapping("/deletaTodos")
	public ResponseEntity<String> deletaTodos() throws Exception{
		return new ResponseEntity<String>(usuarioService.deleteAll(), HttpStatus.OK);
	}
}
