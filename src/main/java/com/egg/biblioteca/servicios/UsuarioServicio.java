package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Margarita_Bravo
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2)throws MiException{
        validar(nombre, email, password, password2);
        
        Usuario usuario= new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
    }
    
    private void validar(String nombre, String email, String password, String password2)throws MiException{
        if(nombre.isEmpty()|| nombre==null){
            throw new MiException("El nombre no puede ser  nulo o estar vacio");
        }
        if(nombre.isEmpty()|| email==null){
            throw new MiException("El email no puede ser  nulo o estar vacio");
        }
        if(nombre.isEmpty()|| password==null || password.length() <= 5){
            throw new MiException("La contraseña no puede ser  nulo y debe tener mas de 5 digitos");
        }
        if(nombre.isEmpty()|| password2==null){
            throw new MiException("Las contraseñas ingresadas deben coinsidir");
        }
    }

    @Override//cuando un usuario se loguee otorgara los permisos a lo q tiene acceso el usuario
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//autenticamos al usuario atraves del email
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if(usuario != null){
            
            List<GrantedAuthority>permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());//role_user
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(),permisos);
        }else{
            return null;
        }
    }
}
