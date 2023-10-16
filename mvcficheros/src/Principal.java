import javax.swing.*;

public class Principal {
    JTextField tfDNI;
    JTextField tfNombre;
    JList listaAlumnos;
    JButton btGuardar;
    JButton btNuevo;
    JButton btImportar;
    JButton btEliminar;
    JButton btExportar;
    JPanel panelPrincipal;
    JLabel lNombre;
    JLabel lDNI;

    public Principal() {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
