package com.egg.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Margarita_Bravo
 */
@Entity
public class Autor {
    @Id
    @GeneratedValue(generator = "uuid")//Queremos q el id se genere de forma automatica al momento del q repositoria persista la entidad
    @GenericGenerator(name = "uuid", strategy = "uuid2")//como alternativa de generador id en cadena de texto
    private String id;
    private String nombre;

    public Autor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
