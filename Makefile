OUT_DIR ?= ./target/
RUN ?= Main

.PHONY: all clean mkdir run

all: mkdir $(OUTS)

# TODO add: -source 1.7.
# Problem: gives warning on Java 8.
all:
	javac -d '$(OUT_DIR)' *.java

clean:
	rm -rf $(OUT_DIR)

mkdir:
	mkdir -p $(OUT_DIR)

run: all
	cd $(OUT_DIR) && java -ea -D'custom.property=value' $(RUN) arg0 arg1
