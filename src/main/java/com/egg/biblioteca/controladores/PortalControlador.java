package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 *
 * @author Margarita_Bravo
 */
/*a este controlador le indicamos al framerwork de spring q es de tipo cotroler*/
@Controller
@RequestMapping("/")//configura cual es la url q va a escuhar a este controlador a partir de la barra
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")//mapea la url cuando se haya ingresado la barra
    public String index() {//metodo para acceder a traves de una operacion get http
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
             @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");
            
            return "index.html";
        } catch (MiException ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error",ex.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("email",email);
            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required=false)String error, ModelMap modelo){
        if (error != null) {
            modelo.put("error", "Usuario o contrraseña invalidos!");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")//al cerrar sesion no permitir ir a otra pagina sin antes loguerte
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if(logueado.getRol().toString().equals("ADMIN")){//si es un admin se loguea va directamente a cargar libros no a inico o home
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }
}
