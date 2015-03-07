# JUnit

Dominating Java unit test library.

Source code:
<https://github.com/junit-team/junit>

TODO: install with a package manager, and use it from the command line to remove Ubuntu specifics.

Run given class from the command line:
<http://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line>

Run a single test method from the command line: not possible by default!
<http://stackoverflow.com/questions/9288107/run-single-test-from-a-junit-class-using-command-line>

## 3 vs 4

4 is the latest version. This cheatsheet will focus on JUnit 4.

There were significant interface changes between them, so make sure to specify the version on Google searches.

Ubuntu 14.04 `junit` package is version 3: version 4 is at `junit4`.

`import junit.framework.*;` and `import junit.framework.*;` were dropped in JUnit 4

Test classes had to extend `junit.framework.TestCase` in JUnit 3, but not in 4.

JUnit 3 tests name methods had to be prefixed by `test`. This requirement was dropped in JUnit 4 where methods are annotated by `@Test`. The Wiki recommended that JUnit 4 tests don't be prefixed by `test` as that duplicates the annotation.
