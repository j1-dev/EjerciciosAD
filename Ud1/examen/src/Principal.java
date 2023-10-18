import javax.swing.*;
import java.util.ArrayList;

public class Principal {
    private JLabel etNombre;
    private JLabel etProvincia;
    private JTextField tfNombre;
    private JComboBox comboProvincia;
    private JButton btAtras;
    private JButton btGuardar;
    private JButton btNuevo;
    private JButton btEliminar;
    private JButton btSiguiente;
    private JButton btExportar;
    private JLabel etPersonasAlmacenadas;
    private JLabel etCantidadPersonas;
    private JPanel panelPrincipal;
    private JLabel etMensaje;
    private JLabel etPersonaActual;
    private JLabel etPersonaSeleccionada;

    public Principal() {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ArrayList<String> provincias = new ArrayList<>();
        provincias.add("Almería");
        provincias.add("Granada");
        provincias.add("Málaga");
        provincias.add("Jaén");
        provincias.add("Córdoba");
        provincias.add("Sevilla");
        provincias.add("Cádiz");
        provincias.add("Huelva");
        comboProvincia.setModel(new DefaultComboBoxModel<>(provincias.toArray()));
        comboProvincia.setSelectedIndex(-1);
    }

    public JLabel getEtNombre() {
        return etNombre;
    }

    public void setEtNombre(JLabel etNombre) {
        this.etNombre = etNombre;
    }

    public JLabel getEtProvincia() {
        return etProvincia;
    }

    public void setEtProvincia(JLabel etProvincia) {
        this.etProvincia = etProvincia;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JComboBox getComboProvincia() {
        return comboProvincia;
    }

    public void setComboProvincia(JComboBox comboProvincia) {
        this.comboProvincia = comboProvincia;
    }

    public JButton getBtAtras() {
        return btAtras;
    }

    public void setBtAtras(JButton btAtras) {
        this.btAtras = btAtras;
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

    public JButton getBtSiguiente() {
        return btSiguiente;
    }

    public void setBtSiguiente(JButton btSiguiente) {
        this.btSiguiente = btSiguiente;
    }

    public JButton getBtExportar() {
        return btExportar;
    }

    public void setBtExportar(JButton button1) {
        this.btExportar = button1;
    }

    public JLabel getEtPersonasAlmacenadas() {
        return etPersonasAlmacenadas;
    }

    public void setEtPersonasAlmacenadas(JLabel etAlumnosAlmacenados) {
        this.etPersonasAlmacenadas = etAlumnosAlmacenados;
    }

    public JLabel getEtCantidadPersonas() {
        return etCantidadPersonas;
    }

    public void setEtCantidadPersonas(JLabel etCantidadAlumnos) {
        this.etCantidadPersonas = etCantidadAlumnos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getEtMensaje() {
        return etMensaje;
    }

    public void setEtMensaje(JLabel etMensaje) {
        this.etMensaje = etMensaje;
    }

    public JLabel getEtPersonaActual() {
        return etPersonaActual;
    }

    public void setEtPersonaActual(JLabel etPersonaActual) {
        this.etPersonaActual = etPersonaActual;
    }

    public JLabel getEtPersonaSeleccionada() {
        return etPersonaSeleccionada;
    }

    public void setEtPersonaSeleccionada(JLabel etPersonaSeleccionada) {
        this.etPersonaSeleccionada = etPersonaSeleccionada;
    }
}
