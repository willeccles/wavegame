MAINCLASSES=$(subst .java,.class,$(wildcard mainGame/*.java))
AUDIO=$(subst .java,.class,$(wildcard mainGame/audio/*.java))
NET=$(subst .java,.class,$(wildcard mainGame/net/*.java))
MAINDIR=mainGame
NETDIR=$(MAINDIR)/net
AUDIODIR=$(MAINDIR)/audio
IMGDIR=$(MAINDIR)/images
SOUNDDIR=$(MAINDIR)/sounds
SERVERCLASSES=$(subst .java,.class,$(wildcard server/*.java))

game: $(MAINCLASSES) $(AUDIO) $(NET)

server: $(SERVERCLASSES)

%.class: %.java
	javac $<

all: game server

jar: all
	@echo Class-Path: . >> gamemanifest.mf
	@echo Main-Class: mainGame.Game >> gamemanifest.mf
	@echo Class-Path: . >> servermanifest.mf
	@echo Main-Class: server.Server >> servermanifest.mf
	jar cvmf gamemanifest.mf gameclient.jar $(MAINCLASSES) $(AUDIO) $(NET) $(IMGDIR) $(SOUNDDIR)
	jar cvmf servermanifest.mf gameserver.jar $(SERVERCLASSES)
	@rm *.mf

clean:
	rm -f mainGame/*.class
	rm -f mainGame/*/*.class
	rm -f server/*.class
