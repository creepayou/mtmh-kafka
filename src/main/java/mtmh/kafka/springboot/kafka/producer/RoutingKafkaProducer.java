package mtmh.kafka.springboot.kafka.producer;

import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.stereotype.Component;

import mtmh.kafka.springboot.model.AppointmentEvent;

@Component
public class RoutingKafkaProducer {

    private RoutingKafkaTemplate routingTemplate;

    public void sendDefaultTopic(String message) {
        routingTemplate.send("default-topic", message.getBytes());
    }

    public void sendAppointmentEvent(AppointmentEvent appointmentEvent) {
        routingTemplate.send("appointment-event", appointmentEvent);
    }
}
