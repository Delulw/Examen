package mx.huertos.sensors.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.huertos.sensors.dto.CreateSensorReadingRequest;
import mx.huertos.sensors.model.SensorReading;
import mx.huertos.sensors.service.SensorReadingService;

@RestController
@RequestMapping("/api/v1/sensor-readings")
public class SensorReadingController {
    private final SensorReadingService service;

    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SensorReading> create(
            @Valid @RequestBody CreateSensorReadingRequest request) {
        SensorReading created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public List<SensorReading> findAll(@RequestParam(required = false) String parcelId) {
        return parcelId == null ? service.findAll() : service.findByParcelId(parcelId);
    }

    @GetMapping("/{id}")
    public SensorReading findById(@PathVariable String id) {
        return service.findById(id);
    }
}
