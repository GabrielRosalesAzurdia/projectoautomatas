package Automata;

/**
 * Interface que implementan los AFD y AFND
 * @author Grupo Automatas # 2
 */
public interface Proceso {
    public abstract boolean esFinal(String estado); // True si estado es un estado final
    public abstract boolean reconocer(String cadena) throws Exception; // True si la cadena es reconocida
    public abstract String toString(); // Muestra las transiciones y estados finales
}
