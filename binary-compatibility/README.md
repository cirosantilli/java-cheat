# Binary compatibility

Determines what updates you can make to class files, so that existing dependant libraries will not have to be modified.

Specified by the [JLS 7 Chapter 13](http://docs.oracle.com/javase/specs/jls/se7/html/jls-13.html).

Things that break APIs:

-   differentiate between preconditions and postconditions.

    Example of preconditions: method arguments.

    Example of post-condition: method return.

    Strengthening preconditions (e.g. take a derived class) breaks API, strengthening post-conditions does not, and vice versa.

-   adding methods to interface or non-final class

    If clients had already extended and added a method with the same name, the API breaks.

## Bibliography

- <https://wiki.eclipse.org/Evolving_Java-based_APIs>, <https://wiki.eclipse.org/Evolving_Java-based_APIs_2>
