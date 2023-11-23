package mtmh.kafka.springboot.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.apachecommons.CommonsLog;
import mtmh.kafka.springboot.kafka.producer.AppointmentEventProducer;
import mtmh.kafka.springboot.model.Appointment;
import mtmh.kafka.springboot.model.AppointmentEvent;
import mtmh.kafka.springboot.model.AppointmentEventType;

@Service
@CommonsLog
public class AppointmentService {

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    KafkaTemplate<Integer, Object> kafkaTemplate;

    @Autowired
    AppointmentEventProducer producer;

    public AppointmentEvent sendAppointmentEvent() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        AppointmentEvent event = new AppointmentEvent().setAppointmentEventId(123)
                                    .setAppointment(new Appointment().setAddress("away").setEmail("doremon@gmail.com"))
                                    .setAppointmentEventType(AppointmentEventType.NEW);

        var completableFuture = producer.sendAppointmentEvent_ProducerRecord(event);

        //then
        SendResult<Integer, Object> sendResult1 = completableFuture.get();
        assert sendResult1.getRecordMetadata().partition() == 1;
        return (AppointmentEvent) sendResult1.getProducerRecord().value();
    }

    public Appointment processLibraryEvent(ConsumerRecord<Integer, AppointmentEvent> consumerRecord)
            throws JsonProcessingException {
        AppointmentEvent appointmentEvent = consumerRecord.value();
        log.info("libraryEvent : " + appointmentEvent);

        if (appointmentEvent.getAppointmentEventId() != null && (appointmentEvent.getAppointmentEventId() == 999)) {
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        switch (appointmentEvent.getAppointmentEventType()) {
            case NEW -> save(appointmentEvent);
            case UPDATE -> {
                update(appointmentEvent);
            }
            default -> log.info("Invalid Library Event Type");
        }

        return appointmentEvent.getAppointment();
    }

    private void save(AppointmentEvent appointmentEvent) {
        log.error("Successfully Persisted the appointment Event");
    }

    private void update(AppointmentEvent appointmentEvent) {

        if (appointmentEvent.getAppointmentEventId() == null) {
            throw new IllegalArgumentException("Library Event Id is missing");
        }

        log.error("Update is successful for the library Event");
    }

}
