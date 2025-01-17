package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component //Recuperar objectos de spring
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;

    private DefaultTableModel tableModeloClientes;
    private Integer idCliente;


    IClienteServicio clienteServicio;

    @Autowired //Inyeccion de dependencias
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null); //Centra evntana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Creando las cabeceras de la tabla
        this.tableModeloClientes = new DefaultTableModel(0,4);
        String[] cabeceros = {"Id","Nombre","Apellido","Membresia"};
        this.tableModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tableModeloClientes);

        //Cargar las filas en la tabla
        listarClientes();
    }

    private void listarClientes(){
        this.tableModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarCliente();
        clientes.forEach(cliente -> {
            //Creando renglon
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tableModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente(){
        if (nombreTexto.getText().equals("")){
            mostrarMensaje("Nombre no puede estar vacío");
            nombreTexto.requestFocusInWindow();
            return;
        } else if (apellidoTexto.getText().equals("")){
            mostrarMensaje("Apellido no puede estar vacío");
            apellidoTexto.requestFocusInWindow();
            return;
        }else if (membresiaTexto.getText().equals("")){
            mostrarMensaje("Membresía no puede estar vacío");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        //Recuperar los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());

        var cliente = new Cliente();

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        cliente.setId(this.idCliente);

        //Insertando el cliente en la BD
        this.clienteServicio.guardarCliente(cliente);


        if (idCliente == null){
            mostrarMensaje("Nuevo cliente agregado!");
        }else {
            mostrarMensaje("Se actualizó el registro!");
        }

        //Limpiando textos
        limpiarFormulario();
        listarClientes();


    }

    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon != -1){
            //-1 significa que no se selecciono ningun registro
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon,1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon,3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");

        //Limpiar el id para que este en null
        this.idCliente = null;

    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

}
