package org.generation.blogPessoal.Service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64; // inserir manualmente essa biblioteca e apagar a anterior Base64
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) // pesquisa se o USUÁRIO existe no banco

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe no banco!", null);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		// verificando se o id existe no banco
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			// verificando se o usuário já está cadastrado
			Optional<Usuario> usuarioCad = usuarioRepository.findByUsuario(usuario.getUsuario());

			// verificando se usuario já tem o email de alteração cadastrado por outro
			// usuário
			if (usuarioCad.isPresent() && (usuarioCad.get().getId() != usuario.getId())) { //compara os ids, se forem iguais, ele altera. se for diferente, ele não autoriza

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail do usuário já cadastrado!", null);

			} else {
				///// ja estava não mexe
				// recebendo a data de nascimento
				int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

				// verificando se o usuário está logado
				if (idade < 18) {

					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário menor de 18 anos.", null);

				} else {

					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

					String senhaEncoder = encoder.encode(usuario.getSenha());
					usuario.setSenha(senhaEncoder);

					return Optional.of(usuarioRepository.save(usuario));
				}
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

		}

	}

	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> usuarioLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) { // compara a senha
																							// encriptada com a senha
																							// normal, dai traz as
																							// respostas abaixo

				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(authHeader);

				return usuarioLogin;

			}
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuário ou senha inválidos!", null);

	}

}
