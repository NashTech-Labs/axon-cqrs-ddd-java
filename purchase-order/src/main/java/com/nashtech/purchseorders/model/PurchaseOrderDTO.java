package com.nashtech.purchseorders.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseOrderDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("lineItems")
    private Set<LineItem> lineItemSet;
    @JsonProperty("warehouseLocation")
    private String warehouseLocation;
}
