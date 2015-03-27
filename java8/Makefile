IN_EXT ?= .java
OUT_EXT ?= .class
RUN ?= Main

INS := $(wildcard *$(IN_EXT))
OUTS_NOEXT := $(basename $(INS))
OUTS := $(addsuffix $(OUT_EXT), $(OUTS_NOEXT))

.PHONY: all clean

all: $(OUTS)

%$(OUT_EXT): %$(IN_EXT)
	javac -g -source 1.8 '$<'

clean:
	rm -f *$(OUT_EXT)

run:
	java '$(RUN)'
