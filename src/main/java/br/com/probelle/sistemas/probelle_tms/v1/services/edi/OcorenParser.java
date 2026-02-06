package br.com.probelle.sistemas.probelle_tms.v1.services.edi;

import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OcorenParser {

  public List<OcorenEvent> parse(String rawContent) {
    if (rawContent == null || rawContent.isBlank()) {
      throw new ValidationException("OCOREN content is empty");
    }
    List<OcorenEvent> events = new ArrayList<>();
    String[] lines = rawContent.split("\\r?\\n");
    for (String line : lines) {
      if (line == null || line.isBlank()) {
        continue;
      }
      String[] parts = line.split("\\|");
      if (parts.length < 2) {
        throw new ValidationException("Invalid OCOREN line: " + line);
      }
      Map<String, String> fields = new HashMap<>();
      for (int i = 1; i < parts.length; i++) {
        String[] kv = parts[i].split("=", 2);
        if (kv.length == 2) {
          fields.put(kv[0].trim().toUpperCase(Locale.ROOT), kv[1].trim());
        }
      }
      String manifestNo = fields.get("MANIFEST_NO");
      String code = fields.get("CODE");
      String desc = fields.get("DESC");
      String dt = fields.get("DT");
      if (manifestNo == null || code == null || dt == null) {
        throw new ValidationException("Missing fields in OCOREN line: " + line);
      }
      LocalDateTime occurredAt = parseDateTime(dt);
      events.add(new OcorenEvent(manifestNo, code, desc, occurredAt));
    }
    return events;
  }

  private LocalDateTime parseDateTime(String value) {
    try {
      return OffsetDateTime.parse(value).toLocalDateTime();
    } catch (Exception ignored) {
      try {
        return LocalDateTime.parse(value);
      } catch (Exception ex) {
        throw new ValidationException("Invalid date: " + value);
      }
    }
  }
}
