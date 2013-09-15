package battlechap;

import battlechap.*;
//Computer class, this one was really annoying to make, and it doesn't work fully\\
public class Computer extends Player{
    public static final String COLUMNS = "ABCDEFGHIJ";
    public static final String ROWS = "12345678910";

    //Board Qualities\\
    private int[] _max = {0,2,3,3,4,5};
    private int _size; //if input >= size then decrement

    //Data for choosing\\
    private int _hits;
    private int _dir;
    private int _consec;

    private int _posr;
    private int _posc;
    private int _startr;
    private int _startc;

    //Others\\
    private String _guess;
    private String _done;

    public Computer(){
	super();
	_guess = placeShip("hehe");
	_done = "";

	_size = _max.length - 1;
	_hits = 0;
	_dir = 0;
	_consec = 0;

	_posr = ROWS.indexOf(_guess.substring(1));
	_posc = COLUMNS.indexOf(_guess.substring(0,1));

	_startr = 0;
	_startc = 0;
    }

    //Outputs random position on the board\\
    public String placeShip(String name){
	int r = (int) (Math.random() * 10 + 1);
	int ran = (int) (Math.random() * 10);
	String c = COLUMNS.substring(ran, ran + 1);

	String ret = c + "" + r;
	return ret;
    }

    //Outputs the Guess which is optimized each round\\
    public String shoot(){
	System.out.println("\nComputer guesses " + _guess);
	return _guess;
    }

    //Kind of sloppy algorithm for updating the guess each round, works most of the time...\\
    public void updateGuess(boolean foo, int ship){
	String point = "";

	if(foo)
	    _hits++;
	
	if(_hits > 0){
    
	    if (foo){
		_consec = 0;

		if(_hits == 1){
		    _startr = _posr;
		    _startc = _posc;
		}
		
		boolean temp = updatePosition(foo);
		int counter = 0;

		while(!temp){
		    counter++;
		    if(counter > 4)
			break;
		    temp = updatePosition(foo);
		}
		
		//Function to add and correct direction if necessary\\
	    }

	    else{
		_consec++;

		if(_hits == 1){
		    _dir++;
		    _posr = _startr;
		    _posc = _startc;
		    
		    boolean temp = updatePosition(true);
		    int counter = 0;

		    while(!temp){
			counter++;
			if(counter > 4)
			    break;
			temp = updatePosition(true);
		    }
		    if(_consec >= 4){
			_consec = 0;
			_hits = 0;
			_dir = 0;
			_posr = (int) (Math.random() * 10);
			_posc = (int) (Math.random() * 10);
		    }
		    //Function to correct direction if necessary\\
		}
		
		else if(_hits > 1){
		    updatePosition(foo);
		    if(_consec > 2){
			_consec = 0;
			_hits = 0;
			_dir = 0;
			_posr = (int) (Math.random() * 10);
			_posc = (int) (Math.random() * 10);
		    }
		    //Function to correct direction if necessary\\
		    //Check to make sure hasn't already been guessed\\
		}
	    }
	    

	    //This part is a quick fix if what was supposed to work fails\\
	    if(_posc == 10 || _posc == -1){
	        _posc = (int) (Math.random() * 10);
		_hits = 0;
		_dir = 0;
		_consec = 0;
	    }
	    if(_posr == 10 || _posr == -1){
		_posr = (int) (Math.random() * 10);
		_hits = 0;
		_dir = 0;
		_consec = 0;
	    }
	    
	    int tempr = 0;
	    
	    if(_posr == 9){ //I did not get to fixing all of the bugs with the AI, but some
		tempr = 11; //very strange things popped up that I don't I can fix without remaking\\
	    }               //this whole class, which I do not really want to do\\
	    else{
		tempr = _posr + 1;
	    }

	    point = COLUMNS.substring(_posc, _posc+1) + "" + ROWS.substring(_posr, tempr);
	    
	    boolean tee = _done.indexOf(point) > -1;

	    int counter = 0;
	    if(tee && _hits == 1){
		while(tee){
		    counter ++;

		    if(counter > 4)
			break;

		    _dir++;
		    _posc = _startc;
		    _posr = _startr;
		    updatePosition(true);
		    
		    if(_posr == 9){
			tempr = 11; 
		    }
		    else{
			tempr = _posr + 1;
		    }
		    
		    point = COLUMNS.substring(_posc, _posc+1) + "" + ROWS.substring(_posr, tempr);
		    tee = _done.indexOf(point) > -1;
		}
	    }
	}

	else{
	    point = placeShip("");
	    _hits = 0;
	    _dir = 0;
	}
	
	check(point);
	
    }

    /*Updates the position based on certain criteria, namely whether a position has already
     been covered or if the position is beyond a certain bound*/
    public boolean updatePosition(boolean foo){
	boolean ret = false;
	boolean tee = false;
	String point = "";
	
	if(_dir == 4)
	    _dir = 0;

	if(foo){

	    if(_dir == 0){
		if(_posr < 9){
		    int tempr = _posr+2;
		    if(_posr == 8)
			tempr = 11;
		    point = COLUMNS.substring(_posc, _posc+1) + "" + ROWS.substring(_posr+1, tempr);
		    tee = _done.indexOf(point) > -1;
		}

		if(_posr < 9 && !tee){
		    _posr++;
		    ret = true;
		}
		else{
		    if(_hits == 1){
			_dir++;
			_posr = _startr;
		    }
		    else{
			_dir = 2;
			_posr = _startr - 1;
			ret = true;
		    }
		}
	    }
	    
	    else if(_dir == 1){
		if(_posc > 0){
		    point = COLUMNS.substring(_posc-1, _posc) + "" + ROWS.substring(_posr, _posr+1);
		    tee = _done.indexOf(point) > -1;
		}
		
		if(_posc > 0 && !tee){
		    _posc--;
		    ret = true;
		}
		else{
		    if(_hits == 1){
			_dir++;
			_posc = _startc;
		    }
		    else{
			_dir = 3;
			_posc = _startc + 1;
			ret = true;
		    }
		}
	    }
	    
	    else if(_dir == 2){
		if(_posr > 0){
		    point = COLUMNS.substring(_posc, _posc+1) + "" + ROWS.substring(_posr-1, _posr);
		    tee = _done.indexOf(point) > -1;
		}
		
		if(_posr > 0){
		    _posr--;
		    ret = true;
		}
		else{
		    if(_hits == 1){
			_dir++;
			_posr = _startr;
		    }
		    else{
			_dir = 0;
			_posr = _startr + 1;
			ret = true;
		    }
		}
	    }
	    
	    else if(_dir == 3){
		if(_posc < 9){
		    point = COLUMNS.substring(_posc+1, _posc+2) + "" + ROWS.substring(_posr, _posr+1);
		    tee = _done.indexOf(point) > -1;
		}
		if(_posc < 9){
		    _posc++;
		    ret = true;
		}
		else{
		    if(_hits == 1){
			_dir = 0;
			_posc = _startc;
		    }
		    else{
			_dir = 1;
			_posc = _startc - 1;
			ret = true;
		    }
		}
	    }
	}
	    
	else{

	    ret = true;

	    if(_dir == 0){
		if(_startr > 0){
		    _dir = 2;
		    _posr = _startr - 1;
		}
		else{
		    _posr = _startr;
		    _posc = _startc;
		    _hits = 0;
		    _dir = 0;
		}
	    }
	    
	    else if(_dir == 1){
		if(_startc < 9){
		    _dir = 3;
		    _posc = _startc + 1;
		}
		else{
		    _posr = _startr;
		    _posc = _startc;
		    _hits = 0;
		    _dir = 0;
		}
	    }
	    
	    else if(_dir == 2){
		if(_startr < 9){
		    _dir = 0;
		    _posr = _startr + 1;
		}
		else{
		    _posr = _startr;
		    _posc = _startc;
		    _hits = 0;
		    _dir = 0;
		}
	    }
	    
	    else if(_dir == 3){
		if(_startc > 0){
		    _dir = 1;
		    _posc = _startc - 1;		    
		}
		else{
		    _posr = _startr;
		    _posc = _startc;
		    _hits = 0;
		    _dir = 0;
		}
	    }
	}

	return ret;
    }

    
    //Outputs a random direction\\
    public String readDir(String name){
	int ran = (int) (Math.random() * 4);
	String ret = "";

	if(ran == 0)
	    ret = "s";
	else if(ran == 1)
	    ret = "w";
	else if(ran == 2)
	    ret = "n";
	else
	    ret = "e";

	return ret;
    }

    //Checks to make sure a position has not already been covered\\
    public void check(String point){
	String temp = point;

	int counter = 0;
	while(_done.indexOf(temp) > -1){ 
	    counter++;
	    if (counter > 1000)
		break;
	    temp = placeShip("");
	}
	
	_done += temp;
	_guess = temp;

	_posr = ROWS.indexOf(_guess.substring(1));
	_posc = COLUMNS.indexOf(_guess.substring(0,1));

    }

    public static void main(String[] args){
	Computer ben = new Computer();
	System.out.println(ben.shoot());
	ben.updateGuess(true, 0);
	System.out.println(ben.shoot());
	ben.updateGuess(false, 0);
	System.out.println(ben.shoot());
	ben.updateGuess(false, 0);
	System.out.println(ben.shoot());
	ben.updateGuess(true, 0);
	System.out.println(ben.shoot());
    }
}

