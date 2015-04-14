import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {

    // The usual name for `anyName` would be helloWorld.
    @Bean
    public HelloWorld anyName(){
        return new HelloWorld();
    }

    // ERROR: `ctx.getBean` finds methods by the return value,
    // and can only work when there is a single method
    // that returns `HelloWorld`.
    //@Bean
    //public HelloWorld anyName2(){
        //return new HelloWorld();
    //}
}
