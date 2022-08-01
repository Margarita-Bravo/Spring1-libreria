package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Margarita_Bravo
 */
@Controller
@RequestMapping("/autor")//localhost:8080/autor-->entro al index pero si agrego /autor entra a este componente
public class AutorControlador {//controlador propio para autores
    
    @Autowired//
    private AutorServicio autorServicio;//para utilizar sus metodos
    
    
    
    //metodo de registro q abre la vista de autor_form/responde al llamado de la url
    @GetMapping("/registrar") //metodo de registro q abre la vista de aca-->localhost:8080/autor/registrar
    public String registrar(){//retorna el html que queremos q se renderice
        
        return "autor_form.html";//retorna el html q queremos q se renderice 
        
    }
    
    //mappeamos nuestro primer controlador postmapping q es el q recibe la info del form
    @PostMapping("/registro")//atravez de un metodo post en html viaja la info del form
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiException{//r3ecibe el nombre q envian desde input aUTOR_FORM /para indicarle q es un parametro q viaj se lo marca con requestparam para q sea obligatoria
        try{
            autorServicio.crearAutor(nombre);//llamamos al metodo para crear
            modelo.put("exito","El autor fue cargado correctamente");
        }catch(MiException ex){
            modelo.put("error",ex.getMessage());
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            return "autor_form.html";
        }
        
        System.out.println("Prueba carga Nombre autor:"+nombre);//solo prueba para ver si recibe el nombre ingresado por input
        return "index.html";
        
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Autor>autores=autorServicio.listaAutor();
        modelo.addAttribute("autores",autores);
        
        return "autor_list.html";
        
    }
    
    @GetMapping("/modificar/{id}")//la variable id viaja atraves de esta url
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
        
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try{
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        }catch(MiException ex){
            modelo.put("error",ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
