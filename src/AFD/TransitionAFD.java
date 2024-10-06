package AFD;

import java.util.Objects;

public class TransitionAFD {

    private String fromState;
    private String toState;
    private char symbol;

    public TransitionAFD(String fromState, char symbol, String toState) {
        this.fromState = fromState;
        this.toState = toState;
        this.symbol = symbol;
    }

    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return (" " + this.fromState + " '" + this.symbol + "' " + this.toState);
    }

    /**
     * Devuvelve el código hash del objeto, usado para ser comparado con otro
     * objeto en colecciones (HashSet, HashMap..)
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7; // Se inicia con un valor base hash de 7.
        hash = 97 * hash + Objects.hashCode(this.fromState);
        hash = 97 * hash + Objects.hashCode(this.toState);
        hash = 97 * hash + this.symbol;
        return hash;
    }

    //Objetivo de hashCode() en la clase de transición:
    //Identificación Única:
    //Las transiciones en un AFD suelen definirse por su estado de origen, símbolo de entrada y estado de destino. Al implementar hashCode(), se asegura que cada transición pueda ser identificada de manera única en colecciones como HashSet o HashMap.
    //Eficiencia en Colecciones:
    //Las colecciones basadas en hash (como HashSet o HashMap) utilizan el código hash para organizar los elementos. Esto permite búsquedas, inserciones y eliminaciones más rápidas (en tiempo constante promedio). Si las transiciones son almacenadas en un HashSet, el hashCode() ayuda a determinar rápidamente si una transición ya existe o no.
    //Comparación de Objetos:
    //Junto con el método equals(), el hashCode() permite comparar dos objetos de transición. Si dos objetos tienen el mismo código hash, se puede verificar si son iguales utilizando el método equals(). Esto es fundamental para mantener la integridad de las colecciones.

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // Verifica si ambos objetos son la misma instancia en memoria. Si es así, son iguales y se retorna true.
            return true;
        }
        if (obj == null) { // Comprueba si el objeto pasado como argumento es null. Si lo es, no pueden ser iguales, por lo que se retorna false.
            return false;
        }
        if (getClass() != obj.getClass()) { // Asegura que el objeto obj es de la misma clase (TransicionAFD). Si no lo es, se retorna false.
            return false;
        }
        final TransitionAFD other = (TransitionAFD) obj; // "casting" del objeto obj a tipo TransicionAFD. Esto es necesario porque obj es de tipo Object, y queremos acceder a los atributos específicos de TransicionAFD. Si obj no es una instancia de TransicionAFD, ya se ha verificado anteriormente que no se ejecutará esta línea.
        if (this.symbol != other.symbol) {
            return false; // Compara el atributo simbolo de la instancia actual (this) con el de other. Si son diferentes, retorna false, lo que indica que las dos transiciones no son iguales.
        }
        if (!Objects.equals(this.fromState, other.fromState)) { //Además nos fijamos en los estado origen y destino
            return false; // Utiliza Objects.equals() para comparar fromState de ambas instancias. Este método maneja correctamente comparaciones de null, retornando true si ambos son null o si son iguales, y false en caso contrario. Si son diferentes, se retorna false.
        }
        return Objects.equals(this.toState, other.toState); // Se hace lo mismo con estadoD, comparando el estado de destino de ambas transiciones. Si son diferentes, retorna false.
        // Si todas las comparaciones son exitosas, se retorna true, indicando que los objetos son considerados iguales.
    }


}
