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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PrincipalController implements ActionListener, ListSelectionListener {
    private ArrayList<Pelicula> peliculas;
    private ArrayList<String> actores;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private int posInterna;

    public PrincipalController(Principal vista){
        this.vista = vista;
        addListeners(this,this);
        posicion = -1;
        posInterna = -1;
        peliculas = new ArrayList<>();
        actores = new ArrayList<>();
        modeloLista = new DefaultListModel<String>();
        vista.getListaActores().setModel(modeloLista);
    }

    public void addListeners(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtImportar().addActionListener(alistener);
        vista.getListaActores().addListSelectionListener(llistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "Siguiente":
                siguientePelicula();
                break;
            case "Anterior":
                anteriorPelicula();
            case "Importar":
                importarPeliculas();
                break;
            default: break;
        }
    }

    private void importarPeliculas(){
        JFileChooser fileChooser = new JFileChooser(); // Dialogo para escribir/leer desde un fichero
        fileChooser.setDialogTitle("Desde que archivo vas a importar los datos?"); // Se pone un titulo
        int userSelection = fileChooser.showSaveDialog(vista.getBtImportar()); // Se muestra el diologo y se le pasa el
        // boton de importar como parametro
        if(userSelection == JFileChooser.APPROVE_OPTION){
            // Ver la extension del archivo
            File fileToSave = fileChooser.getSelectedFile();
            String ruta = fileToSave.getAbsolutePath();
            ruta += ".xml";
            // Elegir funcion a ejecutar dependiendo de la extension
            leerFicheroXML(ruta);

        }
    }

    public void leerFicheroXML(String ruta) {
        peliculas.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(ruta));
            NodeList plcs = documento.getElementsByTagName("peliculas");
            for (int i = 0; i < plcs.getLength(); i++) {
                Node pelicula = plcs.item(i);
                Element elemento = (Element) pelicula;
                String nombre = elemento.getElementsByTagName("nombre").item(0)
                        .getChildNodes().item(0).getNodeValue();
                String fecha = elemento.getElementsByTagName("fecha").item(0)
                        .getChildNodes().item(0).getNodeValue();
                String genero = elemento.getElementsByTagName("genero").item(0)
                        .getChildNodes().item(0).getNodeValue();
                String sinopsis = elemento.getElementsByTagName("sinopsis").item(0)
                        .getChildNodes().item(0).getNodeValue();
                NodeList actrs = elemento.getElementsByTagName("actores");
                ArrayList<String> nombresActores = new ArrayList<>();
                for (int j = 0; j < actrs.getLength(); j++){
                    Node actor = actrs.item(j);
                    Element el = (Element) actor;
                    String nom = el.getElementsByTagName("nombre").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    nombresActores.add(nom);
                }
                Pelicula aux = new Pelicula(nombre,fecha,genero,sinopsis,nombresActores);
                peliculas.add(aux);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            int aux=vista.getListaActores().getSelectedIndex();
            if((aux >= 0)&&(aux < actores.size())){
                posicion=aux;
            }
        }
    }
}
