package mx.huertos.parcels.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import mx.huertos.parcels.exception.InvalidInputException;

class TextSanitizerTest {
    @Test
    void removesHtmlAndTrimsPlainText() {
        assertEquals("Huerto central", TextSanitizer.plainText("  <b>Huerto</b> central  ", "name"));
    }

    @Test
    void rejectsTextThatBecomesBlankAfterSanitization() {
        assertThrows(InvalidInputException.class,
                () -> TextSanitizer.plainText("<script>alert(1)</script>", "name"));
    }
}
