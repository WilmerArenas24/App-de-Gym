package gm.zona_fit.gui;

import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component //Recuperar objectos de spring
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    private DefaultTableModel tableModeloClientes;


    IClienteServicio clienteServicio;

    @Autowired //Inyeccion de dependencias
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null); //Centra evntana
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tableModeloClientes = new DefaultTableModel(0,4);
        String[] cabeceros = {"Id","Nombre","Apellido","Membresia"};
        this.tableModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tableModeloClientes);
    }
}
