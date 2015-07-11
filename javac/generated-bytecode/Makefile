.POSIX:

IN_EXT ?= .java
TMP_EXT ?= .class
OUT_EXT ?= .byte
RUN ?= ShortTest

INS := $(wildcard *$(IN_EXT))
OUTS_NOEXT := $(basename $(INS))
OUTS := $(addsuffix $(OUT_EXT), $(OUTS_NOEXT))
TMPS := $(addsuffix $(TMP_EXT), $(OUTS_NOEXT))

.PRECIOUS: $(TMPS)
.PHONY: all clean run

all: $(OUTS)

%$(OUT_EXT): %$(TMP_EXT)
	javap -c -constants -private -verbose '$<' > '$@'

%$(TMP_EXT): %$(IN_EXT)
	javac -g '$<'

clean:
	rm -f *$(TMP_EXT) *$(OUT_EXT)

run: all
	java -ea '$(RUN)'
