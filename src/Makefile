MAINCLASSES=$(subst .java,.class,$(wildcard mainGame/*.java))
AUDIO=$(subst .java,.class,$(wildcard mainGame/audio/*.java))
NET=$(subst .java,.class,$(widlcard mainGame/net/*.java))
MAINDIR=mainGame
NETDIR=$(MAINDIR)/net
AUDIODIR=$(MAINDIR)/audio
SERVERCLASSES=$(subst .java,.class,$(wildcard server/*.java))

game: $(MAINCLASSES) $(AUDIO) $(NET)

server: $(SERVERCLASSES)

%.class: %.java
	javac $<

all:
	make maingame
	make gameserver

clean:
	rm -f mainGame/*.class
	rm -f mainGame/*/*.class
	rm -f server/*.class
