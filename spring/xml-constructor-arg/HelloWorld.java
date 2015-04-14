public class HelloWorld {
    private Sayer sayer;
    private String message2;

    public HelloWorld(Sayer sayer, String message2){
        this.sayer = sayer;
        this.message2 = message2;
    }

    public void say(){
        this.sayer.say();
        System.out.println(this.message2);
    }
}
