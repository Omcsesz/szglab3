# Szoftver labor 3
A program a Conway-féle életjátékot játssza. A játék nem igényel játékost, a „játszma” alakulását a kezdeti feltételek szabják meg. A játékot John Conway találta ki 1970-ben. A következő szabályok szerint játsszák: a játékteret egy n × n –es négyzetrács alkotja, a mezőket celláknak nevezik, a bennük lévő pöttyöket sejteknek. Minden sejtnek kétféle állapota lehet: élő, vagy halott. A szomszédok számától függően a következőképpen alakul a cella sorsa:

*	Ha 3-nál több szomszédja van, akkor túlnépesedés miatt elpusztul.
*	Ha kettőnél kevesebb, akkor elszigetelődés miatt pusztul el.
*	Ha pontosan kettő, vagy három szomszédja van, túléli a kört.
*	Új sejt születik minden olyan cellában, melynek környezetében pontosan három sejt található.

#Software laboratory 3
The program simulates Conway's Game of life (https://en.wikipedia.org/wiki/Conway's_Game_of_Life). The  "game" needs no player (zero-player game), the outcome is determined by the initial configuration. The game was invented by John Conway in 1970. The rules are as follows: the game is played on an n-by-n grid, each of which is in one of two possible states, alive or dead, or "populated" or "unpopulated". Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

  *  Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
  *  Any live cell with two or three live neighbours lives on to the next generation.
  *  Any live cell with more than three live neighbours dies, as if by overpopulation.
  *  Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
