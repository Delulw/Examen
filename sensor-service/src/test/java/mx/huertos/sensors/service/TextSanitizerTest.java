package mx.huertos.sensors.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import mx.huertos.sensors.exception.InvalidInputException;

class TextSanitizerTest {
    @Test
    void removesHtmlAndTrimsPlainText() {
        assertEquals("Sensor calibrado", TextSanitizer.plainText("<b>Sensor calibrado</b> ", "notes"));
    }

    @Test
    void rejectsTextThatBecomesBlankAfterSanitization() {
        assertThrows(InvalidInputException.class,
                () -> TextSanitizer.plainText("<script>alert(1)</script>", "notes"));
    }
}
