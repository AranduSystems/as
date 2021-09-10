/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author armando
 */
public class Utilitarios {

    public Utilitarios() {
    }

    /*
    Este metodo, verificara si el valor ingresado es de tipo Numerico o es de tipo caracter para evitar
    errores al momento de ejecucion donde se realize el parseo de numeros.
     */
    public static boolean comprobarTipoDato(String valor) {
        boolean resultado = false;
        try {
            Integer.parseInt(valor);
            resultado = true;
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }
}
