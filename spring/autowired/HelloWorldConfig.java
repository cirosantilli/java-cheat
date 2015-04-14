import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {
    /*
    This will be automatically setup by a call to a bean.

    TODO how is the bean found? Return type, method name?

    Try commentting the annotation out to see the error.
    */
    @Autowired
    Sayer sayer;

    @Bean
    public HelloWorld anyName() {
        return new HelloWorld(this.sayer);
    }

    @Bean
    public Sayer sayer(){
        return new Sayer("Hello World!");
    }
}
