package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Margarita_Bravo
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @GetMapping("/dashboard")//todo los relacionado a paneles de administracion
        public String panelAdministrativo(){
            return "panel.html";
        }
        
    @GetMapping("/libro/registrar")//todo los relacionado a paneles de administracion
        public String panelAdministrativoLibro(){
            
            return "libro_form.html";
        }
   
    @GetMapping("/autor/registro")
        public String panelAdministrartivoAutor(){
            return "autor_form.html";
        }
}
