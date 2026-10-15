package mx.huertos.parcels.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.huertos.parcels.model.Parcel;

public interface ParcelRepository extends MongoRepository<Parcel, String> {
}
