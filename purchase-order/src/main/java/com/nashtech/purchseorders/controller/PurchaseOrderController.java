package com.nashtech.purchseorders.controller;

import com.nashtech.purchseorders.command.CreatePurchaseOrder;
import com.nashtech.purchseorders.model.PurchaseOrderDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;

@RestController
public class PurchaseOrderController {

    private final CommandGateway commandGateway;

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
            .lookupClass());

    public PurchaseOrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/createPo")
    public CompletableFuture<ResponseEntity<String>> createPurchaseOrder(@RequestBody PurchaseOrderDTO po){
        logger.info("The details in the line item {}", po.getLineItemSet());
        return commandGateway.send(new CreatePurchaseOrder(po.getId(), po.getLineItemSet(), po.getWarehouseLocation()))
                 .thenApply(createPurchaseOrder -> {
                     return new ResponseEntity<>("Created purchase Order with Id " + po.getId(), HttpStatus.CREATED);
                 })
                .exceptionally( exception -> {
                    if(exception.getMessage().contains("AXONIQ-2000")){
                        logger.error("Purchase Order with Id {} already exists, create consider one with new Id", po.getId());
                        return   new ResponseEntity<>("Purchase Order with Id" + po.getId() + " exist", HttpStatus.BAD_REQUEST);
                    }
                  else return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}
