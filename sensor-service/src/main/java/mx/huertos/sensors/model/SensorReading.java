package mx.huertos.sensors.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sensor_readings")
public record SensorReading(
        @Id String id,
        String parcelId,
        double soilMoisture,
        double temperature,
        double soilPh,
        double nitrogen,
        double phosphorus,
        double potassium,
        double airQuality,
        String notes,
        Instant recordedAt) {
}
