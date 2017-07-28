package hello;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        SpringApplication.run(Application.class, args);
        KafkaConsumer kafkaConsumer = new KafkaConsumer();
        // Configure Kafka consumer
        kafkaConsumer.initialize();
        // Start consumption
   
			kafkaConsumer.consume();
	
    }
}
