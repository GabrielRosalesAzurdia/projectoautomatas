package AFD;
import core.Matcher;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AFD implements Cloneable, Matcher {

    private HashSet<String> finalStates;
    private String startState = "";
    private HashSet<TransitionAFD> transitions;

    public AFD() {
        this.transitions = new HashSet();
        this.finalStates = new HashSet();
    }

    public void addTransitions(String fromState, char symbol, String toState) {
        this.transitions.add(new TransitionAFD(fromState, symbol, toState));
    }

    public void addTransitions(TransitionAFD trans) {
        this.transitions.add(trans);
    }

    public String getTransition(String state, char symbol) {
        for (TransitionAFD t : this.transitions) {
            if (t.getFromState().equals(state) && t.getSymbol() == symbol) {
                return t.getToState();
            }
        }

        return "";
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
    
    public HashSet<TransitionAFD> getTransitions() {
        return transitions;
    }


    public void addFinalStates(String finalState) {
        this.finalStates.add(finalState);
    }

    @Override
    public boolean isFinalState(String state) {
        return this.finalStates.contains(state);
    }

    public void deleteSymbol(char symbol)
    {
        this.transitions.removeIf(t -> t.getSymbol() == symbol);
    }

    public void deleteState(String state)
    {
        HashSet<TransitionAFD> toDelete = new HashSet();
        for (TransitionAFD t : this.transitions) {
            if (t.getFromState().equals(state) || t.getToState().equals(state))
            {
                toDelete.add(t);
            }
        }
        this.transitions.removeAll(toDelete);
    }

    public void deleteTransition(TransitionAFD t) {
        this.transitions.remove(t);
    }

    @Override
    public boolean matchString(String string) throws Exception {
        //CONTROL DE EXCEPCIONES
        if (this.startState.isEmpty()) {
            throw new Exception("Error: no start state");
        }
        if (this.getFinalStates().isEmpty()) {
            throw new Exception("Error: no final states");
        }
        
        char[] symbol = string.toCharArray();
        String state = this.getStartState();

        for (int i = 0; i < symbol.length; i++) {
            state = getTransition(state, symbol[i]);
            if (state.isEmpty()) {
                throw new Exception("Error: transition with symbol '" + symbol[i] + "' is not valid!");
            }
        }

        return isFinalState(state);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        HashSet<String> states = new HashSet();

        message.append("STATES\n");

        for (TransitionAFD t : this.transitions) {
            states.add(t.getFromState());
            states.add(t.getToState());
        }

        message.append("START STATE: ").append(this.startState).append("\n");
        message.append("FINAL STATE: \n");
        for (String e : finalStates) {
            message.append(e);
        }

        for (String e : states) {
            message.append(e).append("\n");
        }

        message.append("\nTRANSITIONS:\n");
        for (TransitionAFD t : this.transitions) {
            message.append(t).append("\n");
        }

        return message.toString();
    }

    public static void main(String[] args) {
        AFD automata = new AFD();

        automata.finalStates.add("q1");

        automata.addTransitions("q0", '1', "q0");
        automata.addTransitions("q0", '0', "q2");
        automata.addTransitions("q2", '0', "q2");
        automata.addTransitions("q2", '1', "q1");
        automata.addTransitions("q1", '0', "q1");
        automata.addTransitions("q1", '1', "q1");

        System.out.println(automata);

        automata.setStartState("q0");

        try {
            if (automata.matchString("101")) {
                System.out.println("MATCH");
            } else {
                System.out.println("NO MATCH");
            }
        } catch (Exception ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        AFD copia = null;
        try {
            copia = (AFD) super.clone(); //Hace una copia binaria de los objetos. Se llama a super.clone() para realizar una copia superficial del objeto. Esto significa que se copian las referencias a los atributos, pero no los objetos en sí. Si la clase no implementa la interfaz Cloneable, se lanzará una excepción CloneNotSupportedException.
        } catch (CloneNotSupportedException ex) {
            System.out.println("Clone not supported");
        }
        //Como el clone de HashSet hace solo una copia superficial, tenemos que copia a mano los elementos
        assert copia != null;
        copia.finalStates = new HashSet<>();
        // Se inicializa un nuevo HashSet para estadosFinales en la copia. Luego, se agrega cada estado de this.estadosFinales al nuevo conjunto. Esto asegura que copia.estadosFinales contenga nuevas referencias a los mismos objetos, evitando que ambos objetos compartan la misma referencia.
        copia.finalStates.addAll(this.finalStates);

        copia.transitions = new HashSet<TransitionAFD>();
        //Finalmente, se retorna la copia del objeto AFD.
        copia.transitions.addAll(this.transitions);

        return copia;
    }


}
