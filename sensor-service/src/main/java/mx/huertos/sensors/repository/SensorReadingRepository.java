package mx.huertos.sensors.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.huertos.sensors.model.SensorReading;

public interface SensorReadingRepository extends MongoRepository<SensorReading, String> {
    List<SensorReading> findByParcelIdOrderByRecordedAtDesc(String parcelId);
}
