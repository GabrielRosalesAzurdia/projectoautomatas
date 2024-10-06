package AFND;

import java.util.HashSet;
import java.util.Objects;

/**
 * Clase TransicionL Los estados son cadenas de caracteres (String).
 *
 * @author Grupo Automatas # 2
 */
public class TransitionEpsilon {

    private String start;
    private HashSet<String> destinies;

    public TransitionEpsilon(String start, HashSet<String> destinies) {
        this.start = start;
        this.destinies = destinies;
    }

    public String getStart() {
        return start;
    }

    public HashSet<String> getDestinies() {
        return destinies;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder(" " + this.start);

        for (String valor : this.destinies) {
            message.append(" ").append(valor);
        }

        return message.toString();
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
        hash = 59 * hash + Objects.hashCode(this.start);
        hash = 59 * hash + Objects.hashCode(this.destinies);
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
        final TransitionEpsilon other = (TransitionEpsilon) obj;
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.destinies, other.destinies)) {
            return false;
        }
        return true;
    }


}
