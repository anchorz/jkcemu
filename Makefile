all:
	find src/jkcemu/ -type f -name "*.class" -exec rm {} \;
	javac -cp src/jsoup-1.10.3.jar:src src/jkcemu/Main.java

run:
	java -classpath "src/jsoup-1.10.3.jar:src" jkcemu.Main Z1013

$(OUT):
	mkdir $(OUT)

find_and_replace:
	find src -type f -name "*.java" -exec /usr/bin/perl -i -p -e 's/import.*java\.lang\.\*;.*\n//g' {} \;
