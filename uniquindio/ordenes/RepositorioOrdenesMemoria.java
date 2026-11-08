package uniquindio.ordenes;

import java.util.*;

public class RepositorioOrdenesMemoria implements RepositorioOrdenes {
    private final Map<Integer, OrdenServicio> ordenes =
            new LinkedHashMap<>();

    @Override
    public void guardar(OrdenServicio orden) {

        if (orden == null) {
            throw new IllegalArgumentException(
                    "E\"La orden no puede ser nula.\""
            );
        }

        ordenes.put(orden.getNumero(), orden);
    }

    @Override
    public Optional<OrdenServicio> buscarPorNumero(int numero) {
        return Optional.ofNullable(ordenes.get(numero));
    }

    @Override
    public List<OrdenServicio> listar() {
        return new ArrayList<>(ordenes.values());
    }
}
