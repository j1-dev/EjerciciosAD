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
    private ArrayList<Alumno> alumnos;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;

    public PrincipalController(Principal vista){
        this.vista = vista;
        addListeners(this,this);
        posicion=-1;
        alumnos=new ArrayList<>();
        modeloLista = new DefaultListModel<String>();
        vista.getListaAlumnos().setModel(modeloLista);
    }

    public void addListeners(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtGuardar().addActionListener(alistener);
        vista.getBtNuevo().addActionListener(alistener);
        vista.getListaAlumnos().addListSelectionListener(llistener);
    }

    private void nuevoAlumno(){
        posicion=-1;
        vista.getTfApellidos().setText("");
        vista.getTfNombre().setText("");
        vista.getTfFecha().setText("");
        vista.getTfFecha().setToolTipText("dd-MM-yyyy");
        vista.getCbCiclo().setToolTipText("");
    }

    private void guardarAlumno(){
        String nombre = vista.getTfNombre().getText();
        String apellidos= vista.getTfApellidos().getText();
        String fecha = vista.getTfFecha().getText();
        String ciclo = vista.getCbCiclo().getSelectedItem().toString();

        if(!(nombre.isEmpty()||apellidos.isEmpty()||fecha.isEmpty()||ciclo.isEmpty()||!checkFecha(fecha,"dd-MM-yyyy"))){
            if(posicion<0){
                modeloLista.add(modeloLista.getSize(), nombre+" "+apellidos+": "+ciclo+" - "+fecha);
                Alumno alumno=new Alumno(nombre, apellidos, fecha, ciclo);
                alumnos.add(alumno);
                posicion=alumnos.size()-1;
                vista.getListaAlumnos().setSelectedIndex(posicion);
            } else {
                modeloLista.set(posicion, nombre+" "+apellidos+": "+ciclo+" - "+fecha);
                Alumno alumno=new Alumno(nombre, apellidos, fecha, ciclo);
                alumnos.set(posicion, alumno);
            }
        }
    }

    private boolean checkFecha(String fecha, String formato){
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "Nuevo":
                nuevoAlumno();
                break;
            case "Guardar":
                guardarAlumno();
                break;
            case "Exportar":
                //exportarAlumnos();
                break;
            default: break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int aux=vista.getListaAlumnos().getSelectedIndex();
            if((aux >= 0)&&(aux < alumnos.size())){
                posicion=aux;
                Alumno alumno = alumnos.get(posicion);
                vista.getTfNombre().setText(alumno.getNombre());
                vista.getTfApellidos().setText(alumno.getApellidos());
                vista.getTfFecha().setText(alumno.getfNacimiento());
                vista.getCbCiclo().setSelectedIndex(getIndex(alumno.getCiclo()));
            }
        }
    }

    private int getIndex(String ciclo){
        return switch (ciclo) {
            case "DAM" -> 0;
            case "DAW" -> 1;
            case "ASIR" -> 2;
            default -> -1;
        };
    }
}
