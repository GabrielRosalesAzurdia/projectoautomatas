package GUI;

import AFND.TransitionAFND;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GenGrafo {
    public static void generateGraph(HashSet<String> states, HashSet<String> finalStates, String startState, HashSet<TransitionAFND> transitions) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            ArrayList<Object> nodes = new ArrayList<>();
            int spacing = 200;
            int xOffset = 400;
            int yOffset = 100;

            // Crear nodos
            for (String state : states) {
                String cellStyle;

                // Establecer estilo para el estado inicial
                if (state.equals(startState)) {
                    cellStyle = "shape=ellipse;fillColor=orange;strokeColor=black;strokeWidth=2"; // Color y estilo del estado inicial
                }
                // Establecer estilo para estados finales
                else if (finalStates.contains(state)) {
                    cellStyle = "shape=doubleEllipse;fillColor=lightgreen;strokeColor=black"; // Doble circunferencia para estados finales
                }
                // Establecer estilo para estados normales
                else {
                    cellStyle = "shape=ellipse;fillColor=lightblue;strokeColor=black"; // Color para estados normales
                }

                // Crear el nodo con el estilo correspondiente
                Object vertex = graph.insertVertex(parent, null, state, xOffset, yOffset, 80, 80, cellStyle);
                nodes.add(vertex);

                xOffset += spacing; // Desplazar la posición horizontal para el siguiente nodo
            }

            // Crear transiciones
            for (TransitionAFND transition : transitions) {
                Object fromVertex = nodes.get(getIndex(states, transition.getStart(), nodes));
                for (String toState : transition.getDestinies()) {
                    Object toVertex = nodes.get(getIndex(states, toState, nodes));
                    graph.insertEdge(parent, null, String.valueOf(transition.getSymbol()), fromVertex, toVertex, "strokeColor=red;strokeWidth=2;");
                }
                yOffset += 100; // Desplazar la posición vertical para las transiciones (si es necesario)
            }

            // Aplicar el layout jerárquico
            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
            layout.execute(parent);

        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        JFrame frame = new JFrame("Gráfica del Autómata");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static int getIndex(HashSet<String> states, String state, ArrayList<Object> nodes) {
        int index = 0;
        for (String s : states) {
            if (s.equals(state)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}