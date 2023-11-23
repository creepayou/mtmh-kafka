package mtmh.kafka.springboot.kafka.consumer;

import mtmh.kafka.springboot.model.AppointmentEvent;
import mtmh.kafka.springboot.service.AppointmentService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.apachecommons.CommonsLog;

//@Component
@CommonsLog
public class AppointmentEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer, AppointmentEvent> {

    @Autowired
    private AppointmentService service;

    @Override
    @KafkaListener(topics = "appointment-events", groupId = "appointment-consumer-group")
    public void onMessage(ConsumerRecord<Integer, AppointmentEvent> consumerRecord, Acknowledgment acknowledgment) {
        try {
            service.processLibraryEvent(consumerRecord);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("Consumer Record: " + consumerRecord);
        acknowledgment.acknowledge();
    }
}
