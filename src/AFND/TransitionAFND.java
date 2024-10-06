package AFND;

import java.util.HashSet;
import java.util.Objects;
public class TransitionAFND {

    private String start;
    private HashSet<String> destinies;
    private char symbol;

    public TransitionAFND(String start, char symbol, HashSet<String> destinies) {
        this.start = start;
        this.destinies = new HashSet<>(destinies);
        this.symbol = symbol;
    }

    public String getStart() {
        return start;
    }

    public HashSet<String> getDestinies() {
        return destinies;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return " " + this.start + " '" + this.symbol + "'" + this.destinies.toString();
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
        hash = 71 * hash + Objects.hashCode(this.start); // El número 71 se elige a menudo porque es un número primo. Usar números primos en la multiplicación ayuda a distribuir los códigos hash de manera más uniforme en la tabla hash, lo que reduce la posibilidad de colisiones
        hash = 71 * hash + Objects.hashCode(this.destinies);
        hash = 71 * hash + this.symbol;
        return hash;
    }

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
        final TransitionAFND other = (TransitionAFND) obj;
        if (this.symbol != other.symbol) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        return Objects.equals(this.destinies, other.destinies);
    }


}
