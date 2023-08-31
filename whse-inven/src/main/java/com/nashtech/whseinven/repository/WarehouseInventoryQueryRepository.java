package com.nashtech.whseinven.repository;

import com.nashtech.whseinven.entity.WarehouseInventoryQueryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.stream.Stream;

public interface WarehouseInventoryQueryRepository extends CrudRepository<WarehouseInventoryQueryEntity, String> {
    @Query(value = "select w from whse_inven w where w.whse_loc = :location", nativeQuery = true)
    Stream<WarehouseInventoryQueryEntity> findbyWarehouseLocation(String location);

    @Modifying
    @Query(value = "update whse_inven set qoo = qoo + :qty, openorders=concat(openorders, ', ', :poId) where id=:id", nativeQuery = true)
    int IncreaseQuantityOnOrder(String id, Double qty, String poId);
    @Modifying
    @Query(value = "update whse_inven set qoh = qoh + :qty, qoo = qoo - :qty where id=:id", nativeQuery = true)
    int updateInventory(String id, Double qty);
}
