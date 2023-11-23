package com.github.olcmateusz.warglaive.enums;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class FactionDeserializer extends JsonDeserializer<Faction> {

    @Override
    public Faction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectNode node = jsonParser.readValueAsTree();
        String type = node.get("type").asText();

        return Faction.valueOf(type.toUpperCase());
    }
}