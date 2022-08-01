package com.egg.biblioteca;

import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Margarita_Bravo
 */
@Configuration//importamos para avisar que es una configuraion de seguridad
@EnableWebSecurity//activamos la seguridad web
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter{//extiende de configurity
    
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @Autowired//CODIFICAMOS LA CONTRASEÑA DEL USUARIO PARA MAYOT SEGURIDAD
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
        
          
    }
    
    @Override
    protected void configure(HttpSecurity http)throws Exception{//recibe un ob de tipo http
        http
            .authorizeHttpRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*","/js/*","/img/*","/**")
                .permitAll()//autorizamos determinados parametros ej las rutas css, js
            .and().formLogin()
                .loginPage("/login")//para q el usuario pueda iniciar sesion
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")
                .permitAll()
            .and().logout()
                .logoutUrl("/logout")//para cerrar sesion
                .logoutSuccessUrl("/login")
                .permitAll()
            .and().csrf()
                .disable();
                
                
                
                 
        
    }
}
