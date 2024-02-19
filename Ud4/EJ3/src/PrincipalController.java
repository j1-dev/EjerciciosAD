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
import java.io.File;
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
        String nombre = vista.getTfNombre().getText();
        String descripcion = vista.getTfDescripcion().getText();
        if(!(ingredientes.isEmpty()||nombre.isEmpty()||descripcion.isEmpty())){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Especifica a que archivo vas a esportar los datos");
            int userSelection = fileChooser.showSaveDialog(vista.getBtExportar());
            if (userSelection == JFileChooser.APPROVE_OPTION){
                // Ver la extension del archivo
                File fileToSave = fileChooser.getSelectedFile();
                String ruta = fileToSave.getAbsolutePath();
                ruta += ".xml";
                // Elegir funcion a ejecutar dependiendo de la extension
                escribirFicheroXML(ruta);
            }
        }
    }

    public void escribirFicheroXML(String ruta) {
        System.out.println(ruta);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            String name = vista.getTfNombre().getText();
            String desc = vista.getTfDescripcion().getText();
            Text texto = null;
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document documento = dom.createDocument(null, "Receta", null);
            Element raiz = documento.createElement("receta");
            documento.getDocumentElement().appendChild(raiz);
            Element nombre = documento.createElement("nombre");
            texto = documento.createTextNode(name);
            nombre.appendChild(texto);
            raiz.appendChild(nombre);
            Element descripcion = documento.createElement("descripcion");
            texto = documento.createTextNode(desc);
            descripcion.appendChild(texto);
            raiz.appendChild(descripcion);
            Element nodoIngredientes = documento.createElement("ingredientes");

            for (String ingrediente : ingredientes) {
                Element nodoIngrediente = documento.createElement("ingrediente");
                texto = documento.createTextNode(ingrediente);
                nodoIngrediente.appendChild(texto);
                nodoIngredientes.appendChild(nodoIngrediente);
            }

            raiz.appendChild(nodoIngredientes);

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
}
