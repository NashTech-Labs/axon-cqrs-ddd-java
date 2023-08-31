package com.nashtech.whseinven.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "whse_inven")
public class WarehouseInventoryQueryEntity {

    @Id
    String id;
    @Column(name = "itemId")
    String itemId;

    @Column(name = "whse_loc")
    String whseLoc;

    @Column(name = "qoo")
    Double quantityOnOnder;

    @Column(name = "qoh")
    Double quantityOnHand;

    @Column(name = "openorders")
    String openPurchaseOrders;
}
