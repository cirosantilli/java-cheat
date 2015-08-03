public class ConstructorEmpty {
    // Generates the same bytecode as the default constructor.
    // http://stackoverflow.com/questions/17619041/is-constructor-generated-default-constructor
    //
    // this is passed to the constructor as the first parameter,
    // and thus loaded with aload_0
    public ConstructorEmpty() {}
    public static void main(String[] args) {}
}
