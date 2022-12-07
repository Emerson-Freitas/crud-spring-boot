package br.com.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.models.UsuarioModel;
import br.com.crud.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<UsuarioModel> findAll(){
		
		return usuarioRepository.findAll();
	}
	
	public UsuarioModel save(UsuarioModel usuarioModel) throws Exception {
		
		if(usuarioModel.getNome() == "" || usuarioModel.getIdade() < 1 || usuarioModel.getEmail() == "") {
			throw new Exception("Por favor, preencha corretamente os campos!");
		}
		
		return usuarioRepository.save(usuarioModel);
	}
	
	public String delete(Long id) throws Exception {
		
		Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
		
		if(!usuario.isPresent()) {
			throw new Exception("O usuario com o id " + id + " ainda não foi cadastrado!");
		}
		
		usuarioRepository.deleteById(id);
		return "Usuario com o id " + id + " deletado com sucesso!";
	}
	
	public String deleteAll() throws Exception {
		
		List<UsuarioModel> usuarios = usuarioRepository.findAll();
		if(usuarios.isEmpty()) {
			throw new Exception("Erro, a lista de usuarios está vazia!");
		}
		
		usuarioRepository.deleteAll();
		return "Todos os usuarios foram deletados com sucesso!";
	}
	
	public UsuarioModel findById(Long id) throws Exception {
		Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
		
		if(!usuario.isPresent()) {
			throw new Exception("O usuario com o id " + id + " não está cadastrado!");
		}
		return usuarioRepository.getById(id);
	}
	
	public UsuarioModel atualizaUsuario(UsuarioModel usuarioModel) throws Exception {
		
		if(usuarioModel.getNome() == "" || usuarioModel.getIdade() < 1 || usuarioModel.getEmail() == "") {
			throw new Exception("Por favor preencha todos os campos!");
		}
		
		return usuarioRepository.saveAndFlush(usuarioModel);
	}
	
	public List<UsuarioModel> buscarPorNome(String nome){
		return usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
	}
}
