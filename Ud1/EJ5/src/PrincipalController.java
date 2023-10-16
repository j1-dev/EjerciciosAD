import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListDataListener;
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
    private int posPelicula;

    public PrincipalController(Principal vista){
        this.vista = vista;
        addListeners(this,this);
        posicion = -1;
        posPelicula = -1;
        peliculas = new ArrayList<>();
        actores = new ArrayList<>();
        modeloLista = new DefaultListModel<String>();
        vista.getListaActores().setModel(modeloLista);
    }

    public void addListeners(ActionListener alistener, ListSelectionListener llistener){
        vista.getBtImportar().addActionListener(alistener);
        vista.getBtSiguiente().addActionListener(alistener);
        vista.getBtAnterior().addActionListener(alistener);
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
                break;
            case "Importar":
                importarPeliculas();
                break;
            default: break;
        }
    }

    private void siguientePelicula(){
        System.out.println(posPelicula);
        if(posPelicula < peliculas.size()-1){
            int pos = posPelicula+1;
            rellenarCampos(pos);
        }
    }

    private void anteriorPelicula(){
        System.out.println(posPelicula);
        if(posPelicula > 0){
            int pos = posPelicula-1;
            rellenarCampos(pos);
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
            // Elegir funcion a ejecutar dependiendo de la extension
            System.out.println(ruta);
            leerFicheroXML(ruta);
        }
    }

    public void leerFicheroXML(String ruta) {
        peliculas.clear();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(ruta));

            NodeList peliculasList = documento.getElementsByTagName("pelicula");

            for (int i = 0; i < peliculasList.getLength(); i++) {
                Node pelicula = peliculasList.item(i);
                if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) pelicula;

                    String nombre = elemento.getElementsByTagName("nombre").item(0)
                            .getTextContent();
                    String fecha = elemento.getElementsByTagName("fecha").item(0)
                            .getTextContent();
                    String genero = elemento.getElementsByTagName("genero").item(0)
                            .getTextContent();
                    String sinopsis = elemento.getElementsByTagName("sinopsis").item(0)
                            .getTextContent();

                    NodeList actoresList = elemento.getElementsByTagName("actor");
                    ArrayList<String> nombresActores = new ArrayList<>();
                    for (int j = 0; j < actoresList.getLength(); j++) {
                        Element actorElement = (Element) actoresList.item(j);
                        String nom = actorElement.getElementsByTagName("nombre").item(0)
                                .getTextContent();
                        nombresActores.add(nom);
                    }

                    Pelicula aux = new Pelicula(nombre, fecha, genero, sinopsis, nombresActores);
                    peliculas.add(aux);
                }
            }
            rellenarCampos(0);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void rellenarCampos(int index){
        Pelicula pelicula = peliculas.get(index);
        vista.getTfTitulo().setText(pelicula.getNombre());
        vista.getTfFecha().setText(pelicula.getFecha());
        vista.getTfGenero().setText(pelicula.getGenero());
        vista.getTfSinopsis().setText(pelicula.getSinopsis());
        ArrayList<String> actores = pelicula.getActores();
        this.actores = actores;
        int i = 0;
        modeloLista.clear();
        for (String actor: actores) {
            modeloLista.add(i++, actor);
        }
        posPelicula = index;
        posicion = 0;
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
