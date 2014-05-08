package com.maze;

import java.util.Stack;

public class Game {
	
	private Maze maze;
	private Corridor start;
	private Corridor flag;
	private Corridor currRoboCorridor;
	
	private int moveCount=0;
	private int turnCount=0;
	
	private Stack<Corridor>gameStack;
	
	public Game(){
		this.gameStack=new Stack<Corridor>();
		this.maze=new Maze();
		this.start=this.maze.getStartCorridor();
		this.currRoboCorridor=this.maze.getStartCorridor();
	}
	
	public static void main(String[] args){
		Game theGame=new Game();
		theGame.playGame();
	}
	
	
	public void playGame(){
		int count=1;
		boolean keepGoing=true;
		//while(this.currRoboCorridor.getTheChar()!='!'){
		while(count<200 && keepGoing){
			char currDirection=this.currRoboCorridor.getTheChar();
			MoveResult move=null;
			Corridor backTrackMove=this.currRoboCorridor.clone();
			
			switch(currDirection){
			    case '^':{
			    	//If we can go in the same direction then do it
			    	if(this.maze.canGoNorth()){
			    		
			    		//First we have to copy the corridor but in the opposite direction for pushing onto the stack
			    		backTrackMove.setTheChar('v');
			    		Corridor westCor=null;
			    		Corridor eastCor=null;
			    		
			    		//We also should also copy the western corridor to push onto the stack if we can go west
			    		if(this.maze.canGoWest()){
			    			westCor=this.currRoboCorridor.clone();
			    			westCor.setTheChar('<');
			    		}
			    		//We also should copy the eastern corridor to push onto the stack if we can go east
			    		if(this.maze.canGoEast()){
			    			eastCor=this.currRoboCorridor.clone();
			    			eastCor.setTheChar('>');	
			    		}
			    		
			    		//Now we move North
			    		move=this.goNorth();
			    		//The move is successful
			    		if(move.isResult()){	
			    			//east is the last direction we want to try if we are facing north
			    			if(null!=eastCor){			    				
			    				this.gameStack.push(eastCor);
			    			}
			    			if(null!=westCor){
			    				this.gameStack.push(westCor);
			    			}	
			    			this.gameStack.push(backTrackMove);
			    		}
				    	else{
				    		System.out.println("ERROR: "+move.getMessage());
				    		keepGoing=false;
				    	}
			    	}
			    	//We can't go north but we can go west, therefore turn west
			    	else if(this.maze.canGoWest()){
			    		move=this.turnWest();
			    		if(!move.isResult()){
			    			System.out.println("Error: "+move.getMessage());
			    			keepGoing=false;
			    		}
			    	}
			    	//We can't go north nor can we go west, therefore try turning south and then east
			    	else if(this.maze.canGoEast()){
			    		move=this.turnWest();
			    		if(move.isResult()){
			    			System.out.println("\n");
			    			System.out.println("Count: "+count);			    			
			    			this.maze.printMaze();
			    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
			    			++count;
			    			move=this.turnSouth();
			    			if(move.isResult()){	
				    			System.out.println("\n");
				    			System.out.println("Count: "+count);			    			
				    			this.maze.printMaze();
				    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
				    			++count;
			    				move=this.turnEast();
			    				if(!move.isResult()){
			    					System.out.println("ERROR: "+move.getMessage());
			    					keepGoing=false;
			    				}
			    			}
					    	else{
					    		System.out.println("ERROR: "+move.getMessage());
					    		keepGoing=false;
					    	}			    			
			    		}
			    		else{
			    			System.out.println("Error: "+move.getMessage());
			    			keepGoing=false;
			    		}
			    	}
			    	//We have to pop until the direction is no longer south
			    	else{
			    		if(this.turnAround(count)){
			    			count+=2;
			    			count+=this.backTrackMoves(count);				    			
			    		}		    		
			    		else{
			    			keepGoing=false;
			    		}
			    	}		    	
			    	break;
			    }
			    case '<':{
			    	if(this.maze.canGoWest()){
			    		
			    		//Make a copy of the current corridor but with the opposite direction to push onto the stack
			    		backTrackMove.setTheChar('>');
			    		Corridor northCor=null;
			    		Corridor southCor=null;
			    		
			    		//If we can go north then we should make a copy of the northern corridor to push onto the stack
			    		if(this.maze.canGoNorth()){
			    			northCor=this.currRoboCorridor.clone();
			    			northCor.setTheChar('^');
			    		}
			    		//If we can go south then we should make a copy of the southern corridor to push onto the stack
			    		if(this.maze.canGoSouth()){
			    			southCor=this.currRoboCorridor.clone();
			    			southCor.setTheChar('v');	
			    		}
			    		
			    		//Now move to the west
			    		move=this.goWest();
			    		//The move is successful
			    		if(move.isResult()){
			    			//When facing west, we should try to move north last, therefore push it onto the stack first		    			 
			    			if(null!=northCor){
			    				this.gameStack.push(northCor);
			    			}
			    			if(null!=southCor){
			    				this.gameStack.push(southCor);
			    			}
			    			this.gameStack.push(backTrackMove);
			    		}
			    		else{
			    			keepGoing=false;
			    		}
			    	}
			    	else if(this.maze.canGoSouth()){
			    		move=this.turnSouth();
			    	}
			    	//If we can turn north we have to turn south, east, and then north
			    	else if(this.maze.canGoNorth()){
			    		move=this.turnSouth();
			    		if(move.isResult()){	
			    			System.out.println("\n");
			    			System.out.println("Count: "+count);			    			
			    			this.maze.printMaze();
			    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
			    			++count;
			    		    move=this.turnEast();
			    		    if(move.isResult()){
				    			System.out.println("\n");
				    			System.out.println("Count: "+count);			    			
				    			this.maze.printMaze();
				    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
				    			++count;
			    		    	move=this.turnNorth();
			    		    	if(!move.isResult()){
			    		    		System.out.println("ERROR: "+move.getMessage());
			    		    		keepGoing=false;
			    		    	}
			    		    }
					    	else{
					    		System.out.println("ERROR: "+move.getMessage());
					    		keepGoing=false;
					    	}
			    		}
				    	else{
				    		System.out.println("ERROR: "+move.getMessage());
				    		keepGoing=false;
				    	}			    		
			    	}
			    	//We have to back track
			    	else{
			    		if(this.turnAround(count)){
			    			count+=2;
			    			count+=this.backTrackMoves(count);			    			
			    		}
			    		else{
			    			keepGoing=false;
			    		}
			    	}			    	
			    	break;
			    }
			    case '>':{
			    	
			    	if(this.maze.canGoEast()){
			    		
			    		//Make a copy of the current corridor but with the opposite direction to push onto the stack
			    		backTrackMove.setTheChar('<');
			    		Corridor northCor=null;
			    		Corridor southCor=null;
			    		
			    		//If we can go north then we should make a copy of the northern corridor to push onto the stack
			    		if(this.maze.canGoNorth()){
			    			northCor=this.currRoboCorridor.clone();
			    			northCor.setTheChar('^');
			    		}
			    		//If we can go south then we should make a copy of the southern corridor to push onto the stack
			    		if(this.maze.canGoSouth()){
			    			southCor=this.currRoboCorridor.clone();
			    			southCor.setTheChar('v');	
			    		}
			    		
			    		//Now move to the west
			    		move=this.goEast();
			    		//The move is successful
			    		if(move.isResult()){
			    			//When facing east, we should try to move south last, therefore push it onto the stack first		    			 
			    			if(null!=southCor){
			    				this.gameStack.push(southCor);
			    			}
			    			if(null!=northCor){
			    				this.gameStack.push(northCor);
			    			}
			    			this.gameStack.push(backTrackMove);
			    		}
			    		else{
			    			keepGoing=false;
			    		}
			    	}
			    	else if(this.maze.canGoNorth()){
			    		move=this.turnNorth();
			    	}
			    	//If we can turn north we have to turn south, east, and then north
			    	else if(this.maze.canGoSouth()){
			    		move=this.turnNorth();
			    		if(move.isResult()){
			    			System.out.println("\n");
			    			System.out.println("Count: "+count);			    			
			    			this.maze.printMaze();
			    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
			    			++count;
			    		    move=this.turnWest();
			    		    if(move.isResult()){
				    			System.out.println("\n");
				    			System.out.println("Count: "+count);			    			
				    			this.maze.printMaze();
				    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
				    			++count;
			    		    	move=this.turnSouth();
			    		    	if(!move.isResult()){
			    		    		System.out.println("ERROR: "+move.getMessage());
			    		    		keepGoing=false;
			    		    	}
			    		    }
					    	else{
					    		System.out.println("ERROR: "+move.getMessage());
					    		keepGoing=false;
					    	}
			    		}
				    	else{
				    		System.out.println("ERROR: "+move.getMessage());
				    		keepGoing=false;
				    	}			    		
			    	}
			    	//We have to back track
			    	else{
			    		if(this.turnAround(count)){
			    			count+=2;
			    			count+=this.backTrackMoves(count);
			    		}
			    		else{
			    			keepGoing=false;
			    		}
			    	}					    				    	
			    	break;
			    }
			    case 'v':{
			    	//If we can go in the same direction then do it
			    	if(this.maze.canGoSouth()){
			    		
			    		//First we have to copy the corridor but in the opposite direction for pushing onto the stack
			    		backTrackMove.setTheChar('^');
			    		Corridor westCor=null;
			    		Corridor eastCor=null;
			    		
			    		//We also should also copy the western corridor to push onto the stack if we can go west
			    		if(this.maze.canGoWest()){
			    			westCor=this.currRoboCorridor.clone();
			    			westCor.setTheChar('<');
			    		}
			    		//We also should copy the eastern corridor to push onto the stack if we can go east
			    		if(this.maze.canGoEast()){
			    			eastCor=this.currRoboCorridor.clone();
			    			eastCor.setTheChar('>');	
			    		}
			    		
			    		//Now we move South
			    		move=this.goSouth();
			    		//The move is successful
			    		if(move.isResult()){	
			    			//west is the last direction we want to try if we are facing south
			    			if(null!=westCor){
			    				this.gameStack.push(westCor);
			    			}
			    			if(null!=eastCor){			    				
			    				this.gameStack.push(eastCor);
			    			}	
			    			this.gameStack.push(backTrackMove);
			    		}
				    	else{
				    		System.out.println("ERROR: "+move.getMessage());
				    		keepGoing=false;
				    	}
			    	}
			    	//We can't go north but we can go west, therefore turn west
			    	else if(this.maze.canGoEast()){
			    		move=this.turnEast();
			    		if(!move.isResult()){
			    			System.out.println("Error: "+move.getMessage());
			    			keepGoing=false;
			    		}
			    	}
			    	//We can't go north nor can we go west, therefore try turning south and then east
			    	else if(this.maze.canGoWest()){
			    		move=this.turnEast();
			    		if(move.isResult()){
			    			System.out.println("\n");
			    			System.out.println("Count: "+count);			    			
			    			this.maze.printMaze();
			    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
			    			++count;
			    			move=this.turnNorth();
			    			if(move.isResult()){
				    			System.out.println("\n");
				    			System.out.println("Count: "+count);			    			
				    			this.maze.printMaze();
				    			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
				    			++count;
			    				move=this.turnWest();
			    				if(!move.isResult()){
			    					System.out.println("ERROR: "+move.getMessage());
			    					keepGoing=false;
			    				}
			    			}
					    	else{
					    		System.out.println("ERROR: "+move.getMessage());
					    		keepGoing=false;
					    	}			    			
			    		}
			    		else{
			    			System.out.println("Error: "+move.getMessage());
			    			keepGoing=false;
			    		}
			    	}
			    	//We have to pop until the direction is no longer south
			    	else{
			    		if(this.turnAround(count)){
			    			count+=2;
			    			count+=this.backTrackMoves(count);	
			    		}	
			    		else{
			    		    keepGoing=false;
			    		}
			    	}				    	
			    	break;
			    }
			    default:{
			    	break;
			    }
			}
			System.out.println("\n");
			System.out.println("Count: "+count);
			this.maze.printMaze();
			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
			++count;
		}
	}
	
	private int backTrackMoves(int count){
		char startDir=this.maze.getCurrRoboCorridor().getTheChar();
		
		if(!this.gameStack.isEmpty()){
			Corridor poppedCor=this.gameStack.pop();		
			char stackDir=poppedCor.getTheChar();
			this.maze.setCurrRoboCorridor(poppedCor, stackDir);
			
			System.out.println("\n");
			System.out.println("Count: "+count);
			this.maze.printMaze();
			++count;
			while(!(this.gameStack.isEmpty()) && stackDir==startDir){			    			
				this.maze.setCurrRoboCorridor(poppedCor, stackDir);
				this.maze.setCurrRoboCorridor(poppedCor, stackDir);
				System.out.println("\n");
				System.out.println("Count: "+count);
				this.maze.printMaze();
				poppedCor=this.gameStack.pop();
				stackDir=poppedCor.getTheChar();
				++count;
			}	
			this.maze.setCurrRoboCorridor(poppedCor, stackDir);
			this.maze.setCurrRoboCorridor(poppedCor, stackDir);
		}
		return count;
	}
	
	
	private boolean turnAround(int count){
		char roboDirection=this.currRoboCorridor.getTheChar();
		boolean retVal=false;
		MoveResult move=null;
		switch(roboDirection){
		    case '^':{
		    	move=this.turnWest();
		    	if(move.isResult()){
					System.out.println("\n");
					System.out.println("Count: "+count);
					++count;
		    		this.maze.printMaze();
		    		move=this.turnSouth();
		    		if(move.isResult()){
		    			System.out.println("\n");
		    			System.out.println("Count: "+count);
		    			this.maze.printMaze();
		    			Corridor turnAroundCorridor=this.maze.getCurrRoboCorridor().clone();
		    			this.gameStack.push(turnAroundCorridor);
		    			retVal=true;
		    		}
			    	else{
			    		System.out.println("ERROR: "+move.getMessage());
			    	}
		    	}
		    	else{
		    		System.out.println("ERROR: "+move.getMessage());		    		
		    	}
		    	break;
		    }
		    case '<':{
		    	move=this.turnSouth();
		    	if(move.isResult()){
	    			System.out.println("\n");
	    			System.out.println("Count: "+count);
	    			++count;
		    		this.maze.printMaze();
		    		move=this.turnEast();
		    		if(move.isResult()){
		    			System.out.println("\n");
		    			System.out.println("Count: "+count);
		    			this.maze.printMaze();
		    			Corridor turnAroundCorridor=this.maze.getCurrRoboCorridor().clone();
		    			this.gameStack.push(turnAroundCorridor);
		    			retVal=true;
		    		}
			    	else{
			    		System.out.println("ERROR: "+move.getMessage());
			    	}
		    	}
		    	else{
		    		System.out.println("ERROR: "+move.getMessage());
		    	}
		    	break;
		    }
		    case '>':{
		    	move=this.turnNorth();
		    	if(move.isResult()){
	    			System.out.println("\n");
	    			System.out.println("Count: "+count);
	    			++count;
		    		this.maze.printMaze();
		    		move=this.turnWest();
		    		if(move.isResult()){
		    			System.out.println("\n");
		    			System.out.println("Count: "+count);
		    			this.maze.printMaze();
		    			retVal=true;
		    		}
			    	else{
			    		System.out.println("ERROR: "+move.getMessage());
			    	}
		    	}
		    	else{
		    		System.out.println("ERROR: "+move.getMessage());
		    	}
		    	break;
		    }
		    case 'v':{
		    	move=this.turnEast();
		    	if(move.isResult()){
	    			System.out.println("\n");
	    			System.out.println("Count: "+count);
	    			++count;
		    		this.maze.printMaze();
		    		move=this.turnNorth();
		    		if(move.isResult()){
		    			System.out.println("\n");
		    			System.out.println("Count: "+count);
		    			this.maze.printMaze();
		    			Corridor turnAroundCorridor=this.maze.getCurrRoboCorridor().clone();
		    			this.gameStack.push(turnAroundCorridor);
		    			retVal=true;
		    		}
			    	else{
			    		System.out.println("ERROR: "+move.getMessage());
			    	}
		    	}
		    	else{
		    		System.out.println("ERROR: "+move.getMessage());
		    	}
		    	break;
		    }
		    default:{
		    	//do nothing
		    	break;
		    }
		}
		return retVal;
	}
	
	
	public MoveResult turnWest(){
		MoveResult retVal=this.maze.setCurrRoboCorridor(this.currRoboCorridor, '<');
		if(retVal.isResult()){
			++this.turnCount;
		}
		return retVal;
	}
	
	public MoveResult goWest(){
		Corridor westernCorridor=this.currRoboCorridor.getWestDirection();
		MoveResult retVal=this.maze.setCurrRoboCorridor(westernCorridor, '<');
		this.currRoboCorridor=westernCorridor;
		if(retVal.isResult()){
			++this.moveCount;
		}
		return retVal;
	}
	
	public MoveResult turnSouth(){
		MoveResult retVal=this.maze.setCurrRoboCorridor(this.currRoboCorridor, 'v');
		if(retVal.isResult()){
			++this.turnCount;
		}
		return retVal;
	}
	
	public MoveResult goSouth(){
		Corridor southernCorridor=this.currRoboCorridor.getSouthDirection();
		MoveResult retVal=this.maze.setCurrRoboCorridor(southernCorridor, 'v');
		this.currRoboCorridor=southernCorridor;
		if(retVal.isResult()){
			++this.moveCount;
		}
		return retVal;
	}

	public MoveResult turnEast(){
		MoveResult retVal=this.maze.setCurrRoboCorridor(this.currRoboCorridor, '>');
		if(retVal.isResult()){
			++this.turnCount;
		}
		return retVal;
	}
	
	public MoveResult goEast(){
		Corridor easternCorridor=this.currRoboCorridor.getEastDirection();
		MoveResult retVal=this.maze.setCurrRoboCorridor(easternCorridor, '>');
		this.currRoboCorridor=easternCorridor;
		if(retVal.isResult()){
			++this.moveCount;
		}
		return retVal;
	}
	
	public MoveResult turnNorth(){
		MoveResult retVal=this.maze.setCurrRoboCorridor(this.currRoboCorridor, '^');		
		if(retVal.isResult()){
			++this.turnCount;
		}
		return retVal;
	}
	
	public MoveResult goNorth(){
		Corridor northernCorridor=this.currRoboCorridor.getNorthDirection();
		MoveResult retVal=this.maze.setCurrRoboCorridor(northernCorridor, '^');
		this.currRoboCorridor=northernCorridor;
		if(retVal.isResult()){
			++this.moveCount;
		}
		return retVal;
	}
		

}
