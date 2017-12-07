# ~~wavegame~~ Player Known's Battle Lands
Group A1's ~~Wavegame~~ Player Known's Battle Lands project.

## Links:

- [YouTube tutorial](https://www.youtube.com/watch?v=1gir2R7G9ws)
- [Style guide](https://google.github.io/styleguide/javaguide.html)

## How to setup and play:

1. Unzip the file.

2. Open Eclipse. Press File -> Import -> General -> Exisiting Projects into Workspace -> Next

3. Next to "Select root directory", click "Browse..." and then select the folder that was contained in Wave\_Game.zip (the folder is called "Wave Game").

4. Click "Finish" at the bottom.

5. Now that the project is imported, double click the class called "Game" to open that file.

6. This file contains the main method, so you can now click the green arrow at the top toolbar to start the game.

7. Information on how to play the game is located under the "Help" option in the game menu.

## How to use the server:

1. Go into the code and in `Leaderboard.java`, change the IP and Address to be those of your server (if you are hosting on your own computer, you can make the IP `"localhost"` and the port whatever you want).
2. In the terminal, if you have the jar of the server, you can run the jar. Otherwise, in Eclipse, add a new launch configuration for the server. Under the Arguments tab for the launch configuration, set the port you want to run, i.e. 25324. Ports must be within a certain range of numbers, but I'm not your Mommy, so use Google.
3. In the game, you can connect to that server using whatever IP/Port combo you used for the server.

## Known Issues (as of 12-07-2017)

- Space bar advances level (left in as dev feature).
- Going to a certain boss in Waves mode and then going back to the menu and going back into Waves mode puts you at the wrong level.
- There is no endgame in Waves mode.
- Certain multiplayer features need smoothing out.
