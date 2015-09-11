.POSIX:

CCC ?= gcj
IN_EXT ?= .java
OUT_EXT ?= .out
RUN ?= Main
TMP_EXT ?= .o

INS := $(wildcard *$(IN_EXT))
OUTS := $(INS:$(IN_EXT)=$(TMP_EXT))
RUN_BASENAME := $(RUN)$(OUT_EXT)

.PHONY: clean run

$(RUN_BASENAME): $(OUTS)
	$(CCC) -ggdb3 --main='$(RUN)' -o '$@' $+

%$(TMP_EXT): %$(IN_EXT)
	$(CCC) -c -ggdb3 '$<' -o '$@'

clean:
	rm -f *'$(TMP_EXT)' '$(RUN_BASENAME)'

run: $(RUN_BASENAME)
	./'$(RUN_BASENAME)'
