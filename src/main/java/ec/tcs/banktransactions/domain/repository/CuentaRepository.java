package ec.tcs.banktransactions.domain.repository;

import ec.tcs.banktransactions.domain.model.Cuenta;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Cuenta> findByClienteId(String clienteId);

    @Modifying
    @Transactional
    //@Query("UPDATE Cuenta c SET c.estado = false WHERE c.clienteId = :clienteId")
    @Query("DELETE FROM Cuenta c WHERE c.clienteId = :clienteId")
    void deleteByClienteId(String clienteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

}
