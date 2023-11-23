package com.github.olcmateusz.warglaive.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FactionDeserializer.class)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Faction {
    ALLIANCE("ALLIANCE"),
    HORDE("HORDE");

    private final String type;

    Faction(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
