package ec.tcs.banktransactions.web.controller;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.domain.model.Cuenta;
import ec.tcs.banktransactions.domain.repository.CuentaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CuentaRepository cuentaRepository;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {

        /// Create a new account test
        cuenta = Cuenta.builder()
                .numeroCuenta("CTA123")
                .tipoCuenta("Ahorros")
                .clienteId("CLI123")
                .saldoInicial(1000.0)
                .estado(false)
                .build();

        /// Clean that account if it exists
        cuentaRepository.deleteByClienteId(cuenta.getClienteId());

        /// Save the new test account
        cuentaRepository.save(cuenta);

    }

    @AfterEach
    void clean() {
        cuentaRepository.deleteByClienteId(cuenta.getClienteId());
    }

    @Test
    void testIntegrationCuentaDeleteCreateReadRepositoryController() {
        ResponseEntity<CuentaDTO> response = restTemplate
                .getForEntity("/cuentas/{id}", CuentaDTO.class, cuenta.getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(cuenta.getNumeroCuenta(), response.getBody().getNumeroCuenta());
    }

}