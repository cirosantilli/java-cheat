/**
Magic first sentence. Stops after first  that is followed by a (blank,
tab, or line terminator), or at the first block tag.
<p>
Document comments must start with two asterisks {@code **}.
<p>
The main format is HTML. By convention, auto-closing tags
like {@code p} and {@code li} are left unclosed:
<ul>
  <li>a
  <li>b
  <li>c
</ul>

<h1># literal</h1>

<p>
Used to escape the at sign.
<p>
{@literal @}param

<h1># link</h1>

<p>
Class in current package: {@link Main2}.
<p>
Method in current class: {@link #method}.
<p>
Field in current class: {@link #field}.
<p>
Field in base class: {@link #baseField}.
<p>
Default overloaded method: {@link #overload}. TODO which is selected?
Seems to be the one that comes first on the source code.
<p>
Overloaded method with int: {@link #overload(int)}
<p>
Overloaded method with float {@link #overload(float)}
<p>
Another package: {@link package0.Main}.
<p>
Custom link content: {@link Main2 main2}. Shows only {@code main2}.
<p>
JCL: TODO {@link java.lang.String}. But it did check that the class exists: {@code NotAClass} would fail.
<p>
The manpage says that the label cannot contain curly braces: {@link java.lang.String {}<b>outside</b>}, you must use the HTML escape.
<p>
Link to external Javadoc of JDK or other libraries:
<a href="http://stackoverflow.com/questions/17580248/javadocs-link-to-external-javadoc">http://stackoverflow.com/questions/17580248/javadocs-link-to-external-javadoc</a>
<p>
Link to a resource (non-Java) file: {@code@link resource} Not possible?
<a href="http://stackoverflow.com/questions/8712556/reference-resources-in-javadoc">http://stackoverflow.com/questions/8712556/reference-resources-in-javadoc"</a>

<h1># value</h1>

<p>
Inline the value of a static final field, and generate a link to it.
<p>
{@code STATIC_FINAL_INT =} {@value #STATIC_FINAL_INT}
<p>
Cannot be put inside another Javadoc code block:
{@code STATIC_FINAL_INT =} {@value #STATIC_FINAL_INT}}
<p>
Any compile time constant is parsed alright:
{@code STATIC_FINAL_INT_SUM =} {@value #STATIC_FINAL_INT_SUM}
<p>
String constants are automagically quoted:
{@code STATIC_FINAL_STRING =} {@value #STATIC_FINAL_STRING}
<p>
Objects simply don't show: {@code STATIC_FINAL_OBJECT = @value #STATIC_FINAL_OBJECT}. Using {@code toString} could not work because even though final, the object contents can still be changed.
<a href="http://stackoverflow.com/questions/19807696/how-to-use-value-tag-in-javadoc">http://stackoverflow.com/questions/19807696/how-to-use-value-tag-in-javadoc</a>
Javadoc 8 gives an error.

<h1># linkplain</h1>

<p>
Like link, but don't surround the label in a code block: TODO I don't see any difference!
{@linkplain #method "The label with linkplain"}, {@linkplain #method "The label with link"}.

<h1># code</h1>

<p>
Escapes everything inside it: good to add literal XML to pages to avoid escaping the tags. But you still need pre.
<pre>
{@code
<p>
Literal HTML.
</p>
}
</pre>
<p>
The closing curly brace is unescapable: {@code printf"}<b>outside</b>"}
<p>
But it does consider nesting the nesting of what is inside code.
So unless you have code which contains unbalanced curly braces, it does not matter:
{@code {}<b>outside</b>}
{@code {{}}<b>outside</b>}

<h1># see</h1>

<p>
Links to other classes or methods. Multiple can be used.
<p>
If it starts with {@code <}, then it is left as is. This makes it easy to add URLs with {@code <a>} elements.

@see Main
@see Main2
@see <a href="http://example.com">example.com</a>
*/
public class Main extends Base {

    /**
     * {@code @value} without argument on a static final field: {@value}
     */
    public static final int STATIC_FINAL_INT = 0;

    public static final int STATIC_FINAL_INT_SUM = 1 + 1;

    public static final String STATIC_FINAL_STRING = "abc";

    public static final Integer STATIC_FINAL_OBJECT = new Integer(1);

    /**
     * Method.
     * <p>
     * If you forget to document a param or a non-void return you will get a warning.
     * <p>
     * If you add a param that is not in the signature, it gives an error.
     * <p>
     * @param  i desciption of i
     * @param  f desciption of f
     * @param  main class parameter
     * @param  iFinal final argument. {@code final} does
     *                not show because it makes to difference to callers asdf
     * @return The return value
     * @throws IllegalArgumentException Does not check if it can actually throw.
     *                                  But the name must be resolved.
     */
    public int method(int i, float f, Main main, final int iFinal) {
        return 0;
    }

    public void overload(int i) {}
    public void overload(float f) {}

    /*
     *This is not used by javadoc since the comment starts with a single asterisk.
     */
    public void singleAsterisk() {}

    /**
     * Protected methods are included by default.
     */
    protected void protectedMethod() {}

    /**
     * Private methods are excluded by default.
     * <p>
     * They can be enabled by the {@code -private} flag.
     */
    private void privateMethod() {}

    /**
     * Documentation of the field.
     */
    public int field;

    // #inheritDoc

        /**
         * inheritDoc can inherit the method body and arguments separtely:
         * <p>
         * Before.
         * {@inheritDoc}
         * After.
         * <p>
         * If you only add a single inheritDoc tag as documentation,
         * then the parameters are imported by default. See {@link #inheritDocOnly}
         *
         * @param i Before. {@inheritDoc} After.
         */
        @Override
        public void inheritDoc(int i) {}

        /**
         * {@inheritDoc}
         */
        @Override
        public void inheritDocOnly(int i) {}

        /**
         * {@inheritDoc}
         *
         * @param i derived
         */
        @Override
        public void inheritDocBodyOnly(int i) {}

        // To see if it automatically inherits or not.
        // By default, the copy does happen, and gets a marker as:
        // `Description copied from class: Base`
        public void inheritAuto() {}
}
