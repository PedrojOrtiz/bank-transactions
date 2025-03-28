package ec.tcs.banktransactions.web.controller;

import ec.tcs.banktransactions.application.dto.CuentaDTO;
import ec.tcs.banktransactions.application.service.CuentaService;
import ec.tcs.banktransactions.domain.model.Cuenta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.save(cuentaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerCuenta(@PathVariable Long id) throws Throwable {
        return ResponseEntity.ok(cuentaService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) throws Throwable {
        return ResponseEntity.ok(cuentaService.updateCuenta(id, cuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
