#Package name
LOGIN=xmazur09

#External libraries
LIB=lib/
#Source files
SRC=SRC/
#Data files
DATA=data/

.PHONY: all package run test clean zip

all: package run

package:
# /opt/apache-maven-3.9.1/bin/mvn
	mvn package

run: package
#java -cp target/pacman-1.0.jar ija.pacman.App
	mvn javafx:run

test:
#Not implemented yet

clean:
	find . -name "*.class" -type f -delete
	rm -rf $(LOGIN).zip build

zip:
	zip -r $(LOGIN).zip $(LOGIN) readme.txt -x *.class -D $(LOGIN)