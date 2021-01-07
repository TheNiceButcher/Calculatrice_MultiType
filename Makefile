FILES = Main.java Operation.java REPL.java Type.java
CLASS = *.class
all: $(FILES)
	javac $(FILES)
doc: $(FILES)
	javadoc -d doc $(FILES)
clean:
	rm -r $(CLASS)
