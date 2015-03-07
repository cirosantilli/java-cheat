# Javadoc

Generates HTML documentation from well formatted comments.

Part of the JDK.

Documentation: <http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/javadoc.html>

Sample usage:

    javadoc -d _doc/ A.java B.java

Then view the generated index file `_doc/index.html`.

Private members are not shown by default on the docs, you can enable them with `-private`.

Package documentation can be done with a `package-info.java` file:
<http://stackoverflow.com/questions/624422/how-do-i-document-packages-in-java>
