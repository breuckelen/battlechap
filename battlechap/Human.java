package battlechap;

import cs1.Keyboard;
import battlechap.*;

/*Most functions are from the Player class, the only difference is that they produce
output by using the Keyboard functions*/
public class Human extends Player{
    public Human(){
	super();
    }

    public void makeCode(){
	System.out.print("\nInput passcode here: ");
	setCode(Keyboard.readString());
	System.out.println();
    }

    public boolean readCode(){
	System.out.print("Passcode: ");
	return Keyboard.readString().equals(getCode());
    }

    public String placeShip(String name){
	System.out.print("\nPlace your " + name + " here: ");
	String ret = Keyboard.readString();
	return ret;
    }

    public String shoot(){
	System.out.print("\nShoot here: ");
	String ret = Keyboard.readString();
	return ret;
    }

    public String readDir(String name){
	System.out.print("Input direction for " + name + "(s, e, n, w): ");
	String ret = Keyboard.readString();
	return ret;
    }

    //The only supplementary function that allows the player to choose what to do mid-game\\
    public int giveOptions(){
	System.out.println("What would you like to do? (1-shoot, 2-look board, 3-count to 1 million, 4-take a stroll, 5-cry)");
	int ret = 0;
	int choice = Keyboard.readInt();
	
	if(choice == 1){
	    ret = 1;
	}

	else if(choice == 2){
	    ret = 2;
	}

	else if(choice == 3){
	    for(int i = 0; i < 1000000; i++){
		ret = 0;
	    }
	    System.out.println("DONE!\n");
	}

	else if(choice == 4){
	    System.out.println("I'll be here when you get back, baby\n");
	}

	else{
	    System.out.println("You're not crying, your eyes are just sweaty\n");
	}
	
	return ret;
    }
}
