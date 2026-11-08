package co.edu.uniquindio;

import co.edu.uniquindio.chain.*;
import co.edu.uniquindio.mediator.*;
import co.edu.uniquindio.memento.HistorialOrden;
import co.edu.uniquindio.model.*;
import co.edu.uniquindio.template.ProcesoReparacion;
import co.edu.uniquindio.template.ReparacionFrenos;
import co.edu.uniquindio.template.ReparacionMotor;

import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



        System.out.println("======================================");
        System.out.println("       SISTEMA TALLERTECH");
        System.out.println("======================================");

        // =====================================
        // 1. CREAR LOS OBJETOS DEL TALLER
        // =====================================

        Cliente cliente = new Cliente(
                "Juan Perez",
                "123456789",
                "3101234567"
        );

        Vehiculo vehiculo = new Vehiculo(
                "ABC123",
                "Mazda",
                "2026"
        );

        Mecanico mecanico = new Mecanico(
                "Carlos Gomez",
                "FRENOS"
        );

        Diagnostico diagnostico = new Diagnostico(
                "Desgaste en las pastillas de freno",
                TipoReparacion.FRENOS
        );

        // =====================================
        // 2. CREAR LA ORDEN DE REPARACION
        // =====================================

        OrdenReparacion orden = new OrdenReparacion.Builder(
                "OR-001",
                cliente,
                vehiculo
        )
                .mecanico(mecanico)
                .diagnostico(diagnostico)
                .presupuesto(450000)
                .build();

        System.out.println("\n--- ORDEN DE REPARACION CREADA ---");

        System.out.println("Codigo: " + orden.getCodigo());
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Vehiculo: " + vehiculo.getPlaca());
        System.out.println("Mecanico: " + mecanico.getNombre());
        System.out.println("Presupuesto: $" + orden.getPresupuesto());


        // =====================================
        // 3. CHAIN OF RESPONSIBILITY
        // =====================================

        System.out.println("\n======================================");
        System.out.println("  PATRON 1: CHAIN OF RESPONSIBILITY");
        System.out.println("======================================");

        ValidadorOrden validadorCliente = new ValidadorCliente();
        ValidadorOrden validadorVehiculo = new ValidadorVehiculo();
        ValidadorOrden validadorDiagnostico = new ValidadorDiagnostico();
        ValidadorOrden validadorPresupuesto = new ValidadorPresupuesto();

        // Construir la cadena de validaciones
        validadorCliente.establecerSiguiente(validadorVehiculo);
        validadorVehiculo.establecerSiguiente(validadorDiagnostico);
        validadorDiagnostico.establecerSiguiente(validadorPresupuesto);

        System.out.println("Validando cliente...");
        System.out.println("Validando vehiculo...");
        System.out.println("Validando diagnostico...");
        System.out.println("Validando presupuesto...");

        String resultadoValidacion = validadorCliente.validar(orden);

        System.out.println("\nResultado: " + resultadoValidacion);

        if (resultadoValidacion.startsWith("Error:")) {
            System.out.println("No se puede continuar con la reparacion.");
            return;
        }


        // =====================================
        // 4. MEMENTO
        // =====================================

        System.out.println("\n======================================");
        System.out.println("       PATRON 2: MEMENTO");
        System.out.println("======================================");

        HistorialOrden historial = new HistorialOrden();

        System.out.println("Guardando estado original de la orden...");

        historial.guardarEstado(orden);

        System.out.println("Diagnostico original: "
                + orden.getDiagnostico().getDescripcion());

        System.out.println("Presupuesto original: $"
                + orden.getPresupuesto());

        // Simular una modificacion accidental
        orden.setDiagnostico(new Diagnostico(
                "Problemas en el sistema electrico",
                TipoReparacion.ELECTRICA
        ));

        orden.setPresupuesto(900000);

        System.out.println("\nSe modificaron los datos de la orden.");

        System.out.println("Nuevo diagnostico: "
                + orden.getDiagnostico().getDescripcion());

        System.out.println("Nuevo presupuesto: $"
                + orden.getPresupuesto());

        // Recuperar el estado anterior
        System.out.println("\nRestaurando estado anterior...");

        if (historial.restaurarUltimoEstado(orden)) {

            System.out.println("Estado restaurado correctamente.");

            System.out.println("Diagnostico recuperado: "
                    + orden.getDiagnostico().getDescripcion());

            System.out.println("Presupuesto recuperado: $"
                    + orden.getPresupuesto());
        }


        // =====================================
        // 5. TEMPLATE METHOD
        // =====================================

        System.out.println("\n======================================");
        System.out.println("    PATRON 3: TEMPLATE METHOD");
        System.out.println("======================================");

        System.out.println("Iniciando proceso de reparacion...");

        ProcesoReparacion proceso = new ReparacionFrenos(orden);

        proceso.ejecutarProceso();


        // =====================================
        // 6. MEDIATOR
        // =====================================

        System.out.println("\n======================================");
        System.out.println("        PATRON 4: MEDIATOR");
        System.out.println("======================================");

        // Crear inventario
        Inventario inventario = new Inventario();

        inventario.registrarRepuesto("Pastillas de freno", 10);
        inventario.registrarRepuesto("Liquido de frenos", 5);

        // Crear facturacion
        Facturacion facturacion = new Facturacion();

        // Crear mediador
        MediadorTaller mediador = new MediadorTallerConcreto(
                inventario,
                facturacion
        );

        // Crear recepcion
        Recepcion recepcion = new Recepcion(mediador);

        // Repuestos necesarios para la orden
        Map<String, Integer> repuestosNecesarios = Map.of(
                "Pastillas de freno", 2,
                "Liquido de frenos", 1
        );

        System.out.println("Recepcion envia la orden al mediador...");

        String resultadoMediador = recepcion.registrarOrden(
                orden,
                repuestosNecesarios
        );

        System.out.println("\n" + resultadoMediador);

        System.out.println("\nExistencias restantes:");

        System.out.println("Pastillas de freno: "
                + inventario.consultarExistencias("Pastillas de freno"));

        System.out.println("Liquido de frenos: "
                + inventario.consultarExistencias("Liquido de frenos"));


        // =====================================
        // 7. FINALIZAR SIMULACION
        // =====================================

        System.out.println("\n======================================");
        System.out.println("    FIN DE LA SIMULACION TALLERTECH");
        System.out.println("======================================");
        
        }





}


