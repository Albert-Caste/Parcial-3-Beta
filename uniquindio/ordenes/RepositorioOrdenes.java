package uniquindio.ordenes;

import java.util.List;
import java.util.Optional;

public interface RepositorioOrdenes {
    void guardar(OrdenServicio orden);

    Optional<OrdenServicio> buscarPorNumero(int numero);

    List<OrdenServicio> listar();
}
