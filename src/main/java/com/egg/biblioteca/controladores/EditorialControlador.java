package com.egg.biblioteca.controladores;

import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
   @Autowired
   private EditorialServicio editorialServicio;
   
   
   @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException{
        try{
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito","el  editor fue cargado con exito");
        }catch(MiException ex){
            modelo.put("error",ex.getMessage());
            return "editorial_form.html";
        }
        System.out.println("mi prueba para cargar nombre editorial :"+ nombre);
        return "index.html";
    }
}
  
