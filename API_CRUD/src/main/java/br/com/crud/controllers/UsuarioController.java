package br.com.crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.models.UsuarioModel;
import br.com.crud.repositories.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/lista")
	@ResponseBody
	public ResponseEntity<List<UsuarioModel>> usuarios() {

		List<UsuarioModel> listaUsuarios = usuarioRepository.findAll();

		return new ResponseEntity<List<UsuarioModel>>(listaUsuarios, HttpStatus.OK);
	}

	@PostMapping("/cadastrar")
	@ResponseBody
	public ResponseEntity<?> cadastraUsuario(@RequestBody UsuarioModel usuarioModel) {

		String mensagem = "";

		if (usuarioModel.getNome() == null || usuarioModel.getNome() == "") {

			mensagem = "Por favor, digite o nome do usuario!";
			return new ResponseEntity<String>(mensagem, HttpStatus.BAD_REQUEST);
		}

		if (usuarioModel.getEmail() == null || usuarioModel.getEmail() == "") {
			mensagem = "Por favor, digite o email do usuario";
			return new ResponseEntity<String>(mensagem, HttpStatus.BAD_REQUEST);
		}

		if (usuarioModel.getIdade() < 0) {
			mensagem = "Por favor, digite a idade correta do usuario!";
			return new ResponseEntity<String>(mensagem, HttpStatus.BAD_REQUEST);
		}

		UsuarioModel usuario = usuarioRepository.saveAndFlush(usuarioModel);
		return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.CREATED);
	}

	@DeleteMapping("/deletar")
	@ResponseBody
	public ResponseEntity<String> deletarUsuario(@RequestParam(name = "id") Long id){
		
		Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
		
		String mensagem = "";
		
		if(!usuario.isPresent()) {
			mensagem = "Usuario com o ID: " + id + " não foi encontrado!"; 
			return new ResponseEntity<String>(mensagem, HttpStatus.NOT_FOUND);
		}
		
		usuarioRepository.deleteById(id);
		mensagem = "Usuario com o ID " + id + " deletado com sucesso!";
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}
	

	@PutMapping("/atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizaUsuario(@RequestBody UsuarioModel usuario) {

		String mensagem = "";

		Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(usuario.getId());

		if (!usuarioOptional.isPresent()) {

			mensagem = "Erro, não existe nenhum usuario no nosso banco de dados com o id " + usuario.getId();

			return new ResponseEntity<String>(mensagem, HttpStatus.NOT_FOUND);
		}

		UsuarioModel usuarioModel = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<UsuarioModel>(usuarioModel, HttpStatus.OK);
	}

	@GetMapping("/pesquisarUsuario")
	@ResponseBody
	public ResponseEntity<?> pesquisaUsuario(@RequestParam(name = "id") Long id) {

		String mensagem = "";

		Optional<UsuarioModel> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			mensagem = "Erro, não existe nenhum usuario com o ID: " + id + " no nosso banco de dados!";
			return new ResponseEntity<String>(mensagem, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<UsuarioModel>(usuarioRepository.getById(id), HttpStatus.OK);
	}
	
	@GetMapping("/buscarPorNome{nome}")
	@ResponseBody
	public ResponseEntity<List<UsuarioModel>> buscarPorNome(@PathVariable(name = "nome") String nome){
		
		List<UsuarioModel> usuarios = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
		
	}

	@DeleteMapping("/deletaTodos")
	public ResponseEntity<String> deletaTodos(){
		usuarioRepository.deleteAll();
		String mensagem = "Usuarios deletados com sucesso!";
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
		
	}
}
