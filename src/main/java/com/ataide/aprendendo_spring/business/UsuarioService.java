package com.ataide.aprendendo_spring.business;

import com.ataide.aprendendo_spring.infrastructure.entity.Usuario;
import com.ataide.aprendendo_spring.infrastructure.exceptions.ConflictException;
import com.ataide.aprendendo_spring.infrastructure.exceptions.ResourceNotFoundException;
import com.ataide.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.net.PasswordAuthentication;

@Service
@RequiredArgsConstructor

public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GlobalAuthenticationConfigurerAdapter enableGlobalAuthenticationAutowiredConfigurer;

    public Usuario salvaUsuario(Usuario usuario){
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
    }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado", e.getCause());

        }
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email ja cadastrado" + email);
            }
        }catch (ConflictException e){
            throw  new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscaUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deleteUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }


}
