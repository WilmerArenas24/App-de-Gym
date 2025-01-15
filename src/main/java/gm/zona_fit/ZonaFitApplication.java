package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;


	private static final Logger logger =
			LoggerFactory.getLogger(ZonaFitApplication.class);
	String nl = System.lineSeparator(); //Salto de linea forma generica

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion...");
		//Levantar lla fabrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicacion finalizada...");
	}

	@Override
	public void run(String... args) throws Exception {

		zonaFitApp();
	}

	private void zonaFitApp(){
		var salir = false;
		var consola = new Scanner(System.in);

		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola,opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner consola){
		logger.info("""
				--------- Aplicacion zona fit Gym ---------
				********* Menu de Opciones *********
				\t1.Listar Clientes
				\t2.Buscar Clientes
				\t3.Agregar Clientes
				\t4.Actualizar Clientes
				\t5.Eliminar
				\t6.Salir
				Elije una opción:\s""");
		return Integer.parseInt(consola.nextLine());

	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;

		switch (opcion){

			case 1 -> {
				logger.info(nl + "---Listado de clientes---" + nl);
				List<Cliente> clientes = clienteServicio.listarCliente();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}

			case 2 -> {
				logger.info(nl + "---Buscar Cliente---"+nl);
				logger.info("Id del cliente a buscar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

				if (cliente != null){
					logger.info("Cliente: "+cliente + nl);
				} else {
					logger.info("No encontrado");
				}

			}

			case 3 -> {
				logger.info(nl+"---Agregar Cliente---"+nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Membresia: ");
				var membresia = Integer.parseInt(consola.nextLine());

				var cliente = new Cliente();

				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);

				clienteServicio.guardarCliente(cliente);

				logger.info(nl + "Cliente agregado: "+cliente + nl);
			}

			case 4 ->{
				logger.info(nl + "---Actualizar Cliente---"+nl);
				logger.info("Id del cliente a actualizar: ");
				var idCliente = Integer.parseInt(consola.nextLine());

				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

				if (cliente != null){
					logger.info("Nuevo Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Nuevo Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Nueva Membresia: ");
					var membresia = Integer.parseInt(consola.nextLine());

					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);

					clienteServicio.guardarCliente(cliente);

					logger.info("Cliente modificado: " + cliente + nl);
				}else {
					logger.info("Cliente no encontrado!");
				}



			}

			case 5 ->{
				logger.info(nl + "---Eliminar Cliente---" + nl);
				logger.info("Id del cliente a eliminar: ");
				var idCliente = Integer.parseInt(consola.nextLine());

				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

				if (cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente Eliminado: "+cliente);
				}else {
					logger.info("Cliente no encontrado");
				}
			}

			case 6 ->{
				logger.info(nl+"Hasta pronto!!"+nl);
				salir = true;
			}
			default -> logger.info("Opción incorrecta!!");

		}
		return salir;
	}
}
