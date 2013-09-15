package battlechap;

import battlechap.*;
public class BattleGrid{
    //Matrix to display board and hit count\\
    private Matrix _buffer;
    private Matrix _hidden;
    private int _casualties;
    
    //Ships\\
    private String _destroyer;
    private String _sub;
    private String _cruiser;
    private String _battleship;
    private String _carrier;

    //To help easy conversion from points to rows and columns\\
    public static final String COLUMNS = "ABCDEFGHIJ";
    public static final String ROWS = "12345678910";
    
    public BattleGrid(){
	_buffer = new Matrix(10);
	clear(_buffer);
	_hidden = new Matrix(10);
	clear(_hidden);

	_casualties = 0;

	_destroyer = "";
	_sub = "";
	_cruiser = "";
	_battleship = "";
	_carrier = "";
    }

    //Get, in the form of points instead of row\columns\\
    public Object get(String point){
	int col = COLUMNS.indexOf(point.substring(0,1));
	int row = ROWS.indexOf(point.substring(1));
	return _buffer.get(row, col);
    }

    //Set in the form of points\\
    public void set(String point, Object newVal){

	//For modifying the matrices\\
	int col = COLUMNS.indexOf(point.substring(0,1));
	int row = ROWS.indexOf(point.substring(1));

	//Adds X's and M's for hits and misses to the hidden board\\
	if(get(point).equals("~")){
	    _hidden.set(row, col, "M");
	}
	else{
	    _hidden.set(row, col, "X");
	}
	
	_buffer.set(row, col, newVal);

    }
    
    //Sets all empty spaces on the display buffer to ~\\
    public void clear(Matrix L){
	for(int r = 0; r < L.size(); r++){
	    for(int c = 0; c < L.size(); c++){
		L.set(r, c, "~");
	    }
	}
    }

    //Adds to buffers together.  Useful for in several situations\\
    public void addBuffers(Matrix target, Matrix source){
	for(int r = 0; r < source.size(); r ++){
	    for(int c = 0; c < source.size(); c++){
		if(!source.isEmpty(r, c) && !source.get(r, c).equals("~"))
		    target.set(r, c, source.get(r, c));
	    }
	}
    }

    //Checks if position in _buffer is empty\\
    public boolean empty(String point){
	int col = COLUMNS.indexOf(point.substring(0,1));
	int row = ROWS.indexOf(point.substring(1));
	return _buffer.isEmpty(row, col);
    }

    //Creates ship if there are no collisions, returns false if there are collisions\\
    public boolean createShip(int size, String point, String direction){
	boolean foo = true;
	boolean too = _sub.length() == 0;

	Matrix temp = new Matrix(10);
	addBuffers(temp, _buffer);

	int col = COLUMNS.indexOf(point.substring(0,1));
	int row = ROWS.indexOf(point.substring(1));

	String points = "";
	String val = "*";

	//Modifies the character to be added to the board based on the ship\\
	if(size == 2)
	    val = "D";
	else if(size == 3 && too)
	    val = "S";
	
	else if(size == 3)
	    val = "I";
	
	else if(size == 4)
	    val = "B";
	
	else
	    val = "C";
	

	/*Tries to place the ship starting at a certain point along a certain direction in a temporary buffer. 
	 If this fails, then foo is set to false, the display buffer is not modified, and false is returned.*/

	if(direction.equals("s")){
	    try{
		for(int r = row; r < row + size; r++){
		    if(temp.isEmpty(r, col)){
			temp.set(r, col, val);
			points += COLUMNS.substring(col, col+1) + ROWS.substring(r, r+1);
		    }

		    else{
			foo = false;
			break;
		    }
		}
	    } catch(IndexOutOfBoundsException e){
		foo = false;
	    }
	}
	
	else if(direction.equals("n")){
	    try{    
		for(int r = row; r > row - size; r--){
		    if(temp.isEmpty(r, col)){
			temp.set(r, col, val);
			points += COLUMNS.substring(col, col+1) + ROWS.substring(r, r+1);
		    }

		    else{
			foo = false;
			break;
		    }
		}
	    } catch(IndexOutOfBoundsException e){
		foo = false;
	    }
	}
	
	else if(direction.equals("e")){
	    try{    
		for(int c = col; c < col + size; c++){
		    if(temp.isEmpty(row, c)){
			temp.set(row, c, val);
			points += COLUMNS.substring(c, c+1) + ROWS.substring(row, row+1);
		    }
		    
		    else{
			foo = false;
			break;
		    }
		}
	    } catch(IndexOutOfBoundsException e){
		foo = false;
	    }
	}

	else if(direction.equals("w")){
	    try{    
		for(int c = col; c > col - size; c--){
		    if(temp.isEmpty(row, c)){
			temp.set(row, c, val);
			points += COLUMNS.substring(c, c+1) + ROWS.substring(row, row+1);
		    }
		    
		    else{
			foo = false;
			break;
		    }
		}
	    } catch(IndexOutOfBoundsException e){
		foo = false;
	    }
	}

	else{
	    foo = false;
	}

	//Determines where the String containing the points of a ship will go\\
	if(size == 2)
	    _destroyer = points;
	else if(size == 3 && too)
	    _sub = points;
	
	else if(size == 3)
	    _cruiser = points;
	
	else if(size == 4)
	    _battleship = points;
	
	else
	    _carrier = points;
	

	//If no overlaps, set temp buffer to current buffer\\
	if (foo){
	    addBuffers(_buffer, temp);
	}

	return foo;
    }
    
    //Takes input from the shot\\
    public void reportDead(String ship, String name){
	boolean foo = true;
	
	for(int i = 0; i < ship.length(); i += 2){
	    try{
		if(ROWS.indexOf(ship.substring(i+1, i+3)) > -1){
		    if(!(get(ship.substring(i, i+3)).equals("~"))){
			foo = false;
			break;
		    }
		}
		else{
		    if(!(get(ship.substring(i, i+2)).equals("~"))){
			foo = false;
			break;
		    }
		}
	    } catch(StringIndexOutOfBoundsException e){
		if(!(get(ship.substring(i, i+2)).equals("~"))){
		    foo = false;
		    break;
		}
	    }
	}
	
	if(foo){
	    //***Animation here as well***\\
	    System.out.println("You took down my " + name + "!\n");
	}
	
    }

    //Reports which ship has been most recently sunk\\
    public int reportDead(String point){
	int retval = 0;

	if(_destroyer.indexOf(point) > 0){
	    reportDead(_destroyer, "DESTROYER");
	    retval = 2;
	}

	else if(_sub.indexOf(point) > 0){
	    reportDead(_sub, "SUBMARINE");
	    retval = 3;
	}

	else if(_cruiser.indexOf(point) > 0){
	    reportDead(_cruiser, "INTERGALACTIC-CRUISER");
	    retval = 3;
	}
	
	else if(_battleship.indexOf(point) > 0){
	    reportDead(_battleship, "BATTLESHIP");
	    retval = 4;
	}

	else{
	    reportDead(_carrier, "CARRIER");
	    retval = 5;
	}

	return retval;

    }
       
    //Keeps track of casualties on board\\
    public void death(){
	_casualties++;
    }

    public int deathCount(){
	return _casualties;
    }

    public String hidden(){
	String retval = "   A  B  C  D  E  F  G  H  I  J\n" +  _hidden.toString();
	return retval;
    }
    
    public String toString(){
	String retval = "   A  B  C  D  E  F  G  H  I  J\n" +  _buffer.toString();
	return retval;
    }
    
    public static void main(String[] args){
    }
}
