package mtmh.kafka.springboot.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.apachecommons.CommonsLog;
import mtmh.kafka.springboot.model.Appointment;
import mtmh.kafka.springboot.model.AppointmentEvent;
import mtmh.kafka.springboot.service.AppointmentService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class AppointmentEventsConsumer {

    @Autowired
    private AppointmentService appointmentService;

    @KafkaListener(topics = {"appointment-events"}, groupId = "appointment-consumer-group")
    public Appointment onMessage(
	@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    ConsumerRecord<Integer, AppointmentEvent> consumerRecord) throws JsonProcessingException {
        Appointment result = appointmentService.processLibraryEvent(consumerRecord);
        log.info("Consumer Record: "+ consumerRecord);
        return result;
    }

    /**
     *  ----- consumer group and partition with intial offset  ----
     *
    @KafkaListener(groupId = "inventory-consumer-group-1",
            topicPartitions = @TopicPartition(topic = "inventory-events",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "2", initialOffset = "0")}))
     */
    public void onMessage_PartitionIntialOffset(ConsumerRecord<Integer, AppointmentEvent> consumerRecord) {
        log.info("Consumer Record: " + consumerRecord);
    }


    /**
     * ----- consumer group and partition with no intial offset  ----
     *
    @KafkaListener(topicPartitions = @TopicPartition(topic = "inventory-events", partitions = { "0", "1" }))
     */
    public void onMessage_PartitionNoOffset(ConsumerRecord<Integer, AppointmentEvent> consumerRecord) {
        log.info("Consumer Record: " + consumerRecord);
    }
}
