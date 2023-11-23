package mtmh.kafka.springboot.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.apachecommons.CommonsLog;
import mtmh.kafka.springboot.model.AppointmentEvent;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CommonsLog
@Component
public class AppointmentEventProducer {

    @Autowired
    private KafkaTemplate<Integer, Object> kafkaTemplate;

    public CompletableFuture<SendResult<Integer, Object>> sendAppointmentEvent_Async(AppointmentEvent appointmentEvent) throws JsonProcessingException {

        var key = appointmentEvent.getAppointmentEventId();

        var completableFuture = kafkaTemplate.send("appointment-events", key, appointmentEvent);

        return completableFuture.whenComplete(((sendResult, throwable) -> {
            if (throwable != null) {
                handleFailure(key, appointmentEvent, throwable);
            } else {
                handleSuccess(key, appointmentEvent, sendResult);
            }
        }));
    }

    public CompletableFuture<SendResult<Integer, Object>> sendAppointmentEvent_ProducerRecord(AppointmentEvent appointmentEvent) throws JsonProcessingException {
        var key = appointmentEvent.getAppointmentEventId();
        var producerRecord = buildProducerRecord(key, appointmentEvent);
        var completableFuture = kafkaTemplate.send(producerRecord);

        return completableFuture.whenComplete(((sendResult, throwable) -> {
            if (throwable != null) {
                handleFailure(key, appointmentEvent, throwable);
            } else {
                handleSuccess(key, appointmentEvent, sendResult);
            }
        }));
    }

    private ProducerRecord<Integer, Object> buildProducerRecord(Integer key, Object value) {
        List<Header> recordHeader = List.of(new RecordHeader("event-source", "appointment-event-producer".getBytes()));
        return new ProducerRecord<>("appointment-events", null, key, value, recordHeader);
    }

    private void handleSuccess(Integer key, Object value, SendResult<Integer, Object> sendResult) {
        log.info("Message sent successfully for the key: " + key + " and the value: " + value);
    }

    private void handleFailure(Integer key, Object value, Throwable throwable) {
        log.error("Error sending message and exception is " + throwable.getMessage(), throwable);
    }
}
