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

public class PrincipalController implements ActionListener, ListSelectionListener {

    private ArrayList<Alumno> alumnos;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private AccesoBD dbacceso;
    private boolean mysql=true;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public PrincipalController(Principal vista) {
        this.vista = vista;//Necesitamos tener una referencia de la ventana del formulario
        anadirListener(this,this);//Aquí llamamos al método que añadirá los listener a los distintos botones
        posicion=-1;/*Cuando inicializamos el formulario, éste está vacío (a no ser que posteriormente importemos información), por
        lo que la única acción que podríamos realizar al presionar sobre el botón guardar sería añadir un nuevo alumno. Indicamos
        esto estableciendo el valor de la variable posición como negativa*/
        this.alumnos=new ArrayList<>();//Inicializamos la lista de alumnos como una lista vacía de la clase Alumno
        modeloLista=new DefaultListModel<>();//Aquí inicializamos la lista que usaremos para representar la información del JList
        vista.getListaAlumnos().setModel(modeloLista);/*Aquí establecemos como fuente de datos para nuestro JList la lista creada en el
        paso anterior*/
        dbacceso=AccesoBD.getInstance();//Inicializamos la variable de acceso a la base de datos
        reiniciarModeloLista(0);//Cargamos la lista de alumnos
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        /*En este método únicamente establecemos que la clase que se va a encargar de manejas los eventos lanzados por los botones
         * y el JList sea esta clase*/
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
        if(!e.getValueIsAdjusting()) {//Primero nos aseguramos que la acción de selección ha finalizado
            int aux= vista.getListaAlumnos().getSelectedIndex();/*Cuando finaliza la selección, podemos consultar en nuestro JList,
            haciendo uso de su propiedad SelectedIndex, cuál es el elemento de la lista que se ha seleccionado*/
            if((aux>=0)&&(aux<alumnos.size())) {/*Aquí nos aseguramos que el valor devuelto por selectedIndex se corresponde
            con una posición válida de la lista de alumnos*/
                posicion=aux;//La posición es válida, por lo que la establezco como el nuevo valor de nuestra variable posición
                Alumno alumno = alumnos.get(posicion);/*Seleccionamos el correspondiente alumno de nuestra lista y rellenamos los
                campos Nombre y DNI con su información*/
                vista.getTfNombre().setText(alumno.getNombre());
                vista.getTfApellidos().setText(alumno.getApellidos());
                vista.getTfFecha().setText(sdf.format(alumno.getfNacimiento()));
                vista.getCbCiclo().setSelectedIndex(alumno.getCiclo());
            }
        }
    }

    private void nuevoAlumno(){
        posicion=-1;/*Cuando presionamos sobre el botón nuevo alumno establecemos el valor de la variable posición como negativa,
        para que cuando presionemos el botón guardar sepamos que nuestra intención es añadir un nuevo alumno*/
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
        if(!(nombre.isEmpty()||apellidos.isEmpty())||fechaNacimiento.isEmpty()){//Validamos los datos
            if(dbacceso==null){//Obtenemos la instancia a la clase de acceso a datos
                dbacceso=AccesoBD.getInstance();
            }
            if(posicion<0){//Si la posición es negativa insertamos un nuevo alumno
                Alumno alumno=new Alumno(nombre, apellidos, fechaNacimiento, ciclo);//Creamos un nuevo alumno sin identificador
                dbacceso.insertarAlumno(mysql,alumno);//Insertamos el nuevo alumno
                int pos=alumnos.size();//El nuevo alumno ocupará la última posición en el JList
                reiniciarModeloLista(pos);//Recargamos la lista de alumnos
            }
            else{//Si no vamos a insertar un nuevo alumno, modificaremos uno existente
                Alumno alumno=alumnos.get(posicion);//Obtenemos el alumno para la posición escogida
                alumno.setNombre(nombre);
                alumno.setApellidos(apellidos);
                alumno.setfNacimiento(fechaNacimiento);
                alumno.setCiclo(ciclo);
                dbacceso.modificarAlumno(mysql,alumno);//Actualizamos el alumno en la base de datos
                reiniciarModeloLista(posicion);//Recargamos la lista de alumnos
            }
        }
    }

    private void reiniciarModeloLista(int posi){
        alumnos=dbacceso.getAlumnos(mysql);//Primero obtenemos la lista de alumnos desde la base de datos
        modeloLista.clear();//Vaciamos el JList
        for(int i=0;i<alumnos.size();++i){//Recorremos la lista de alumnos para recargar el JList
            Alumno alumno=alumnos.get(i);
            modeloLista.add(i,alumno.getNombre()+": "+alumno.getApellidos());
        }
        if(modeloLista.isEmpty()){/*Si la lista está vacía ponemos los campos de texto vacíos y la posición negativa
        ya que la única operación que podremos hacer será insertar un nuevo alumno*/
            posicion=-1;
            vista.getTfNombre().setText("");
            vista.getTfApellidos().setText("");
        }
        else{//Si la lista no está vacía debemos seleccionar una posición
            if((posi>=0)&&(posi<modeloLista.size())){/*Si la posición que recibimos como argumento en este método
            es una posición válida dentro del JList, seleccionamos esa posición*/
                posicion=posi;
            }
            else{//En caso contrario, por defecto seleccionaremos la primera posición
                posicion=0;
            }
            vista.getListaAlumnos().setSelectedIndex(posicion);//Aquí seleccionamos la posición en el JList
        }
    }

    private void eliminarAlumno(){
        if(posicion>=0) {//Comprobar que es una posición válida
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Eliminar alumno",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {//Confirmar operación
                Alumno alumno=alumnos.get(posicion);//Obtenemos el alumno para la posición dada
                if(dbacceso==null){//Obtenemos una instancia de la clase de acceso a la base de datos
                    dbacceso=AccesoBD.getInstance();
                }
                dbacceso.eliminarAlumno(mysql,alumno.getIdAlumno());//Eliminamos el alumno usando su identificador
                if(posicion>=(alumnos.size()-1)){/*Si el alumno eliminado era el último, seleccionaremos ahora el
                alumno anterior en la lista*/
                    --posicion;
                }
                reiniciarModeloLista(posicion);//Recargamos la lista de alumnos
            }
        }
    }

}
