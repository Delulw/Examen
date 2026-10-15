package mx.huertos.parcels.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("parcels")
public record Parcel(
        @Id String id,
        String name,
        Location location,
        SoilType soilType,
        String ownerOrLead,
        Instant createdAt) {
}
