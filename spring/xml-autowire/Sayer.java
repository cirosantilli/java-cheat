public class Sayer {
    private String message;

    public void setMessage(String message){
        this.message  = message;
    }

    public void say(){
        System.out.println(this.message);
    }
}
