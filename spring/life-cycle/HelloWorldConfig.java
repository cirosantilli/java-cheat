import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public HelloWorld helloWorld(){
        return new HelloWorld();
    }
}
