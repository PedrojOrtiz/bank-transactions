package ec.tcs.banktransactions.domain.repository;

import ec.tcs.banktransactions.domain.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT COALESCE(SUM(m.valor), 0.0) FROM Movimiento m WHERE m.cuenta.id = :cuentaId")
    double sumSaldoByCuentaId(Long cuentaId);

}
