package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Margarita_Bravo
 */
@Repository//marcamos el componente como repositorio
public interface LibroRepositorio extends JpaRepository<Libro, Long>{//creamos una interfaz q va a extender de JPArepositori manejando entidad libro cuya llave id es long 
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")//creamos la query para la busqueda y agregamos la consulta
    public Libro buscarPorTitulo(@Param("titulo") String titulo);//en los parametros del metodo se vincula con los campos de la query que corresponde 

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor(@Param("nombre") String nombre);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre")
        public List<Libro> buscarPorEditorial(@Param("nombre")String nombre);
}
