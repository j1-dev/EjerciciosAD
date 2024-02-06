import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrincipalController implements ActionListener, ListSelectionListener {

    private ArrayList<Alumno> alumnos;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private AccesoBD dbacceso;
    private boolean mysql=true;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public PrincipalController(Principal vista) {
        this.vista = vista;
        anadirListener(this,this);
        posicion=-1;
        this.alumnos=new ArrayList<>();
        modeloLista=new DefaultListModel<>();
        vista.getListaAlumnos().setModel(modeloLista);
        dbacceso=AccesoBD.getInstance();
        reiniciarModeloLista(0);
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtEliminar().addActionListener(alistener);
        vista.getBtGuardar().addActionListener(alistener);
        vista.getBtNuevo().addActionListener(alistener);
        vista.getListaAlumnos().addListSelectionListener(llistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Nuevo": nuevoAlumno();
                break;
            case "Guardar":
                try {
                    guardarAlumno();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Eliminar": eliminarAlumno();
                break;
            default:
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            int aux= vista.getListaAlumnos().getSelectedIndex();
            if((aux>=0)&&(aux<alumnos.size())) {
                posicion=aux;
                Alumno alumno = alumnos.get(posicion);

                vista.getTfNombre().setText(alumno.getNombre());
                vista.getTfApellidos().setText(alumno.getApellidos());
                vista.getTfFecha().setText(alumno.getfNacimiento());
                vista.getCbCiclo().setSelectedIndex(alumno.getCiclo());
            }
        }
    }

    private void nuevoAlumno(){
        posicion=-1;
        vista.getTfNombre().setText("");
        vista.getTfApellidos().setText("");
        vista.getTfFecha().setText("");
        vista.getCbCiclo().setSelectedIndex(-1);
    }

    private void guardarAlumno() throws ParseException {
        String nombre=vista.getTfNombre().getText();
        String apellidos=vista.getTfApellidos().getText();
        String fechaNacimiento=vista.getTfFecha().getText();
        int ciclo = vista.getCbCiclo().getSelectedIndex();

        if(!(nombre.isEmpty()||apellidos.isEmpty())||fechaNacimiento.isEmpty()||ciclo == -1){
            try {
                Date d = sdf.parse(fechaNacimiento);
                if (dbacceso == null) {
                    dbacceso = AccesoBD.getInstance();
                }
                if (posicion < 0) {
                    Alumno alumno = new Alumno(nombre, apellidos, fechaNacimiento, ciclo);
                    dbacceso.insertarAlumno(mysql, alumno);
                    int pos = alumnos.size();
                    reiniciarModeloLista(pos);
                } else {
                    Alumno alumno = alumnos.get(posicion);
                    alumno.setNombre(nombre);
                    alumno.setApellidos(apellidos);
                    alumno.setfNacimiento(fechaNacimiento);
                    alumno.setCiclo(ciclo);
                    dbacceso.modificarAlumno(mysql, alumno);
                    reiniciarModeloLista(posicion);
                }
            }catch (ParseException e) {
                vista.getLbError().setText("Formato de fecha erroneo. Debe ser: yyyy-MM-dd");
            }
        } else {
            vista.getLbError().setText("Todos los campos deben estar rellenos");
        }
    }

    private void reiniciarModeloLista(int posi){
        alumnos=dbacceso.getAlumnos(mysql);
        modeloLista.clear();
        for(int i=0;i<alumnos.size();++i){
            Alumno alumno=alumnos.get(i);
            modeloLista.add(i,alumno.getNombre()+": "+alumno.getApellidos());
        }
        if(modeloLista.isEmpty()){
            nuevoAlumno();
        }
        else{
            if((posi>=0)&&(posi<modeloLista.size())){
                posicion=posi;
            }
            else{
                posicion=0;
            }
            vista.getListaAlumnos().setSelectedIndex(posicion);
        }
    }

    private void eliminarAlumno(){
        if(posicion>=0) {//Comprobar que es una posición válida
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Eliminar alumno",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Alumno alumno=alumnos.get(posicion);
                if(dbacceso==null){
                    dbacceso=AccesoBD.getInstance();
                }
                dbacceso.eliminarAlumno(mysql,alumno.getIdAlumno());
                if(posicion>=(alumnos.size()-1)){
                    --posicion;
                }
                reiniciarModeloLista(posicion);
            }
        }
    }

}
