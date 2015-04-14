import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {

    /**
    The parameter {@code Sayer} is automatically found
    by looking for a bean with that return type.
    */
    @Bean
    public HelloWorld anyName(Sayer sayer) {
        return new HelloWorld(sayer);
    }

    @Bean
    public Sayer sayer(){
        return new Sayer("Hello World!");
    }
}
