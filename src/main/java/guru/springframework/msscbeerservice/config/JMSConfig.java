package guru.springframework.msscbeerservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JMSConfig {
    
    public static final String MY_QUEUE = "my-hello-world";
    public static final String MY_SEND_RCV_QUEUE = "my-send-recv";
    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    
    @Bean
    MessageConverter messageConverter() {
	MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	converter.setTargetType(MessageType.TEXT);
	converter.setTypeIdPropertyName("_type");
	return converter;
    }

}
