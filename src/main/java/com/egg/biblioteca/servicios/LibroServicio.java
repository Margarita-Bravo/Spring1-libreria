package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Margarita_Bravo
 */
 //se escriben metodos para q la app cumpla con los requisitos del cliente
    //permitir crear libro.habilitar, etc
@Service
public class LibroServicio {
    
   @Autowired//le indicamos al servidor de app q esta variable es inicializada por el
    private LibroRepositorio libroRepositorio;//instanciamos como atributo global de esta clase 
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
   
    //metodo crear libro
    @Transactional//mensiono q metodo efectua una transaccion en la base de datos
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiException{//damos de alta un libro creamos una entidad
    
    
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);//llamamos metodo validar
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    //metodo retorna una lista de objetos de determionada entidad en este caso libros
    public List<Libro>ListarLibro(){//enlistamos la entidad
        List<Libro> libros = new ArrayList();//la lista se va a rellenar con los que nos retorne el metodo repositorio
        libros =libroRepositorio.findAll();//metodo que trae jparepository/encuantra todos los libros/todos los datos
        return libros;
    }
    /*con este metodo modificar q esta vinculado atraves del id tiene q buscar cual es el libro vinculado a esta id para
    poder modificar sus datos, haciendo uso del metodo repositorio findById nos va a devolver una clase opctional como 
    respuesta, preguntamos si el resultado esta presente y si es true encuantra un autor o libro con esa editorial lo 
    tare y le seteamos y cambiamos lo q deseemos*/
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)throws MiException{//modificamos la entidad haciendo uso de la clase optional
       
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);//llamamos metodo validar
        
        Optional<Libro>respuesta=libroRepositorio.findById(isbn);//objeto contenedor si el valor esta presente en el formulario devuelve true y retorna el valor con el metdo get
        Optional<Autor>respuestaAutor=autorRepositorio.findById(idAutor);
        Optional<Editorial>respuestaEditorial=editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        
        
        if(respuesta.isPresent()){//si esta respuest existe es true entonces modificame el libro
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        }
    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiException{
        if (isbn == null) {//validacion para saber que los atributos enten llegando lleno
            throw new MiException("El isbn no puede ser nulo");
        }
        if(titulo.isEmpty() || titulo==null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares == null) {
            throw new MiException("Ejemplares no puede ser nulo");
        }
        if(idAutor.isEmpty() || idAutor==null){
            throw new MiException("El autor no puede ser nulo o estar vacio");
        }
         if(idEditorial.isEmpty() || idEditorial==null){
            throw new MiException("Editorial no puede ser nulo o estar vacio");
        }
    }
}
