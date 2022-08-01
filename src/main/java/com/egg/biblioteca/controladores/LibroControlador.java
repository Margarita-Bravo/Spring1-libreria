package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/libro")
public class LibroControlador {//gestionar todo con libros
    //necesitamos acceso a estos datos para manipular el libro
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")//localhost:8080/libro/registrar
    public String registrar(ModelMap modelo){//atreves del modelo puedo inyectar todo lo que tenga libro
        List<Autor>autores=autorServicio.listaAutor();
        List<Editorial>editoriales=editorialServicio.listarEditoriales();
        
        //anclamos esto para q pueda ser usado en la interfaz de usuario
        modelo.addAttribute("autores",autores);//mando toda mi lista al modelo
        modelo.addAttribute("editoriales",editoriales);
        
        return "libro_form.html";//renderiza el html del form libro
    }
    
    @PostMapping("/registro")//recibe los datos del formulario para hacer los llamados necesarios para persistir
    public String registro(@RequestParam(required=false) Long isbn, @RequestParam String titulo,//reqired para evitar error si no ingresan numero 
          @RequestParam(required=false) Integer ejemplares,@RequestParam String idAutor, 
          @RequestParam String idEditorial, ModelMap modelo){//modelos sirven para que insertemos en este modelo toda la info q se muestre x pantalla o para lo q se necsite en la interfaz del usuario en este caso mjs error
        try{
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);//si todo sale bien creo un libro y me lleva al index
            modelo.put("exito","El libro fue cargado correctamente");
            
        }catch(MiException ex){
            List<Autor>autores=autorServicio.listaAutor();
            List<Editorial>editoriales=editorialServicio.listarEditoriales();
            modelo.addAttribute("autores",autores);//mando toda mi lista al modelo
            modelo.addAttribute("editoriales",editoriales);
                modelo.put("error", ex.getMessage());//si falla se ve este error en la pagina
            //Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null,ex);//VISTO X CONOSLA si no envio error y renderizo nuevamente el formulario
            return "libro_form.html";//volvemos a cargar el formulario
        }
        System.out.println("prueba carga de libro, isbn :"+isbn+" , titulo :"+titulo+", ejemplares :"+ejemplares+", autor :"+idAutor+", idEditorial :"+idEditorial);
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro>libros=libroServicio.ListarLibro();
        modelo.addAttribute("libros",libros);
        
        return"libro_list";
    }
}
