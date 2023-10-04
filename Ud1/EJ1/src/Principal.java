import javax.swing.*;
import java.util.ArrayList;

public class Principal {
    private JTextField tfNombre;
    private JTextField tfApellidos;
    private JComboBox cbCiclo;
    private JList listaAlumnos;
    private JButton btNuevo;
    private JButton btGuardar;
    private JPanel panelPrincipal;
    private JTextField tfFecha;

    public Principal(){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ArrayList<String> ciclos = new ArrayList<String>();
        ciclos.add("DAM");
        ciclos.add("DAW");
        ciclos.add("ASIR");
        cbCiclo.setModel(new DefaultComboBoxModel<>(ciclos.toArray()));
        tfFecha.setToolTipText("dd-MM-yyyy");
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JTextField getTfApellidos() {
        return tfApellidos;
    }

    public void setTfApellidos(JTextField tfApellidos) {
        this.tfApellidos = tfApellidos;
    }

    public JComboBox getCbCiclo() {
        return cbCiclo;
    }

    public void setCbCiclo(JComboBox cbCiclo) {
        this.cbCiclo = cbCiclo;
    }

    public JList getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(JList listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public JButton getBtNuevo() {
        return btNuevo;
    }

    public void setBtNuevo(JButton btNuevo) {
        this.btNuevo = btNuevo;
    }

    public JButton getBtGuardar() {
        return btGuardar;
    }

    public void setBtGuardar(JButton btGuardar) {
        this.btGuardar = btGuardar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTfFecha() {
        return tfFecha;
    }

    public void setTfFecha(JTextField tfFecha) {
        this.tfFecha = tfFecha;
    }
}
