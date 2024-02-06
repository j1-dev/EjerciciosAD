import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrincipalController implements ActionListener, ListSelectionListener {

    private ArrayList<Persona> personas;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private AccesoBD dbacceso;
    private boolean mysql=true;

    public PrincipalController(Principal vista) {
        this.vista = vista;
        anadirListener(this,this);
        posicion=-1;
        this.personas =new ArrayList<>();
        modeloLista=new DefaultListModel<>();
        vista.getListaPersonas().setModel(modeloLista);
        dbacceso=AccesoBD.getInstance();
        reiniciarModeloLista(0);
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtEliminar().addActionListener(alistener);
        vista.getBtGuardar().addActionListener(alistener);
        vista.getBtNuevo().addActionListener(alistener);
        vista.getListaPersonas().addListSelectionListener(llistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Nuevo": nuevaPersona();
                break;
            case "Guardar":
                try {
                    guardarPersona();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Eliminar": eliminarPersona();
                break;
            default:
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int aux= vista.getListaPersonas().getSelectedIndex();
            if((aux>=0)&&(aux< personas.size())) {
                posicion=aux;
                Persona persona = personas.get(posicion);

                vista.getTfNombre().setText(persona.getNombre());
                vista.getCbProvincia().setSelectedIndex(persona.getProvincia());
            }
        }
    }

    private void nuevaPersona(){
        posicion=-1;
        vista.getTfNombre().setText("");
        vista.getCbProvincia().setSelectedIndex(-1);
    }

    private void guardarPersona() throws ParseException {
        String nombre=vista.getTfNombre().getText();
        int provincia = vista.getCbProvincia().getSelectedIndex();

        if(!(nombre.isEmpty()||provincia == -1)){
            if (dbacceso == null) {
                dbacceso = AccesoBD.getInstance();
            }
            if (posicion < 0) {
                Persona persona = new Persona(nombre, provincia);
                dbacceso.insertarPersona(mysql, persona);
                int pos = personas.size();
                reiniciarModeloLista(pos);

            } else {
                Persona persona = personas.get(posicion);
                persona.setNombre(nombre);
                persona.setProvincia(provincia);
                dbacceso.modificarPersona(mysql, persona);
                reiniciarModeloLista(posicion);
            }
        } else {
            vista.getLbError().setText("Todos los campos deben estar rellenos");
        }
    }

    private void reiniciarModeloLista(int posi){
        personas =dbacceso.getPersonas(mysql);
        modeloLista.clear();
        for(int i = 0; i< personas.size(); ++i){
            Persona persona = personas.get(i);
            modeloLista.add(i, persona.getNombre());
        }
        if(modeloLista.isEmpty()){
            nuevaPersona();
        }
        else{
            if((posi>=0)&&(posi<modeloLista.size())){
                posicion=posi;
            }
            else{
                posicion=0;
            }
            vista.getListaPersonas().setSelectedIndex(posicion);
        }
    }

    private void eliminarPersona(){
        if(posicion>=0) {//Comprobar que es una posición válida
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Eliminar persona",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Persona persona = personas.get(posicion);
                if(dbacceso==null){
                    dbacceso=AccesoBD.getInstance();
                }
                dbacceso.eliminarPersona(mysql, persona.getIdPersona());
                if(posicion>=(personas.size()-1)){
                    --posicion;
                }
                reiniciarModeloLista(posicion);
            }
        }
    }

}
