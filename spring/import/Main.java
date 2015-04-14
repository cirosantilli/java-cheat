import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx =
            new AnnotationConfigApplicationContext(BConfig.class);
        // THIS is what @Import does: it allows us to find beans from A,
        // even though we only havea  context from BConfig.
        A a = ctx.getBean(A.class);
        B b = ctx.getBean(B.class);
        a.say();
        b.say();
    }
}
