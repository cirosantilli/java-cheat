public class HelloWorld {
    private Sayer sayer;

    public void setSayer(Sayer sayer){
        this.sayer  = sayer;
    }

    public void say(){
        this.sayer.say();
    }
}
