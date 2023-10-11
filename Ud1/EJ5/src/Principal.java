import javax.swing.*;

public class Principal {
    private JPanel panelPrincipal;
    private JTextField tfTitulo;
    private JTextField tfFecha;
    private JTextField tfGenero;
    private JTextField tfSinopsis;
    private JList listaActores;
    private JButton btImportar;
    private JLabel etTitulo;
    private JLabel etFecha;
    private JLabel etGenero;
    private JLabel etSinopsis;
    private JButton btSiguiente;
    private JButton btAnterior;

    public Principal(){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTfTitulo() {
        return tfTitulo;
    }

    public void setTfTitulo(JTextField tfTitulo) {
        this.tfTitulo = tfTitulo;
    }

    public JTextField getTfFecha() {
        return tfFecha;
    }

    public void setTfFecha(JTextField tfFecha) {
        this.tfFecha = tfFecha;
    }

    public JTextField getTfGenero() {
        return tfGenero;
    }

    public void setTfGenero(JTextField tfGenero) {
        this.tfGenero = tfGenero;
    }

    public JTextField getTfSinopsis() {
        return tfSinopsis;
    }

    public void setTfSinopsis(JTextField tfSinopsis) {
        this.tfSinopsis = tfSinopsis;
    }

    public JList getListaActores() {
        return listaActores;
    }

    public void setListaActores(JList lActores) {
        this.listaActores = lActores;
    }

    public JButton getBtImportar() {
        return btImportar;
    }

    public void setBtImportar(JButton btImportar) {
        this.btImportar = btImportar;
    }

    public JLabel getEtTitulo() {
        return etTitulo;
    }

    public void setEtTitulo(JLabel etTitulo) {
        this.etTitulo = etTitulo;
    }

    public JLabel getEtFecha() {
        return etFecha;
    }

    public void setEtFecha(JLabel etFecha) {
        this.etFecha = etFecha;
    }

    public JLabel getEtGenero() {
        return etGenero;
    }

    public void setEtGenero(JLabel etGenero) {
        this.etGenero = etGenero;
    }

    public JLabel getEtSinopsis() {
        return etSinopsis;
    }

    public void setEtSinopsis(JLabel etSinopsis) {
        this.etSinopsis = etSinopsis;
    }
}
