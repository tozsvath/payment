package com.payment.notification.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    int[] timestampArray = p.readValueAs(int[].class);
    if (timestampArray.length == 7) {
      LocalDateTime localDateTime = LocalDateTime.of(
          timestampArray[0],
          timestampArray[1],
          timestampArray[2],
          timestampArray[3],
          timestampArray[4],
          timestampArray[5],
          timestampArray[6]
      );
      return localDateTime;
    }
    return null;
  }
}
