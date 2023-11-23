package mtmh.kafka.springboot;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import mtmh.kafka.springboot.controller.SampleEventController;

@SpringBootApplication
public class Application {

	@Autowired
	private SampleEventController sampleEventController;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public void testRun() throws JsonProcessingException, ExecutionException, InterruptedException{
		sampleEventController.index();
	}

}
