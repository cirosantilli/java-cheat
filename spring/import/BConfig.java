import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// To import multiple classes, use:
// @Import(value = {AConfig.class, CConfig.class})
@Import(AConfig.class)
public class BConfig {
   @Bean
   public B b(){
       return new B();
   }
}
