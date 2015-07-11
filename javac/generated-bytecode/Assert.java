// http://stackoverflow.com/questions/2758224/what-does-the-java-assert-keyword-do-and-when-should-it-be-used/29439538#29439538
public class Assert {
    // TODO how does this not conflict with the static synthetic generated?
    //static final String $assertionsDisabled = "";
    public static void main(String[] args) {
        //assert $assertionsDisabled.length()
        assert System.currentTimeMillis() == 0L;
    }
}
