import entidades.Actor;
import entidades.Pelicula;
import org.bson.types.ObjectId;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import utils.AccesoBD;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalController implements ActionListener, ListSelectionListener {
    private List<Pelicula> peliculas;
    private List<Actor> actores;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    private int posPelicula;
    private AccesoBD bd = AccesoBD.getInstance();

    public PrincipalController(Principal vista){
        this.vista = vista;
        addListeners(this,this);
        posicion = -1;
        posPelicula = -1;
        peliculas = bd.getPeliculas();
        actores = bd.getActores();
        modeloLista = new DefaultListModel<>();
        vista.getListaActores().setModel(modeloLista);
        rellenarCampos(0);
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



    private void rellenarCampos(int index){
        Pelicula pelicula = peliculas.get(index);
        vista.getTfTitulo().setText(pelicula.getNombre());
        vista.getTfFecha().setText(pelicula.getFecha());
        vista.getTfGenero().setText(pelicula.getGenero());
        vista.getTfSinopsis().setText(pelicula.getSinopsis());
        ObjectId idPelicula = pelicula.get_id();
        int i = 0;
        modeloLista.clear();
        for (Actor actor: actores) {
            if(actor.getIdpelicula().equals(idPelicula)) {
                modeloLista.add(i++, actor.getNombre());
            }
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
