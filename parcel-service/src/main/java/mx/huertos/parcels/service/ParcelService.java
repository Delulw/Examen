package mx.huertos.parcels.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.huertos.parcels.dto.CreateParcelRequest;
import mx.huertos.parcels.exception.ResourceNotFoundException;
import mx.huertos.parcels.model.Parcel;
import mx.huertos.parcels.repository.ParcelRepository;

@Service
public class ParcelService {
    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
    }

    public Parcel create(CreateParcelRequest request) {
        String name = TextSanitizer.plainText(request.name(), "name");
        String ownerOrLead = TextSanitizer.plainText(request.ownerOrLead(), "ownerOrLead");
        Parcel parcel = new Parcel(null, name, request.location().toLocation(),
                request.soilType(), ownerOrLead, Instant.now());
        return repository.save(parcel);
    }

    public List<Parcel> findAll() {
        return repository.findAll();
    }

    public Parcel findById(String id) {
        if (id == null || !id.matches("[a-fA-F0-9]{24}")) {
            throw new mx.huertos.parcels.exception.InvalidInputException(
                    "El identificador de la parcela debe ser un ObjectId hexadecimal de 24 caracteres.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una parcela con identificador '" + id + "'."));
    }
}
