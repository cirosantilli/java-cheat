# Javac import

If `javac` finds an import, it looks for:

- the source code of the import. It then compiles them automatically, if the timestamp of the compiled file is older, just like make.
- it it cannot find the source code, it uses `.class` files if any are found
