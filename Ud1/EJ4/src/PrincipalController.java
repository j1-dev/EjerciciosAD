import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PrincipalController implements ActionListener, ListSelectionListener {
    private ArrayList<String> ingredientes;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;

    public PrincipalController(Principal vista){
        this.vista = vista;
        addListeners(this,this);
        posicion = -1;
        ingredientes = new ArrayList<>();
        modeloLista = new DefaultListModel<String>();
        vista.getListaIngredientes().setModel(modeloLista);
    }

    public void addListeners(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtMinus().addActionListener(alistener);
        vista.getBtPlus().addActionListener(alistener);
        vista.getBtExportar().addActionListener(alistener);
        vista.getListaIngredientes().addListSelectionListener(llistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "+":
                addIngrediente();
                break;
            case "-":
                removeIngrediente();
                break;
            case "Exportar":
                exportarReceta();
            default: break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int aux=vista.getListaIngredientes().getSelectedIndex();
            if((aux >= 0)&&(aux < ingredientes.size())){
                posicion=aux;
            }
        }
    }

    private void addIngrediente(){
        String ingrediente = vista.getTfIngrediente().getText();
        if(!(ingrediente.isEmpty())){
            modeloLista.add(modeloLista.getSize(), ingrediente);
            ingredientes.add(ingrediente);
            posicion=ingredientes.size()-1;
            vista.getListaIngredientes().setSelectedIndex(posicion);
        }
    }

    private void removeIngrediente(){
        if(posicion >= 0){
            ingredientes.remove(posicion);
            modeloLista.remove(posicion);
            posicion = -1;
        }
    }

    private void exportarReceta(){

    }
}
