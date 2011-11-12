# Makefile

LD_LIBRARY_PATH := ${LD_LIBRARY_PATH}:.

all: README.html

bxt/sundownJni/SundownJni.class: bxt/sundownJni/SundownJni.java
	javac -cp . bxt/sundownJni/SundownJni.java

bxt/sundownJni/MarkdownToHtmlFile.class: bxt/sundownJni/MarkdownToHtmlFile.java
	javac -cp . bxt/sundownJni/MarkdownToHtmlFile.java

bxt_sundownJni_SundownJni.h: bxt/sundownJni/SundownJni.class
	rm -f bxt_sundownJni_SundownJni.h
	javah -classpath . bxt.sundownJni.SundownJni

bxt_sundownJni_SundownJni_SundownJniOptions.h: bxt/sundownJni/SundownJni.class
	rm -f bxt_sundownJni_SundownJni_SundownJniOptions.h
	javah -classpath . bxt.sundownJni.SundownJni

libSundownJni.so: libSundownJni.c sundown/libsundown.so bxt_sundownJni_SundownJni.h bxt_sundownJni_SundownJni_SundownJniOptions.h
	gcc -fPIC -shared sundown/libsundown.so libSundownJni.c -o libSundownJni.so -I sundown/src -I sundown/html

sundown/libsundown.so:
	cd sundown && make

README.html: bxt/sundownJni/MarkdownToHtmlFile.class libSundownJni.so README.md
	env LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:. java -cp . bxt.sundownJni.MarkdownToHtmlFile README.md > README.html


clean:
	rm -f bxt/sundownJni/*.class
	rm -f libSundownJni.so
	rm -f bxt_sundownJni*.h
	rm -f README.html
	cd sundown && make clean

