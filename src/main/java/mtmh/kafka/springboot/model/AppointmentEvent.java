package mtmh.kafka.springboot.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AppointmentEvent {

    private Integer appointmentEventId;
    private Appointment appointment;
    private AppointmentEventType appointmentEventType;

}
