package uniquindio;

import java.math.BigDecimal;

import uniquindio.clientes.Cliente;
import uniquindio.clientes.Vehiculo;
import uniquindio.coordinacion.HistorialComandos;
import uniquindio.cotizacion.CotizarOrdenCommand;
import uniquindio.cotizacion.TarifaClienteFrecuente;
import uniquindio.cotizacion.TarifaConvenio;
import uniquindio.cotizacion.TarifaNormal;
import uniquindio.notificaciones.ApiNotificaciones;
import uniquindio.notificaciones.NotificadorClienteObserver;
import uniquindio.notificaciones.PanelTallerObserver;
import uniquindio.notificaciones.PublicadorEventos;
import uniquindio.ordenes.*;
import uniquindio.personal.Mecanico;

public class Main {

    public static void main(String[] args) {

        // Configuración del sistema.
        RepositorioOrdenes repositorio =
                new RepositorioOrdenesMemoria();

        HistorialComandos historial =
                new HistorialComandos();

        PublicadorEventos publicador =
                new PublicadorEventos();

        PanelTallerObserver panel =
                new PanelTallerObserver();

        NotificadorClienteObserver notificador =
                new NotificadorClienteObserver(
                        new ApiNotificaciones()
                );

        // OBSERVER: ambos recibirán los cambios de estado.
        publicador.suscribir(panel);
        publicador.suscribir(notificador);

        Cliente cliente = new Cliente(
                "1001234567",
                "Laura Gómez",
                "3000000000"
        );

        Vehiculo vehiculo = new Vehiculo(
                "ABC123",
                "Renault",
                "Sandero",
                cliente
        );

        Mecanico mecanico = new Mecanico(
                1,
                "Ana",
                "Mecánica general"
        );

        mostrarPaso("1. COMMAND: registrar la orden");

        historial.ejecutar(new CrearOrdenCommand(
                repositorio,
                1,
                vehiculo
        ));

        OrdenServicio orden = repositorio
                .buscarPorNumero(1)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No se encontró la orden creada."
                        )
                );

        System.out.println(orden);

        mostrarPaso("2. COMMAND: rechazar una orden duplicada");

        esperarRechazo(
                IllegalArgumentException.class,
                () -> historial.ejecutar(
                        new CrearOrdenCommand(
                                repositorio,
                                1,
                                vehiculo
                        )
                )
        );

        mostrarPaso("3. STATE: impedir avanzar sin mecánico");

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new AvanzarOrdenCommand(
                                repositorio,
                                1,
                                publicador
                        )
                )
        );

        System.out.println(
                "La orden permanece en: "
                        + orden.getNombreEstado()
        );

        mostrarPaso("4. COMMAND: asignar mecánico");

        historial.ejecutar(new AsignarMecanicoCommand(
                repositorio,
                1,
                mecanico
        ));

        System.out.println(
                "Mecánico asignado: "
                        + orden.getMecanico().getNombre()
        );

        mostrarPaso(
                "5. STATE + OBSERVER: iniciar diagnóstico"
        );

        historial.ejecutar(new AvanzarOrdenCommand(
                repositorio,
                1,
                publicador
        ));

        // Los dos observadores reciben la transición:
        // REGISTRADA -> EN_DIAGNOSTICO.

        mostrarPaso(
                "6. STATE: impedir solicitar aprobación sin diagnóstico"
        );

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new AvanzarOrdenCommand(
                                repositorio,
                                1,
                                publicador
                        )
                )
        );

        mostrarPaso("7. COMMAND: registrar diagnóstico");

        historial.ejecutar(
                new ActualizarDiagnosticoCommand(
                        repositorio,
                        1,
                        "Requiere cambio de aceite "
                                + "y revisión de frenos."
                )
        );

        System.out.println(
                "Diagnóstico: " + orden.getDiagnostico()
        );

        mostrarPaso("8. Agregar los servicios de la orden");

        orden.agregarServicio(new ItemServicio(
                "Cambio de aceite",
                new BigDecimal("120000")
        ));

        orden.agregarServicio(new ItemServicio(
                "Revisión de frenos",
                new BigDecimal("80000")
        ));

        repositorio.guardar(orden);

        for (ItemServicio servicio : orden.getServicios()) {
            System.out.println(servicio);
        }

        System.out.println(
                "Valor base: $" + orden.calcularValorBase()
        );

        mostrarPaso(
                "9. STATE: impedir solicitar aprobación sin cotización"
        );

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new AvanzarOrdenCommand(
                                repositorio,
                                1,
                                publicador
                        )
                )
        );

        mostrarPaso(
                "10. COMMAND + STRATEGY: probar las tres tarifas"
        );

        historial.ejecutar(new CotizarOrdenCommand(
                repositorio,
                1,
                new TarifaNormal()
        ));

        mostrarCotizacion(orden);

        historial.ejecutar(new CotizarOrdenCommand(
                repositorio,
                1,
                new TarifaClienteFrecuente()
        ));

        mostrarCotizacion(orden);

        historial.ejecutar(new CotizarOrdenCommand(
                repositorio,
                1,
                new TarifaConvenio()
        ));

        mostrarCotizacion(orden);

        // La cotización vigente es la última: tarifa convenio.

        mostrarPaso(
                "11. STATE + OBSERVER: solicitar aprobación"
        );

        historial.ejecutar(new AvanzarOrdenCommand(
                repositorio,
                1,
                publicador
        ));

        mostrarPaso(
                "12. STATE: bloquear cambios al presupuesto enviado"
        );

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new CotizarOrdenCommand(
                                repositorio,
                                1,
                                new TarifaNormal()
                        )
                )
        );

        System.out.println(
                "El presupuesto permanece en: $"
                        + orden.getPresupuesto()
        );

        mostrarPaso(
                "13. Registrar aprobación e iniciar reparación"
        );

        // Esta acción representa la aprobación del cliente.
        historial.ejecutar(new AvanzarOrdenCommand(
                repositorio,
                1,
                publicador
        ));

        mostrarPaso(
                "14. Confirmar trabajo terminado"
        );

        historial.ejecutar(new AvanzarOrdenCommand(
                repositorio,
                1,
                publicador
        ));

        mostrarPaso(
                "15. OBSERVER: retirar la suscripción del cliente"
        );

        publicador.desuscribir(notificador);

        System.out.println(
                "En la siguiente transición solo se actualizará "
                        + "el panel del taller."
        );

        mostrarPaso(
                "16. STATE + COMMAND: registrar entrega"
        );

        historial.ejecutar(new AvanzarOrdenCommand(
                repositorio,
                1,
                publicador
        ));

        mostrarPaso(
                "17. STATE: impedir modificar una orden entregada"
        );

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new ActualizarDiagnosticoCommand(
                                repositorio,
                                1,
                                "Intento de modificación."
                        )
                )
        );

        mostrarPaso(
                "18. STATE: impedir avanzar después de la entrega"
        );

        esperarRechazo(
                IllegalStateException.class,
                () -> historial.ejecutar(
                        new AvanzarOrdenCommand(
                                repositorio,
                                1,
                                publicador
                        )
                )
        );

        mostrarPaso("RESULTADO FINAL");

        System.out.println(orden);

        System.out.println(
                "Cliente: "
                        + orden.getVehiculo()
                        .getPropietario().getNombre()
        );

        System.out.println(
                "Diagnóstico: " + orden.getDiagnostico()
        );

        System.out.println(
                "Tarifa aplicada: " + orden.getNombreTarifa()
        );

        System.out.println(
                "Órdenes registradas: "
                        + repositorio.listar().size()
        );

        System.out.println(
                "Comandos completados correctamente: "
                        + historial.getCantidadEjecutados()
        );
    }

    private static void mostrarPaso(String titulo) {
        System.out.println("\n========================================");
        System.out.println(titulo);
        System.out.println("========================================");
    }

    private static void mostrarCotizacion(OrdenServicio orden) {
        System.out.println(
                orden.getNombreTarifa()
                        + " -> $" + orden.getPresupuesto()
        );
    }

    private static void esperarRechazo(
            Class<? extends RuntimeException> tipoEsperado,
            Runnable accion) {

        try {
            accion.run();
        } catch (RuntimeException e) {

            // Los errores inesperados no se ocultan.
            if (!tipoEsperado.isInstance(e)) {
                throw e;
            }

            System.out.println(
                    "Operación rechazada: " + e.getMessage()
            );

            return;
        }

        throw new AssertionError(
                "La operación debía ser rechazada, "
                        + "pero terminó correctamente."
        );
    }
}
