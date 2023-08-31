package com.nashtech.purchseorders.model;

import lombok.Value;

@Value
public class Receipt {
    LineItem lineItem;
    Double qtyReceived;
}
