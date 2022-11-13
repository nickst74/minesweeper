# Minesweeper

A Minesweeper game implimentation using Java and the JavaFX.

The number of Rows and Columns of the grid can be defined in the Main Menu, along with the number of Mines that will be present in the game. 

![Main Menu](./preview_images/Screenshot%20from%202022-11-13%2018-05-51.png)

The game start after the corresponding button is pressed, and it is possible to return to the main menu at any time by pressing the "Back" button at the upper left corner of the window.

In the Game Screen, except from the main grid of tiles, there is a timer at the upper right corner of the window that shows the seconds that have elapsed from the start of the game session. Right at its left, there is a counter of the remaining Mines in the game. Every time a tile is marked with a flag, the counter is reduced by one, <ins>regardless of wether there exists a mine in that tile or not</ins>, and it cannot contain negative values, which means that there can be no more flags in the game than mines.

Once a game session is finished, a button with a message will appear at the upper center of the window that can be pressed to reset the game and start over immediatly.

![Game Screen](./preview_images/Screenshot%20from%202022-11-13%2018-06-26.png)

The window is fully resizable with the components' size changing accordingly, with the grid being fitted in a scrollpane, so it can be easily accessible regardless screen sizes.

The game was build with JDK-11 and tested with Java 11, so it should not work with previous versions.

For further information regarding the Minesweeper game rules, they can be found in the following [link](https://minesweepergame.com/strategy/how-to-play-minesweeper.php)

## <ins>**GL and HF !!!**</ins>