package mx.huertos.sensors.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateSensorReadingRequest(
        @NotBlank(message = "El identificador de la parcela es obligatorio.")
        @Pattern(regexp = "[a-fA-F0-9]{24}", message = "El identificador de la parcela debe tener 24 caracteres hexadecimales.")
        String parcelId,

        @NotNull(message = "La humedad del suelo es obligatoria.")
        @DecimalMin(value = "0.0", message = "La humedad del suelo no puede ser menor que 0%.")
        @DecimalMax(value = "100.0", message = "La humedad del suelo no puede exceder 100%.")
        Double soilMoisture,

        @NotNull(message = "La temperatura es obligatoria.")
        @DecimalMin(value = "-80.0", message = "La temperatura debe estar entre -80 y 80 °C.")
        @DecimalMax(value = "80.0", message = "La temperatura debe estar entre -80 y 80 °C.")
        Double temperature,

        @NotNull(message = "El pH del suelo es obligatorio.")
        @DecimalMin(value = "0.0", message = "El pH debe estar entre 0 y 14.")
        @DecimalMax(value = "14.0", message = "El pH debe estar entre 0 y 14.")
        Double soilPh,

        @NotNull(message = "El nivel de nitrógeno es obligatorio.")
        @DecimalMin(value = "0.0", message = "El nivel de nitrógeno no puede ser negativo.")
        @DecimalMax(value = "10000.0", message = "El nivel de nitrógeno no puede exceder 10000.")
        Double nitrogen,

        @NotNull(message = "El nivel de fósforo es obligatorio.")
        @DecimalMin(value = "0.0", message = "El nivel de fósforo no puede ser negativo.")
        @DecimalMax(value = "10000.0", message = "El nivel de fósforo no puede exceder 10000.")
        Double phosphorus,

        @NotNull(message = "El nivel de potasio es obligatorio.")
        @DecimalMin(value = "0.0", message = "El nivel de potasio no puede ser negativo.")
        @DecimalMax(value = "10000.0", message = "El nivel de potasio no puede exceder 10000.")
        Double potassium,

        @NotNull(message = "La medición de calidad del aire es obligatoria.")
        @DecimalMin(value = "0.0", message = "La calidad del aire debe estar entre 0 y 500.")
        @DecimalMax(value = "500.0", message = "La calidad del aire debe estar entre 0 y 500.")
        Double airQuality,

        @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres.")
        String notes) {
}
