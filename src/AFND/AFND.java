package AFND;
import AFD.AFD;
import core.Matcher;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AFND implements Cloneable, Matcher {

    private HashSet<String> finalStates;
    private String startState = "";
    private HashSet<TransitionAFND> transitions;
    private HashSet<TransitionEpsilon> transitionsEpsilon;

    public AFND() {
        this.finalStates = new HashSet();
        this.transitions = new HashSet();
        this.transitionsEpsilon = new HashSet();
    }

    public void addTransition(String startState, char symbol, HashSet toStates) {
        this.transitions.add(new TransitionAFND(startState, symbol, toStates));
    }

    public void addTransition(TransitionAFND trans) {
        this.transitions.add(trans);
    }

    public void addTransitionEpsilon(String startState, HashSet toStates) {
        this.transitionsEpsilon.add(new TransitionEpsilon(startState, toStates));
    }

    public void addTransitionEpsilon(TransitionEpsilon trans) {
        this.transitionsEpsilon.add(trans);
    }

    public HashSet<String> getTransition(String state, char symbol) {
        for (TransitionAFND t : this.transitions) {
            if (t.getStart().equals(state) && t.getSymbol() == symbol) {
                return t.getDestinies();
            }
        }

        return new HashSet<>(); //Si no se encuentra
    }

    public HashSet<String> getTransition(HashSet<String> macroState, char symbol) {
        HashSet<String> searchResult = new HashSet();

        for (String state : macroState) {
            searchResult.addAll(this.getTransition(state, symbol));
        }

        return searchResult;
    }

    public HashSet<String> getTransitionEpsilon(String state) {
        for (TransitionEpsilon sol : this.transitionsEpsilon) {
            if (sol.getStart().equals(state)) {
                return sol.getDestinies();
            }
        }

        return new HashSet();
    }

    @Override
    public boolean isFinalState(String state) {
        return this.finalStates.contains(state);
    }

    public boolean isFinalState(HashSet<String> macroState) {
        for (String state : macroState) {
            if (this.isFinalState(state)) {
                return true;
            }
        }

        return false;
    }

    public HashSet<String> epsilon_closure(String state) {
        HashSet<String> searchResult = new HashSet<>();
        searchResult.add(state);                   //Añadimos el estado actual

        transitionsEpsilon.forEach((transitionEpsilon) -> { //Recorremos las L-Transiciones
            if (transitionEpsilon.getStart().equals(state)) { //con origen ese estado
                transitionEpsilon.getDestinies().forEach((destinyState) -> {
                    //Y añadimos a la solucion todos los estados de la LC del destino
                    if(!destinyState.equals(transitionEpsilon.getStart()))//Evitamos un bucle infinito
                        searchResult.addAll(epsilon_closure(destinyState));
                });
            }
        });

        return searchResult;
    }

    public HashSet<String> epsilon_closure(HashSet<String> states) { //Devuelve el cjto de estados CL(estados)
        HashSet<String> toReturn = new HashSet();

        states.forEach((state) -> {
            HashSet<String> values = epsilon_closure(state); //Aplicamos la clausura a cada estado del conjunto

            //Añadimos cada destino obtenido
            toReturn.addAll(values);

        });

        return toReturn;
    }

    @Override
    public boolean matchString(String string) throws Exception {
        //CONTROL DE EXCEPCIONES
        if (this.startState.isEmpty()) {
            throw new Exception("Error: no start state!");
        }
        if (this.getFinalStates().isEmpty()) {
            throw new Exception("Error: no final states!");
        }
        
        char[] symbol = string.toCharArray();
        HashSet<String> state = new HashSet();
        state.add(this.getStartState());
        state = epsilon_closure(state); //Partimos de la clausura del estado inicial

        for (char c : symbol) {

            state = getTransition(state, c); //Primero evolucionamos consumiendo simbolo

            //Añadimos la clausura de todos los estados destino
            state.addAll(epsilon_closure(state));

            if (state.isEmpty()) {
                throw new Exception("Error: transition with string '" + c + "' not valid!");
            }

        }

        return isFinalState(state);
    }

    public void deleteSymbol(char s) {
        this.transitions.removeIf(t -> t.getSymbol() == s);
    }

    public void deleteState(String e) //
    {
        //Elimina las transiciones con origen e
        HashSet<TransitionAFND> toDelete = new HashSet();
        for (TransitionAFND t : this.transitions) {
            if (t.getStart().equals(e)) {
                toDelete.add(t); //Eliminar transicion
            }
            //Elimina las ocurrencias en los destinos de la transicion
            HashSet<String> toDeleteDestinies = new HashSet();
            for (String estado : t.getDestinies()) {
                if (estado.equals(e)) {
                    toDeleteDestinies.add(e); //Eliminar estado del destino
                }
            }
            t.getDestinies().removeAll(toDeleteDestinies);
        }
        this.transitions.removeAll(toDelete);

        //Elimina las transiciones L con origen e
        HashSet<TransitionEpsilon> deleteTransitionEpsilon = new HashSet();
        for (TransitionEpsilon t : this.transitionsEpsilon) {
            if (t.getStart().equals(e)) {
                deleteTransitionEpsilon.add(t);
            }

            //Elimina las ocurrencias en los destinos de la transicion L
            HashSet<String> deleteDestiniesEpsilon = new HashSet();
            for (String state : t.getDestinies()) {
                if (state.equals(e)) {
                    deleteDestiniesEpsilon.add(e); //Eliminar estado del destino
                }
            }
            t.getDestinies().removeAll(deleteDestiniesEpsilon);
        }
        this.transitionsEpsilon.removeAll(deleteTransitionEpsilon);
    }

    public void deleteTransition(TransitionAFND t) {
        this.transitions.remove(t);
    }

    public void deleteTransitionEpsilon(TransitionEpsilon t) {
        this.transitionsEpsilon.remove(t);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        HashSet<String> states = new HashSet();

        message.append("\nSTATES:");

        for (TransitionAFND t : this.transitions) {
            states.add(t.getStart());
        }

        for (String e : states) {
            message.append(e).append("\n");
        }

        message.append("\nSTART STATE: ").append(this.startState);
        message.append("\nFINAL STATES: ");
        for (String e : finalStates) {
            message.append(e).append(" ");
        }
        message.append("\nTRANSITIONS:\n");
        for (TransitionAFND t : this.transitions) {
            message.append(t).append("\n");
        }

        message.append("\nTRANSITIONS EPSILON:\n");
        for (TransitionEpsilon t : this.transitionsEpsilon) {
            message.append(t).append("\n");
        }

        return message.toString();
    }

    @Override
    public Object clone() {
        AFND copia = null;
        try {
            copia = (AFND) super.clone(); //Hace una copia binaria de los objetos
        } catch (CloneNotSupportedException ex) {
            System.out.println("Clone not supported");
        }
        //Como el clone de HashSet hace solo una copia superficial, tenemos que copia a mano los elementos
        assert copia != null;
        copia.finalStates = new HashSet<String>();
        copia.finalStates.addAll(this.finalStates);

        copia.transitions = new HashSet<TransitionAFND>();
        copia.transitions.addAll(this.transitions);

        copia.transitionsEpsilon = new HashSet<TransitionEpsilon>();
        copia.transitionsEpsilon.addAll(this.transitionsEpsilon);

        return copia;
    }

    public HashSet<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(HashSet<String> finalStates) {
        this.finalStates = finalStates;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public static void main(String[] args) {
        AFND automata = new AFND();

        HashSet<String> finalStates = new HashSet();
        finalStates.add("q1");
        automata.setFinalStates(finalStates);

        HashSet<String> fistTransition = new HashSet();
        fistTransition.add("q1");
        automata.addTransition("q0", '1',fistTransition);
        HashSet<String> secondTransition = new HashSet();
        secondTransition.add("q2");
        automata.addTransition("q0", '0', secondTransition);
        HashSet<String> thirdTransition = new HashSet();
        thirdTransition.add("q2");
        automata.addTransitionEpsilon("q2", thirdTransition);
        HashSet<String> forthTransition = new HashSet();
        forthTransition.add("q1");
        automata.addTransitionEpsilon("q1", forthTransition);

        automata.setStartState("q0");

        System.out.println(automata);

        try {
            if (automata.matchString("1")) {
                System.out.println("MATCH");
            } else {
                System.out.println("NO MATCH");
            }
        } catch (Exception ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStart(HashSet<String> destinies, char symbol) {
        for (TransitionAFND t : this.transitions) {
            if (t.getDestinies().equals(destinies) && t.getSymbol() == symbol) {
                return t.getStart();
            }
        }

        return ""; //Si no se encuentra
    }
}
