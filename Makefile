IN_DIR 		?= ./
IN_EXTS 	?= .java
OUT_DIR 	?= ./_build/
OUT_EXT 	?= .class
RUN			?= Main

INS			:= $(foreach IN_EXT, $(IN_EXTS), $(wildcard $(IN_DIR)*$(IN_EXT)) )
INS_NODIR 	:= $(notdir $(INS))
OUTS_NODIR	:= $(basename $(INS_NODIR))
OUTS_NODIR	:= $(addsuffix $(OUT_EXT), $(OUTS_NODIR))
OUTS		:= $(addprefix $(OUT_DIR), $(OUTS_NODIR))

.PHONY: all clean install-deps-ubuntu mkdir run

all: mkdir $(OUTS) $(OUT_DIR)AppletHelloWorld.jar

$(OUT_DIR)AppletHelloWorld.jar: $(OUT_DIR)AppletHelloWorld$(OUT_EXT)
	jar cf "$@" "$<"

$(OUT_DIR)%$(OUT_EXT): %$(IN_EXTS)
	javac -cp '.:/usr/share/tomcat7/lib/servlet-api.jar' -d '$(OUT_DIR)' '$<'

clean:
	rm -rf $(OUT_DIR)

help:
	@echo 'Compile each `.java` file into a `.class`.'
	@echo ''
	@echo 'all                 - (default) Build all targets.'
	@echo 'clean               - Remove files built.'
	@echo 'install-deps-ubuntu - Install dependencies on Ubuntu.'
	@echo 'run                 - Run the default file.'
	@echo 'run RUN=Main        - Run the file named `Main.java`.'

install-deps-ubuntu:
	sudo apt-get install openjdk-7-jdk
	sudo apt-get install maven

mkdir:
	mkdir -p $(OUT_DIR)

run: all
	cd $(OUT_DIR) && java -ea -D'custom.property=value' $(RUN) arg0 arg1
