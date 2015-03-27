DIR := doc

.PHONY: all clean view
#TODO dry up
all:
	javadoc -d '$(DIR)' -source 1.7 *.java package0

clean:
	rm -rf '$(DIR)'

view: all
	xdg-open '$(DIR)/index.html'
