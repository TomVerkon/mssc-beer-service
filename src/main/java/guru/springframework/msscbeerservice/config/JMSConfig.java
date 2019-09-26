package guru.springframework.msscbeerservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JMSConfig {
    
    public static final String MY_QUEUE = "my-hello-world";
    public static final String MY_SEND_RCV_QUEUE = "my-send-recv";
    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    
    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
	MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	converter.setTargetType(MessageType.TEXT);
	converter.setTypeIdPropertyName("_type");
	converter.setObjectMapper(objectMapper);
	return converter;
    }

}
