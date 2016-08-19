package cz.rockpoint.utils.JodaAdapter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

// Class for JodaTimeDialect with necessary format methods
class JodaTimeExpressionObject {

    private final Locale locale;

    JodaTimeExpressionObject(Locale locale) {
        this.locale = locale;
    }

    // Method adjust given format to the required one
    public String format(LocalTime localTime, String format) {
        return format(localTime, DateTimeFormat.forPattern(format));
    }

    // Method works with Joda-LocalTime and prints with required format
    private String format(LocalTime localTime, DateTimeFormatter formatter) {
        return formatter.withLocale(locale).print(localTime);
    }
}
