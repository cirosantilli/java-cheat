# Pros and cons

Cons: http://c2.com/cgi/wiki?JavaDesignFlaws

Java is very sane:

-   statically typed: you have to tell the type of each variable. This makes things much easier to understand and document.

-   all capabilities are based on a class structure. No insane global functions like python `len()`.

-   Javadoc utility is officially maintained. Documentation generation just works.

-   more cross platform capabilities than certain compiled languages such as C++.

A consequence of sanity is that sometimes you have to type much more than on a slightly insaner language like Python. Examples:

-   no tuples to return multiple values from functions

-   no language built-ins for lists and dictionaries.

-   one class per file with same name as the file.
    Renaming files is a pain. Creating scripts is also a pain.

Some annoying Java facts include:

-   primitives and arrays. They exist for performance,
    but live in a different world than Objects, e.g. you can't do:

        1.toString()

    like you would on a more dynamic and slower language like Ruby.

-   Javadoc sucks: you can't reuse anything across methods.

Sanity pays off when source changes slowly. This is usually the case for large projects. If you are doing simple scripting, Python may be better. But remember: scripts grow.
