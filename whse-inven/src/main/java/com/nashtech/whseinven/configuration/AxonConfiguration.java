package com.nashtech.whseinven.configuration;

import org.axonframework.axonserver.connector.TargetContextResolver;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.nashtech")
@org.springframework.context.annotation.Configuration
public class AxonConfiguration {
    @Bean
    public SnapshotTriggerDefinition whseAggregateSnapshotTriggerDefinition(Snapshotter snapshotter, @Value("${axon.aggregate.whseinventory.snapshot-threshold:250}") int threshold) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
    }
}
