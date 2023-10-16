import javax.swing.*;

public class Principal {
    private JTextField tfNombre;
    private JLabel etNombre;
    private JLabel etDescripcion;
    private JTextArea tfDescripcion;
    private JList listaIngredientes;
    private JButton btPlus;
    private JButton btMinus;
    private JButton btExportar;
    private JPanel panelPrincipal;
    private JLabel etIngredientes;
    private JTextField tfIngrediente;

    public Principal(){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tfDescripcion.setLineWrap(true);
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JLabel getEtNombre() {
        return etNombre;
    }

    public void setEtNombre(JLabel etNombre) {
        this.etNombre = etNombre;
    }

    public JLabel getEtDescripcion() {
        return etDescripcion;
    }

    public void setEtDescripcion(JLabel etDescripcion) {
        this.etDescripcion = etDescripcion;
    }

    public JTextArea getTfDescripcion() {
        return tfDescripcion;
    }

    public void setTfDescripcion(JTextArea tfDescripcion) {
        this.tfDescripcion = tfDescripcion;
    }

    public JList getListaIngredientes() {
        return listaIngredientes;
    }

    public void setListaIngredientes(JList listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    public JButton getBtPlus() {
        return btPlus;
    }

    public void setBtPlus(JButton btPlus) {
        this.btPlus = btPlus;
    }

    public JButton getBtMinus() {
        return btMinus;
    }

    public void setBtMinus(JButton btMinus) {
        this.btMinus = btMinus;
    }

    public JButton getBtExportar() {
        return btExportar;
    }

    public void setBtExportar(JButton btExportar) {
        this.btExportar = btExportar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getEtIngredientes() {
        return etIngredientes;
    }

    public void setEtIngredientes(JLabel etIngredientes) {
        this.etIngredientes = etIngredientes;
    }

    public JTextField getTfIngrediente() {
        return tfIngrediente;
    }

    public void setTfIngrediente(JTextField tfIngrediente) {
        this.tfIngrediente = tfIngrediente;
    }
}
