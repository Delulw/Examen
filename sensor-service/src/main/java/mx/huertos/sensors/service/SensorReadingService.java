package mx.huertos.sensors.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.huertos.sensors.dto.CreateSensorReadingRequest;
import mx.huertos.sensors.exception.InvalidInputException;
import mx.huertos.sensors.exception.ResourceNotFoundException;
import mx.huertos.sensors.model.SensorReading;
import mx.huertos.sensors.repository.SensorReadingRepository;

@Service
public class SensorReadingService {
    private final SensorReadingRepository repository;

    public SensorReadingService(SensorReadingRepository repository) {
        this.repository = repository;
    }

    public SensorReading create(CreateSensorReadingRequest request) {
        String notes = request.notes() == null || request.notes().isBlank()
                ? null
                : TextSanitizer.plainText(request.notes(), "notes");
        SensorReading reading = new SensorReading(null, request.parcelId(),
                request.soilMoisture(), request.temperature(), request.soilPh(),
                request.nitrogen(), request.phosphorus(), request.potassium(),
                request.airQuality(), notes, Instant.now());
        return repository.save(reading);
    }

    public List<SensorReading> findAll() {
        return repository.findAll();
    }

    public SensorReading findById(String id) {
        validateId(id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una medición con identificador '" + id + "'."));
    }

    public List<SensorReading> findByParcelId(String parcelId) {
        validateId(parcelId);
        return repository.findByParcelIdOrderByRecordedAtDesc(parcelId);
    }

    private void validateId(String id) {
        if (id == null || !id.matches("[a-fA-F0-9]{24}")) {
            throw new InvalidInputException(
                    "El identificador debe ser un ObjectId hexadecimal de 24 caracteres.");
        }
    }
}
