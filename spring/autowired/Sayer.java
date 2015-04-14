public class Sayer {
    private String message;

    public Sayer(String message){
        this.message  = message;
    }

    public void say(){
        System.out.println(this.message);
    }
}
