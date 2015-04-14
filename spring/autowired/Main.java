import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx =
            new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        // This magically searches HelloWorldConfig
        // for a method that returns HelloWorld.
        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        helloWorld.say();
    }
}
