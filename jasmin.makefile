IN_EXT := .j
OUT_EXT := .class
RUN ?= Main

INS := $(wildcard *$(IN_EXT))
OUTS := $(addsuffix $(OUT_EXT), $(basename $(INS)))

.PHONY: all clean run install-deps-ubuntu

all: $(OUTS)

%$(OUT_EXT): %$(IN_EXT)
	jasmin '$<'

clean:
	rm -rf *$(OUT_EXT)

run: all
	java $(RUN)
