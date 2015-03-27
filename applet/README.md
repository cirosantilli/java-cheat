# Applet

WIP

Build with:

    $(OUT_DIR)AppletHelloWorld.jar: $(OUT_DIR)AppletHelloWorld$(OUT_EXT)
        jar cf "$@" "$<"
