package mx.huertos.parcels.dto;

import mx.huertos.parcels.model.Location;
import mx.huertos.parcels.model.SoilType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateParcelRequest(
        @NotBlank(message = "El nombre de la parcela es obligatorio.")
        @Size(max = 100, message = "El nombre de la parcela no puede exceder 100 caracteres.")
        String name,

        @NotNull(message = "La ubicación es obligatoria.")
        @Valid
        LocationRequest location,

        @NotNull(message = "El tipo de suelo es obligatorio.")
        SoilType soilType,

        @NotBlank(message = "El responsable de la parcela es obligatorio.")
        @Size(max = 100, message = "El responsable no puede exceder 100 caracteres.")
        String ownerOrLead) {

    public record LocationRequest(
            @NotNull(message = "La latitud es obligatoria.")
            @jakarta.validation.constraints.DecimalMin(value = "-90.0", message = "La latitud debe estar entre -90 y 90.")
            @jakarta.validation.constraints.DecimalMax(value = "90.0", message = "La latitud debe estar entre -90 y 90.")
            Double latitude,

            @NotNull(message = "La longitud es obligatoria.")
            @jakarta.validation.constraints.DecimalMin(value = "-180.0", message = "La longitud debe estar entre -180 y 180.")
            @jakarta.validation.constraints.DecimalMax(value = "180.0", message = "La longitud debe estar entre -180 y 180.")
            Double longitude) {

        public Location toLocation() {
            return new Location(latitude, longitude);
        }
    }
}
