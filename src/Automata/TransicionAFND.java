package Automata;

import java.util.HashSet;
import java.util.Objects;

/**
 * Clase TransicionAFND. Los estados son cadenas de caracteres (String). El
 * símbolo de entrada es un caracter (Character).
 *
 * @author Grupo Automatas # 2
 */
public class TransicionAFND {

    private String origen;
    private HashSet<String> destinos;
    private char simbolo;

    /**
     * Crea la transición con los parámetros indicados
     *
     * @param origen Estado origen
     * @param simbolo Símbolo de entrada
     * @param destinos Conjunto de estados destino
     */
    public TransicionAFND(String origen, char simbolo, HashSet<String> destinos) {
        this.origen = origen;
        this.destinos = new HashSet<>(destinos); // Asegúrate de que se crea una copia
        this.simbolo = simbolo;
    }

    /**
     * Devuelve el estado de origen
     *
     * @return
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Devuelve el conjunto de estados de destino
     *
     * @return
     */
    public HashSet<String> getDestinos() {
        return destinos;
    }

    /**
     * Devuelve el simbolo de la transicion
     *
     * @return
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     * Permite la representación en texto de la transicion
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder mensaje = new StringBuilder(" " + this.origen + " '" + this.simbolo + "'" + this.destinos.toString());

        return mensaje.toString();
    }

    /**
     * Devuvelve el código hash del objeto, usado para ser comparado con otro
     * objeto en colecciones (HashSet, HashMap..)
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5; // El valor inicial del hash (en este caso, 5) y el número por el cual se multiplica (71) son elecciones de diseño que se utilizan para ayudar a distribuir mejor los códigos hash y minimizar colisiones. El 5 es arbitrario
        hash = 71 * hash + Objects.hashCode(this.origen); // El número 71 se elige a menudo porque es un número primo. Usar números primos en la multiplicación ayuda a distribuir los códigos hash de manera más uniforme en la tabla hash, lo que reduce la posibilidad de colisiones
        hash = 71 * hash + Objects.hashCode(this.destinos);
        hash = 71 * hash + this.simbolo;
        return hash;
    }

    /**
     * Devuelve verdadero si la transición pasada por parámetro equivale a la
     * que invoca el método
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransicionAFND other = (TransicionAFND) obj;
        if (this.simbolo != other.simbolo) {
            return false;
        }
        if (!Objects.equals(this.origen, other.origen)) {
            return false;
        }
        return Objects.equals(this.destinos, other.destinos);
    }

}
