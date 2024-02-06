import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PrincipalController implements ActionListener, ListSelectionListener {

    private List<Persona> personas;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private AccesoBD dbacceso;
    private boolean mysql=false;

    public PrincipalController(Principal vista) {
        this.vista = vista;//Necesitamos tener una referencia de la ventana del formulario
        anadirListener(this,this);//Aquí llamamos al método que añadirá los listener a los distintos botones
        posicion=-1;/*Cuando inicializamos el formulario, éste está vacío (a no ser que posteriormente importemos información), por
        lo que la única acción que podríamos realizar al presionar sobre el botón guardar sería añadir un nuevo alumno. Indicamos
        esto estableciendo el valor de la variable posición como negativa*/
        this.personas =new ArrayList<>();//Inicializamos la lista de personas como una lista vacía de la clase Persona
        modeloLista=new DefaultListModel<>();//Aquí inicializamos la lista que usaremos para representar la información del JList
        vista.listaPersonas.setModel(modeloLista);/*Aquí establecemos como fuente de datos para nuestro JList la lista creada en el
        paso anterior*/
        dbacceso=AccesoBD.getInstance();//Inicializamos la variable de acceso a la base de datos
        reiniciarModeloLista(0);//Cargamos la lista de personas
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        /*En este método únicamente establecemos que la clase que se va a encargar de manejas los eventos lanzados por los botones
        * y el JList sea esta clase*/
        vista.btEliminar.addActionListener(alistener);
        vista.btGuardar.addActionListener(alistener);
        vista.btNuevo.addActionListener(alistener);
        vista.listaPersonas.addListSelectionListener(llistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Nuevo": nuevaPersona();
                break;
            case "Guardar": guardarPersona();
                break;
            case "Eliminar": eliminarPersona();
                break;
            default:
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {//Primero nos aseguramos que la acción de selección ha finalizado
            int aux=vista.listaPersonas.getSelectedIndex();/*Cuando finaliza la selección, podemos consultar en nuestro JList,
            haciendo uso de su propiedad SelectedIndex, cuál es el elemento de la lista que se ha seleccionado*/
            if((aux>=0)&&(aux< personas.size())) {/*Aquí nos aseguramos que el valor devuelto por selectedIndex se corresponde
            con una posición válida de la lista de personas*/
                posicion=aux;//La posición es válida, por lo que la establezco como el nuevo valor de nuestra variable posición
                Persona persona = personas.get(posicion);/*Seleccionamos el correspondiente persona de nuestra lista y rellenamos los
                campos Nombre y DNI con su información*/
                vista.tfDNI.setText(persona.getDNI());
                vista.tfNombre.setText(persona.getNombre());
            }
        }
    }

    private void nuevaPersona(){
        posicion=-1;/*Cuando presionamos sobre el botón nuevo alumno establecemos el valor de la variable posición como negativa,
        para que cuando presionemos el botón guardar sepamos que nuestra intención es añadir un nuevo alumno*/
        vista.tfDNI.setText("");//Vamos a insertar un nuevo alumno, así que borramos los campos para escribir nueva información
        vista.tfNombre.setText("");
    }

    private void guardarPersona(){
        String nombre=vista.tfNombre.getText();//Obtenemos los valores de los campos
        String dni=vista.tfDNI.getText();
        if(!(nombre.isEmpty()||dni.isEmpty())){//Validamos los datos
            if(dbacceso==null){//Obtenemos la instancia a la clase de acceso a datos
                dbacceso=AccesoBD.getInstance();
            }
            if(posicion<0){//Si la posición es negativa insertamos un nuevo persona
                Persona persona =new Persona(dni,nombre);//Creamos un nuevo persona sin identificador
                dbacceso.insertarPersona(persona);//Insertamos el nuevo persona
                int pos= personas.size();//El nuevo persona ocupará la última posición en el JList
                reiniciarModeloLista(pos);//Recargamos la lista de personas
            }
            else{//Si no vamos a insertar un nuevo persona, modificaremos uno existente
                Persona persona = personas.get(posicion);//Obtenemos el persona para la posición escogida
                persona.setDNI(dni);//Actualizamos la información del persona
                persona.setNombre(nombre);
                dbacceso.modificarPersona(persona);//Actualizamos el persona en la base de datos
                reiniciarModeloLista(posicion);//Recargamos la lista de personas
            }
        }
    }

    private void reiniciarModeloLista(int posi){
        personas =dbacceso.getPersonas();//Primero obtenemos la lista de personas desde la base de datos
        modeloLista.clear();//Vaciamos el JList
        for(int i = 0; i< personas.size(); ++i){//Recorremos la lista de personas para recargar el JList
            Persona persona = personas.get(i);
            modeloLista.add(i, persona.getDNI()+": "+ persona.getNombre());
        }
        if(modeloLista.isEmpty()){/*Si la lista está vacía ponemos los campos de texto vacíos y la posición negativa
        ya que la única operación que podremos hacer será insertar un nuevo alumno*/
            posicion=-1;
            vista.tfNombre.setText("");
            vista.tfDNI.setText("");
        }
        else{//Si la lista no está vacía debemos seleccionar una posición
            if((posi>=0)&&(posi<modeloLista.size())){/*Si la posición que recibimos como argumento en este método
            es una posición válida dentro del JList, seleccionamos esa posición*/
                posicion=posi;
            }
            else{//En caso contrario, por defecto seleccionaremos la primera posición
                posicion=0;
            }
            vista.listaPersonas.setSelectedIndex(posicion);//Aquí seleccionamos la posición en el JList
        }
    }

    private void eliminarPersona(){
        if(posicion>=0) {//Comprobar que es una posición válida
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Eliminar persona",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {//Confirmar operación
                Persona persona = personas.get(posicion);//Obtenemos el persona para la posición dada
                if(dbacceso==null){//Obtenemos una instancia de la clase de acceso a la base de datos
                    dbacceso=AccesoBD.getInstance();
                }
                dbacceso.eliminarPersona(persona.get_id());//Eliminamos el persona usando su identificador
                if(posicion>=(personas.size()-1)){/*Si el persona eliminado era el último, seleccionaremos ahora el
                persona anterior en la lista*/
                    --posicion;
                }
                reiniciarModeloLista(posicion);//Recargamos la lista de personas
            }
        }
    }

}
