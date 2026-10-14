package mx.huertos.sensors.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import mx.huertos.sensors.exception.InvalidInputException;

public final class TextSanitizer {
    private TextSanitizer() {
    }

    public static String plainText(String value, String fieldName) {
        String sanitized = Jsoup.clean(value, Safelist.none()).replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "").trim();
        if (sanitized.isBlank()) {
            throw new InvalidInputException("El campo '" + fieldName + "' no puede quedar vacío después de sanitizarse.");
        }
        return sanitized;
    }
}
