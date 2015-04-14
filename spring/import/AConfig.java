import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AConfig {
   @Bean
   public A a(){
       return new A();
   }
}
