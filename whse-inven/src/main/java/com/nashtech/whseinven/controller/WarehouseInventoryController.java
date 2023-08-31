package com.nashtech.whseinven.controller;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import com.nashtech.whseinven.command.CreateWarehouseInventory;
import com.nashtech.whseinven.command.ReceivePOItem;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;

@RestController
public class WarehouseInventoryController {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
            .lookupClass());
    private final CommandGateway commandGateway;

    public WarehouseInventoryController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/createWhse/{warehouse_location}/item/{item_id}")
    public CompletableFuture<ResponseEntity<String>> createWarehouseInventory(@PathVariable("warehouse_location") String warehouseLocation, @PathVariable("item_id") String itemId) {
        return commandGateway.send(new CreateWarehouseInventory(new WarehouseItemId(itemId, warehouseLocation)))
                .thenApply(created -> new ResponseEntity<>("Created Warehouse Inventory with ItemId " + itemId + " at warehouse " + warehouseLocation, HttpStatus.CREATED))
                .exceptionally(exception -> {
                    if (exception.getMessage().contains("AXONIQ-2000")) {
                        logger.error("Inventory for Item {} exists at location {}", itemId, warehouseLocation);
                        return new ResponseEntity<>("Inventory for Item " + itemId + " exists at location " + warehouseLocation, HttpStatus.ALREADY_REPORTED);
                    }
                    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @PostMapping("/receiveItem/{warehouse_location}/item/{item_id}/qty/{qty}")
    public CompletableFuture<ResponseEntity<String>> receivePOItem(@PathVariable("warehouse_location") String warehouseLocation, @PathVariable("item_id") String itemId, @PathVariable("qty") Double qty) {
        return commandGateway.send(new ReceivePOItem(new WarehouseItemId(itemId, warehouseLocation), qty))
                .thenApply(item -> {
                    logger.info("Item {} received successfully at warehouse {}", itemId, warehouseLocation);
                    return new ResponseEntity<>("Item Received Successfully!", HttpStatus.OK);
                }).exceptionally(exception -> {
                            logger.error(exception.getMessage());
                            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
                        }
                );
    }

}
