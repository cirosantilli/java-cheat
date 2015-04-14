public class HelloWorld {
    private String message;
    private String message2;

    public void setMessage(String message){
        this.message = message;
    }

    public void setMessage2(String message2){
        this.message2 = message2;
    }


    public void say(){
        System.out.println(this.message);
        System.out.println(this.message2);
    }
}
