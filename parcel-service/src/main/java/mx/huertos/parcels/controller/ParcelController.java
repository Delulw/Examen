package mx.huertos.parcels.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.huertos.parcels.dto.CreateParcelRequest;
import mx.huertos.parcels.model.Parcel;
import mx.huertos.parcels.service.ParcelService;

@RestController
@RequestMapping("/api/v1/parcels")
public class ParcelController {
    private final ParcelService service;

    public ParcelController(ParcelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Parcel> create(@Valid @RequestBody CreateParcelRequest request) {
        Parcel created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public List<Parcel> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Parcel findById(@PathVariable String id) {
        return service.findById(id);
    }
}
