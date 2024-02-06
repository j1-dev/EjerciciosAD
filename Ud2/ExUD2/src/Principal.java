import javax.swing.*;
import java.util.ArrayList;

public class Principal {
    private JTextField tfNombre;
    private JComboBox cbProvincia;
    private JList listaPersonas;
    private JButton btGuardar;
    private JButton btNuevo;
    private JButton btEliminar;
    private JPanel panelPrincipal;
    private JLabel lbError;

    public Principal(){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ArrayList<String> provincias = new ArrayList<String>();
        provincias.add("Almería");
        provincias.add("Cádiz");
        provincias.add("Córdoba");
        provincias.add("Granada");
        provincias.add("Huelva");
        provincias.add("Jaén");
        provincias.add("Málaga");
        provincias.add("Sevilla");
        cbProvincia.setModel(new DefaultComboBoxModel<>(provincias.toArray()));
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JComboBox getCbProvincia() {
        return cbProvincia;
    }

    public void setCbProvincia(JComboBox cbProvincia) {
        this.cbProvincia = cbProvincia;
    }

    public JList getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(JList listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public JButton getBtGuardar() {
        return btGuardar;
    }

    public void setBtGuardar(JButton btGuardar) {
        this.btGuardar = btGuardar;
    }

    public JButton getBtNuevo() {
        return btNuevo;
    }

    public void setBtNuevo(JButton btNuevo) {
        this.btNuevo = btNuevo;
    }

    public JButton getBtEliminar() {
        return btEliminar;
    }

    public void setBtEliminar(JButton btEliminar) {
        this.btEliminar = btEliminar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLbError() {
        return lbError;
    }

    public void setLbError(JLabel lbError) {
        this.lbError = lbError;
    }

}
