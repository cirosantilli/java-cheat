/**
Generates a third {@code InnerPrivate$1.class} file.
http://stackoverflow.com/questions/380406/java-inner-class-class-file-names
http://stackoverflow.com/questions/2883181/why-is-an-anonymous-inner-class-containing-nothing-generated-from-this-code
*/
public class InnerPrivate {
    { new Inner2(); }
    class Inner2 {
        private Inner2() { }
    }
}
