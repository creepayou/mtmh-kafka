package mtmh.kafka.springboot.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import mtmh.kafka.springboot.model.AppointmentEvent;
import mtmh.kafka.springboot.service.AppointmentService;

@RestController
public class SampleEventController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/test")
    public AppointmentEvent index() throws JsonProcessingException, ExecutionException, InterruptedException {
        return appointmentService.sendAppointmentEvent();
    }

}
