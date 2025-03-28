package ec.tcs.banktransactions.web.controller;

import ec.tcs.banktransactions.application.dto.MovimientoDTO;
import ec.tcs.banktransactions.application.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDTO> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.createMovimiento(movimientoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerMovimiento(@PathVariable Long id) throws Throwable {
        return ResponseEntity.ok(movimientoService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listarMovimientos() {
        return ResponseEntity.ok(movimientoService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
