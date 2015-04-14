ARGS ?=
OUT_DIR ?= .
OUT_EXT ?= .class
RUN ?= Main

-include Makefile_params

.PHONY: all clean mkdir run

# TODO add: -source 1.7 -bootclasspath /usr/lib/jvm/java-7-oracle/jre/lib/rt.jar 
# Without `bootclasspath`, gives warning on Java 8.
all: mkdir
	javac -d '$(OUT_DIR)' *.java

clean:
	if [ '$(OUT_DIR)' = '.' ]; then rm -f *$(OUT_EXT); else rm -rf $(OUT_DIR); fi

mkdir:
	mkdir -p $(OUT_DIR)

run: all
	cd $(OUT_DIR) && java -ea -D'custom.property=value' $(RUN) $(ARGS)
