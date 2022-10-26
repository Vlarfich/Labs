# makefile z4_1

.SUFFIXES: .class .java

PACK=z_4
TARG=z
JC=javac -g
JM=java -ea -jar
JR=jar -cfe

OBJ=$(PACK)\vect.class \
    $(PACK)\test.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: 	$(OBJ)
	$(JR) $(TARG).jar $(PACK).test $(OBJ)

clean:
	for %%f in ($(OBJ)) do del %%f
	del $(TARG).jar

$(PACK)\vect.class: $(PACK)\vect.java makefile

#$(PACK)\test.class: $(PACK)\test.java makefile

# eof makefile z4_1
