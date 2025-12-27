package com.Veterinaria.Gestion.controller;

import com.Veterinaria.Gestion.model.Mascota;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    private List<Mascota> mascotas = new ArrayList<>();

    public MascotaController() {

        mascotas.add(new Mascota(1L, "Firulais", "Perro", LocalDate.of(2020, 5, 10), "Juan"));
        mascotas.add(new Mascota(2L, "Michi", "Gato", LocalDate.of(2023, 1, 15), "Maria"));
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> obtenerTodas() {
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        return mascotas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/menores")
    public ResponseEntity<List<Mascota>> obtenerMenores(@RequestParam int edad) {
        List<Mascota> filtradas = mascotas.stream()
                .filter(m -> m.getEdad() < edad)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtradas);
    }

    @PostMapping
    public ResponseEntity<Mascota> registrar(@RequestBody Mascota nuevaMascota) {
        mascotas.add(nuevaMascota);
        return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
    }
}