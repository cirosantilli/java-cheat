public class HelloWorld {
    private Sayer sayer;

    public HelloWorld(Sayer sayer) {
        this.sayer = sayer;
    }

    public void say(){
        this.sayer.say();
    }
}
