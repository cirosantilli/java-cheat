public class HelloWorld {

    private String message;

    // This will act like a constructor.
    public void init(){
        this.message = "Hello world!";
    }

    public void say(){
        System.out.println(this.message);
    }

    public void destroy(){
        System.out.println("destroy");
    }
}
