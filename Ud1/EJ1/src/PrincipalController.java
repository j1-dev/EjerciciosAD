import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
        vista.getBtPrimero().addActionListener(alistener);
        vista.getBtArriba().addActionListener(alistener);
        vista.getBtAbajo().addActionListener(alistener);
        vista.getBtUltimo().addActionListener(alistener);
        vista.getBtExportar().addActionListener(alistener);
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
            case "Primero":
                irPrimero();
                break;
            case "Arriba":
                irArriba();
                break;
            case "Abajo":
                irAbajo();
                break;
            case "Ultimo":
                irUltimo();
                break;
            case "Exportar":
                exportarAlumnos();
            default: break;
        }
    }

    public void irPrimero(){
        posicion = 0;
        vista.getListaAlumnos().setSelectedIndex(posicion);
    }

    public void irAbajo(){
        posicion = vista.getListaAlumnos().getSelectedIndex()+1;
        if(posicion >= 0 && posicion < alumnos.size()){
            vista.getListaAlumnos().setSelectedIndex(posicion);
        } else {
            vista.getListaAlumnos().setSelectedIndex(0);
        }
    }

    public void irArriba(){
        posicion = vista.getListaAlumnos().getSelectedIndex()-1;
        if(posicion >= 0 && posicion < alumnos.size()){
            vista.getListaAlumnos().setSelectedIndex(posicion);
        } else {
            vista.getListaAlumnos().setSelectedIndex(alumnos.size()-1);
        }
    }

    public void irUltimo(){
        posicion = alumnos.size()-1;
        vista.getListaAlumnos().setSelectedIndex(posicion);
    }

    private void exportarAlumnos(){
        if(!alumnos.isEmpty()){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Especifica a que archivo vas a esportar los datos");
            int userSelection = fileChooser.showSaveDialog(vista.getBtExportar());
            if (userSelection == JFileChooser.APPROVE_OPTION){
                // Ver la extension del archivo
                File fileToSave = fileChooser.getSelectedFile();
                String ruta = fileToSave.getAbsolutePath();
                String[] partes = ruta.split("\\.");
                // Elegir funcion a ejecutar dependiendo de la extension
                if(partes[partes.length-1].equals("txt")){
                    escribirFicheroTextoPlano(ruta);
                } else if (partes[partes.length-1].equals("xml")){
                    escribirFicheroXML(ruta);
                } else {
                    serializarAlumnos(ruta);
                }
            }
        }
    }

    public void escribirFicheroTextoPlano(String ruta) {
        FileWriter fichero = null;
        PrintWriter escritor = null;

        try {
            fichero = new FileWriter(ruta);
            escritor = new PrintWriter(fichero);
            for (Alumno a: alumnos) {
                escritor.println(a.getNombre()+" "+a.getApellidos()+": "+a.getfNacimiento()+" - "+a.getCiclo());
            }
            escritor.println("Primera línea de fichero para el primer ejemplo");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fichero != null) {
                try {
                    fichero.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void escribirFicheroXML(String ruta) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document documento = dom.createDocument(null, "Alumnos", null);
            Element raiz = documento.createElement("alumnos");
            documento.getDocumentElement().appendChild(raiz);
            Element nodoAlumno = null, nodoDatos = null;
            Text texto = null;

            for (Alumno alumno : alumnos) {
                nodoAlumno = documento.createElement("alumno");
                raiz.appendChild(nodoAlumno);
                nodoDatos = documento.createElement("nombre");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getNombre());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("apellidos");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getApellidos());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("fNacimiento");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getfNacimiento());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("ciclo");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getCiclo());
                nodoDatos.appendChild(texto);
            }
            Source fuente = new DOMSource(documento);
            Result resultado = new StreamResult(new java.io.File(ruta));
            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.transform(fuente, resultado);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void serializarAlumnos(String ruta){
        ObjectOutputStream serializador = null;
        try {
            serializador = new ObjectOutputStream(new FileOutputStream(ruta));
            serializador.writeObject(alumnos);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (serializador != null)
                try {
                    serializador.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int aux=vista.getListaAlumnos().getSelectedIndex();
            if((aux >= 0)&&(aux < alumnos.size())){
                posicion=aux;
                rellenarCampos(posicion);
            }
        }
    }

    private void rellenarCampos(int index){
        Alumno alumno = alumnos.get(index);
        vista.getTfNombre().setText(alumno.getNombre());
        vista.getTfApellidos().setText(alumno.getApellidos());
        vista.getTfFecha().setText(alumno.getfNacimiento());
        vista.getCbCiclo().setSelectedIndex(getIndex(alumno.getCiclo()));
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
