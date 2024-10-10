package GUI;

import AFND.AFND;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Objects;
// viernes 25 se entrega
// sabado 26 se muestra y por grupo
public class Interface {
    private JLabel text1;
    private JLabel text2;
    private JLabel text3;
    private JPanel formpanel;
    private JLabel text4;
    private JTextField estadoTextField;
    private JButton buttonAddEstado;
    private JLabel estadoLabel;
    private JTextField alfabetoTextField;
    private JLabel alfabetoLabel;
    private JButton buttonAddCharacter;
    private JLabel transicionLabel;
    private JComboBox comboBoxfromStateT;
    private JComboBox comboBoxCharacterT;
    private JComboBox comboBoxToStateT;
    private JLabel inicioLabel;
    private JButton addTransitionButton;
    private JLabel finalTransicionLabel;
    private JLabel cadenaTransicionLabel;
    private JTable tablaTransiciones;

    private JPanel MainPanel;

    private JButton buttonCheckString;
    private JLabel finalEstadolabel;
    private JComboBox comboBoxFinalStates;
    private JLabel inicialEstadolabel;
    private JComboBox comboBoxStartState;
    private JList listFinalStates;
    private JButton addButtonFinalState;
    private JList listtoStateTransition;
    private JButton addtoStateButton;
    private JScrollPane scrollPaneTableAFND;
    private JButton cleanTransitionButton;
    private JTextField stringToCheckField;
    private JButton cleanAll;
    private JButton graphbutton;

    private HashSet<String> states = new HashSet(); // Conjunto de datos utilizando tablas Hash que no permite duplicados, eficiente en manejo
    private HashSet<String> symbols = new HashSet();

    private HashSet<String> finalStates = new HashSet();

    private HashSet<String> toStatesT = new HashSet();

    private DefaultListModel<String> finalStatesModel;

    private DefaultListModel<String> toStateTransitionModel;

    private AFND afnd = new AFND();

    private DefaultTableModel transitionTableModel;

    public Interface() {
        finalStatesModel = new DefaultListModel<>();
        listFinalStates.setModel(finalStatesModel);
        toStateTransitionModel = new DefaultListModel<>();
        listtoStateTransition.setModel(toStateTransitionModel);
        listtoStateTransition.setModel(toStateTransitionModel);
        // Initialize the transition table model with symbols as headers
        transitionTableModel = new DefaultTableModel(new Object[]{"States"}, 0);
        tablaTransiciones.setModel(transitionTableModel);

        updateComboBoxSring();

        buttonAddCharacter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(alfabetoTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(MainPanel, "Por favor, introduce un símbolo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(symbols.contains(String.valueOf(alfabetoTextField.getText().charAt(0)))){
                    JOptionPane.showMessageDialog(MainPanel, "Símbolo ya esta en el automata", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(alfabetoTextField.getText().length() > 1){
                    JOptionPane.showMessageDialog(MainPanel, "Por favor, solo se tomo el primer character.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

                String input = alfabetoTextField.getText();
                char firstCharacter = input.charAt(0);

                symbols.add(String.valueOf(firstCharacter));
                updateComboBoxSring();
                updateTransitionTableHeaders();

                System.out.println(symbols);
            }
        });
        buttonAddEstado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(estadoTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(MainPanel, "Por favor, introduce un estado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(states.contains(estadoTextField.getText())){
                    JOptionPane.showMessageDialog(MainPanel, "Estado ya esta en el automata", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                states.add(estadoTextField.getText());
                System.out.println(states);
                updateComboBoxStates();
                updateTransitionTableRows();

            }
        });
        addButtonFinalState.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedState = Objects.requireNonNull(comboBoxFinalStates.getSelectedItem()).toString();
                if(finalStates.contains(selectedState)){
                    JOptionPane.showMessageDialog(MainPanel, "Estado ya es final", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                finalStates.add(selectedState);
                finalStatesModel.addElement(selectedState);
            }
        });
        addtoStateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedState = Objects.requireNonNull(comboBoxToStateT.getSelectedItem()).toString();
                if(toStatesT.contains(selectedState)){
                    JOptionPane.showMessageDialog(MainPanel, "Estado ya es final con este estado inicial y caracter", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                toStatesT.add(selectedState);
                toStateTransitionModel.addElement(selectedState);
            }
        });
        // transition button
        addTransitionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedFromState = Objects.requireNonNull(comboBoxfromStateT.getSelectedItem()).toString();
                String selectedChar = Objects.requireNonNull(comboBoxCharacterT.getSelectedItem()).toString();

                if(toStatesT.isEmpty()){
                    JOptionPane.showMessageDialog(MainPanel, "lista de destinos vacia", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (afnd.getTransition(selectedFromState, selectedChar.charAt(0)).isEmpty() || afnd.getStart(toStatesT,selectedChar.charAt(0)).isEmpty()){
                    afnd.addTransition(selectedFromState, selectedChar.charAt(0), toStatesT);

                    updateTransitionsTable(selectedFromState, selectedChar.charAt(0), toStatesT);

                    toStatesT.clear();
                    toStateTransitionModel.removeAllElements();
                } else {
                    JOptionPane.showMessageDialog(MainPanel, "Esta transición ya existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        cleanTransitionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                toStatesT.clear();
                toStateTransitionModel.removeAllElements();
            }
        });
        buttonCheckString.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try{
                    if(finalStates.isEmpty()){
                        JOptionPane.showMessageDialog(MainPanel, "No hay estados finales", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(comboBoxStartState.getSelectedItem().toString().isEmpty()){
                        JOptionPane.showMessageDialog(MainPanel, "No hay estado inicial", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(stringToCheckField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(MainPanel, "No hay cadena a revisar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    afnd.setFinalStates(finalStates);
                    afnd.setStartState(comboBoxStartState.getSelectedItem().toString());
                    if(afnd.matchString(stringToCheckField.getText())){
                        JOptionPane.showMessageDialog(MainPanel, "Cadena reconocida en el automata", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(MainPanel, "Cadena no reconocida en el automata", "Advertencia", JOptionPane.WARNING_MESSAGE);

                }catch (Exception err){
                    JOptionPane.showMessageDialog(MainPanel, "Cadena no reconocida en el automata", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        cleanAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                afnd = new AFND();
                states.clear();
                symbols.clear();
                finalStates.clear();
                toStatesT.clear();
                finalStatesModel.clear();
                resetTransitionTable();
                updateComboBoxStates();
                updateComboBoxSring();
                updateTransitionTableHeaders();
                updateTransitionTableRows();
            }
        });
        graphbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // AGREGAR FUNCIONALIDAD DE GRAFICO

            }
        });
    }

    private void updateComboBoxStates() {
        // Limpiar el JComboBox antes de agregar nuevos elementos
        comboBoxStartState.removeAllItems();
        comboBoxFinalStates.removeAllItems();
        comboBoxToStateT.removeAllItems();
        comboBoxfromStateT.removeAllItems();

        // Agregar cada estado al JComboBox
        for (String state : states) {
            comboBoxStartState.addItem(state);
            comboBoxFinalStates.addItem(state);
            comboBoxToStateT.addItem(state);
            comboBoxfromStateT.addItem(state);
        }
    }

    private void updateComboBoxSring() {
        // Limpiar el JComboBox antes de agregar nuevos elementos
        comboBoxCharacterT.removeAllItems();
        comboBoxCharacterT.addItem('ϵ');
        // Agregar cada estado al JComboBox
        for (String symbol : symbols) {
            comboBoxCharacterT.addItem(symbol);
        }
    }

    private void updateTransitionsTable(String fromState, char transitionChar, HashSet<String> toStates) {
        StringBuilder toStateString = new StringBuilder();
        for (String state : toStates) {
            toStateString.append(state).append(", ");
        }
        if (!toStateString.isEmpty()) {
            toStateString.setLength(toStateString.length() - 2); // Remove trailing comma
        }
        System.out.println(toStateString);

        int fromRow = getRowIndex(fromState);
        int charColumn = getColumnIndex(transitionChar);
        if (fromRow != -1 && charColumn != -1) {
            transitionTableModel.setValueAt(toStateString.toString(), fromRow, charColumn);
        }
    }

    private void updateTransitionTableHeaders() {
        // Clear all existing columns
        transitionTableModel.setColumnCount(0);

        // Add the first column "States"
        transitionTableModel.addColumn("States");

        // Always include epsilon (ϵ) as the first symbol
        transitionTableModel.addColumn("ϵ");

        // Add each symbol from the symbols set as a new column
        for (String symbol : symbols) {
            transitionTableModel.addColumn(symbol);
        }
    }

    private void updateTransitionTableRows() {
        transitionTableModel.setRowCount(0); // Clear existing rows
        for (String state : states) {
            transitionTableModel.addRow(new Object[]{state});
        }
    }

    private int getRowIndex(String state) {
        for (int i = 0; i < transitionTableModel.getRowCount(); i++) {
            if (transitionTableModel.getValueAt(i, 0).equals(state)) {
                return i;
            }
        }
        return -1;
    }

    private int getColumnIndex(char symbol) {
        for (int i = 1; i < transitionTableModel.getColumnCount(); i++) {
            if (transitionTableModel.getColumnName(i).charAt(0) == symbol) {
                return i;
            }
        }
        return -1;
    }

    private void resetTransitionTable() {
        // Clear existing rows and columns
        transitionTableModel.setRowCount(0);
        transitionTableModel.setColumnCount(0);

        // Add the "States" column back
        transitionTableModel.addColumn("States");

        // Re-add each symbol from the symbols set as a new column
        for (String symbol : symbols) {
            transitionTableModel.addColumn(symbol);
        }

        // Re-add the current states as rows
        for (String state : states) {
            transitionTableModel.addRow(new Object[]{state});
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HERMES APP");
        frame.setContentPane(new Interface().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
