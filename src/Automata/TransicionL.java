package Automata;

import java.util.HashSet;
import java.util.Objects;

/**
 * Clase TransicionL Los estados son cadenas de caracteres (String).
 *
 * @author Grupo Automatas # 2
 */
public class TransicionL {

    private String origen;
    private HashSet<String> destinos;

    /**
     * Crea la transición con los parámetros indicados
     *
     * @param origen Estado origen
     * @param destinos Conjunto de estados
     */
    public TransicionL(String origen, HashSet<String> destinos) {
        this.origen = origen;
        this.destinos = destinos;
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
     * Permite la representación en texto de la transicion
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder mensaje = new StringBuilder(" " + this.origen);

        for (String valor : this.destinos) {
            mensaje.append(" ").append(valor);
        }

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
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.origen);
        hash = 59 * hash + Objects.hashCode(this.destinos);
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
        final TransicionL other = (TransicionL) obj;
        if (!Objects.equals(this.origen, other.origen)) {
            return false;
        }
        if (!Objects.equals(this.destinos, other.destinos)) {
            return false;
        }
        return true;
    }

}
