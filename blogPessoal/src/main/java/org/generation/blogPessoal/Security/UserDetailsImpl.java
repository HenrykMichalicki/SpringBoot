package org.generation.blogPessoal.Security;

import java.util.Collection;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L; // controle interno L, pois a variável é do tipo Long
	
	private String userName;
	private String password;
	
	//recebendo o email e senha do usuario no banco
	public UserDetailsImpl(Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}
	
	public UserDetailsImpl () {
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	// implementar as respostas dos métodos
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //**
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
