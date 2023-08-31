package com.nashtech.purchseorders.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LineItem {
    @JsonProperty("itemId")
    String itemId;
    @JsonProperty("description")
    String description;
    @JsonProperty("qty")
    Double qty;
}
