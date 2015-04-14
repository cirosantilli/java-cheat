# update-java-alternatives

If you are maintaining two Java version on a single Linux machine, you can run `update-alternatives` for executables in one go.

Setting `JAVA_HOME` does not change which `java` is used: it only depends on your `PATH`.

List available alternatives:

    update-java-alternatives -l

Sample output:

    java-7-oracle 1 /usr/lib/jvm/java-7-oracle
    java-8-oracle 2 /usr/lib/jvm/java-8-oracle

Choose one of the alternatives:

    sudo update-java-alternatives -s java-7-oracle
