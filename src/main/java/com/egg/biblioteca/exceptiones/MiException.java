package com.egg.biblioteca.exceptiones;

/**
 *
 * @author Margarita_Bravo
 */
public class MiException extends Exception{
    //generamos esta clase para diferenciar los errores q tengamos
    //en nuestra logica de negocio de los errores y back propios del sistema
    public MiException(String mensaje) {
        super(mensaje);
    }
    
}
