import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class PrincipalController implements ActionListener {

    private ArrayList<Persona> personas;
    private Principal vista;
    private int posicion;

    public PrincipalController(Principal vista) {
        this.vista = vista;
        anadirListener(this);
        personas = new ArrayList<>();
        posicion = -1;
        vista.getEtCantidadPersonas().setText("0");
        vista.getEtPersonaSeleccionada().setText("-1");
    }

    private void anadirListener(ActionListener alistener){
        vista.getBtNuevo().addActionListener(alistener);
        vista.getBtGuardar().addActionListener(alistener);
        vista.getBtEliminar().addActionListener(alistener);
        vista.getBtExportar().addActionListener(alistener);
        vista.getBtAtras().addActionListener(alistener);
        vista.getBtSiguiente().addActionListener(alistener);
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
            case "Anterior": anteriorPersona();
                break;
            case "Siguiente": siguientePersona();
                break;
            case "Exportar": exportarPersonas();
               break;
            default:
                break;
        }
    }

    private void nuevaPersona(){
        posicion = -1;
        vista.getTfNombre().setText("");
        vista.getComboProvincia().setSelectedIndex(-1);
        vista.getEtMensaje().setText("");
        vista.getEtPersonaSeleccionada().setText("-1");
    }

    private void guardarPersona(){
        String nombre = vista.getTfNombre().getText();
        int indexProvincia = vista.getComboProvincia().getSelectedIndex();

        if(!(nombre.isEmpty()||indexProvincia ==-1)){
            String provincia = vista.getComboProvincia().getSelectedItem().toString();
            if (posicion < 0) {
                Persona persona = new Persona(nombre, provincia);
                personas.add(persona);
                posicion=personas.size()-1;
                vista.getEtCantidadPersonas().setText(String.valueOf(personas.size()));
                vista.getEtPersonaSeleccionada().setText(String.valueOf(personas.size()));
            } else {
                Persona persona = new Persona(nombre, provincia);
                personas.set(posicion, persona);
            }
        } else if (nombre.isEmpty()){
            vista.getEtMensaje().setText("No puede dejar el campo nombre vacio");
        } else {
            vista.getEtMensaje().setText("Debe seleccionar alguna provincia");
        }
    }

    private void eliminarPersona(){
        System.out.println(posicion);
        if(posicion >= 0){
            personas.remove(posicion);
            vista.getEtCantidadPersonas().setText(String.valueOf(personas.size()));
            if(posicion == 0){
                vista.getTfNombre().setText("");
                vista.getComboProvincia().setSelectedIndex(-1);
                posicion = -1;
                vista.getEtPersonaSeleccionada().setText("-1");
            } else {
                posicion--;
                rellenarCampos(posicion);
            }
        }
    }

    private void anteriorPersona(){
        if(posicion > 0){
            posicion--;
            rellenarCampos(posicion);
        }
    }

    private void siguientePersona(){
        if(posicion < personas.size()-1){
            posicion++;
            rellenarCampos(posicion);
        }
    }

    private void exportarPersonas(){
        if(!personas.isEmpty()){
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
            for (Persona persona: personas) {
                escritor.println(persona.getNombre()+": "+persona.getProvincia());
            }
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
            Document documento = dom.createDocument(null, "Personas", null);
            Element raiz = documento.createElement("personas");
            documento.getDocumentElement().appendChild(raiz);
            Element nodoAlumno = null, nodoDatos = null;
            Text texto = null;

            for (Persona persona : personas) {
                nodoAlumno = documento.createElement("persona");
                raiz.appendChild(nodoAlumno);
                nodoDatos = documento.createElement("nombre");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(persona.getNombre());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("provincia");
                nodoAlumno.appendChild(nodoDatos);
                texto = documento.createTextNode(persona.getProvincia());
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
            serializador.writeObject(personas);
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

    private void rellenarCampos(int index){
        Persona persona = personas.get(index);
        vista.getTfNombre().setText(persona.getNombre());
        vista.getComboProvincia().setSelectedIndex(getIndex(persona.getProvincia()));
        vista.getEtPersonaSeleccionada().setText(String.valueOf(index+1));
    }

    private int getIndex(String provincia){
        return switch (provincia) {
            case "Almería" -> 0;
            case "Granada" -> 1;
            case "Málaga" -> 2;
            case "Jaén" -> 3;
            case "Córdoba" -> 4;
            case "Sevilla" -> 5;
            case "Cadiz" -> 6;
            case "Huelva" -> 7;
            default -> -1;
        };
    }
}
