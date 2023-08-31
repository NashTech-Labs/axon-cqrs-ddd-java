package com.nashtech.purchseorders.configuration;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class POConfiguration {

    @Bean
    public SnapshotTriggerDefinition purchaseOrderAggregateSnapshotTriggerDefinition(Snapshotter snapshotter, @Value("${axon.aggregate.purchaseorder.snapshot-threshold:250}") int threshold) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
    }
}
