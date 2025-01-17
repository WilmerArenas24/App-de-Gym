package gm.zona_fit.controlador;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.util.List;

@Component
@Data //Se genera get y set automatico
@ViewScoped//se carga cuando se muestre la aplicacion
public class IndexControlador {
    @Autowired
    IClienteServicio cienteServicio;
    private List<Cliente> clientes;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.clientes = this.cienteServicio.listarCliente();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
    }
}
