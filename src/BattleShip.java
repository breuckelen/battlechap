// "I Can Do Bad All by Myself" - Tyler Perry
// Benjamin Attal
// pd 9
// PROJECT 2
// 2013-01-21

package BattleChap;

import cs1.Keyboard;
import BattleChap.*;

//Main BattleShip class\\
public class BattleShip{
    private BattleGrid _grid1, _grid2;
    private Human _player1, _player2;
    private Computer _comp1, _comp2;

    //Acount for user input types of players\\
    public BattleShip(){
	_grid1 = new BattleGrid();
	_grid2 = new BattleGrid();
	
	_player1 = new Human();
	_player2 = new Human();

	_comp1 = new Computer();
	_comp2 = new Computer();


    }

    //Shoot function for both humans and computars\\
    public boolean shoot(BattleGrid grid, String point){
	boolean foo = !(grid.get(point).equals("~"));

	//
	if (foo){
	    System.out.println("\nSuch a kind a shot!!");
	    grid.set(point, "~");
	    grid.reportDead(point); //Reports which ship has been sunk if any\\
	    grid.death();
	}
	else{
	    System.out.println("Dis eye docta, you need a better glasses.");
	    grid.set(point, "~");
	}

	return foo;
    }

    //Ties Player to BattleGrid function for creating individual ships\\
    public void create(Player player, BattleGrid grid, int size, String name){
	String place = "";
	String direction = "";
	boolean foo = false;
	
	System.out.println(grid);

	while(!foo){
	    foo = grid.createShip(size, player.placeShip(name),  player.readDir(name));
	}
    }

    //Repeats create for each ship\\
    public void populate(Player player, BattleGrid grid){
	create(player, grid, 2, "DESTROYER");
	create(player, grid, 3, "SUBMARINE");
	create(player, grid, 3, "INTER-GALACTIC CRUISER");
	create(player, grid, 4, "BATTLESHIP");
	create(player, grid, 5, "CARRIER");
    }

    //Incorporates setup, including password for players
    public void setup(Player player, BattleGrid grid){
	try{
	    player.makeCode();
	} catch(NullPointerException e){
	    System.out.println("Computer is calibrating...");
	}
	populate(player, grid);
    }

    //Options mid game for human players\\
    public void humanOptions(Human player, BattleGrid grid1, BattleGrid grid2){
	System.out.println(grid2.hidden());
	while(!player.readCode()){
	    System.out.println("Sorry, not the right password.");
	}
	
	int a = player.giveOptions();
	
	while(!(a == 1)){
	    if(a == 2)
		System.out.println("\n" + grid1);
	    a = player.giveOptions();
	}
	    
	shoot(grid2, player.shoot());
    }

    //Allows players to choose mode in the beginning\\
    public int mode(){
	System.out.println("Which gamemode will be played?(1-human v. human, 2-human v. comp, 3-comp v comp)");
	int i = Keyboard.readInt();
	while(i > 3 || i < 1)
	    i = Keyboard.readInt();
	return i;
    }

    //Main play function, takes into account mode choice\\
    public void play(){
	int mode = mode();
	if(mode == 1){
	    setup(_player1, _grid1);
	    setup(_player2, _grid2);
	}
	else if(mode == 2){
	    setup(_player1, _grid1);
	    setup(_comp1, _grid2);
	}
	else{
	    setup(_comp1, _grid1);
	    setup(_comp2, _grid2);
	}
	
	while(!(_grid1.deathCount()==17 || _grid2.deathCount()==17)){
	    
	    if (mode == 1){
		System.out.println("\nPlayer 1's turn, shoot!\n");
		humanOptions(_player1, _grid1, _grid2);
		
		System.out.println("\nPlayer 2's turn, shoot!\n");
		humanOptions(_player2, _grid2, _grid1);
	    }
	    else if (mode == 2){
		System.out.println("\nPlayer's turn, shoot!\n");
		humanOptions(_player1, _grid1, _grid2);
		
		String temp1 =  _comp1.shoot();
		_comp1.updateGuess(shoot(_grid1, temp1), 0);
		System.out.println(_grid1.hidden());
	    }
	    else{
		String temp1 = _comp1.shoot();
		_comp1.updateGuess(shoot(_grid2, temp1), 0);
		System.out.println(_grid2.hidden());
		
		String temp2 = _comp2.shoot();
		_comp2.updateGuess(shoot(_grid1, temp2), 0);
		System.out.println(_grid1.hidden());
	    }
	}

	if(_grid1.deathCount() == 17)
	    System.out.println("Player 2 Wins!!!");
	else if (_grid2.deathCount() == 17)
	    System.out.println("Player 1 Wins!!!");
	else
	    System.out.println("Everybody Wins!!!");
    }

    public static void main(String[] args){
	BattleShip game = new BattleShip();
	game.play();
    }
}
