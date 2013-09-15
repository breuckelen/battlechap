package battlechap;

//Abstract class player\\
public class Player{

    private String _code;   
    
    //There are many extra functions in the Human and Computer classes that are not here\ \
    public Player(){
	_code = "";
    }

    public String getCode(){
	return _code;
    }

    //Used for privacy of human players\\
    public void setCode(String foo){
	_code = foo;
    }

    public void makeCode(){
	int trash;
    }

    public boolean readCode(){
	return true;
    }

    public String placeShip(String name){
	return name;
    }

    public String shoot(){
	return "";
    }

    public String readDir(String name){
	return name;
    }
}
