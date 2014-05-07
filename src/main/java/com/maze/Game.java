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
		//while(this.currRoboCorridor.getTheChar()!='!'){
		while(this.maze.canGoNorth() || this.maze.canGoWest()){
			char currDirection=this.currRoboCorridor.getTheChar();
			MoveResult move=null;
			Corridor prevMove=this.currRoboCorridor.clone();
			
			switch(currDirection){
			    case '^':{
			    	if(this.maze.canGoNorth()){
			    		
			    		prevMove.setTheChar('v');
			    		Corridor westCor=null;
			    		Corridor eastCor=null;
			    		
			    		//First we check to see if we can go west
			    		if(this.maze.canGoWest()){
			    			westCor=this.currRoboCorridor.clone();
			    			westCor.setTheChar('<');
			    		}
			    		//First we check to see if we can go east
			    		if(this.maze.canGoEast()){
			    			eastCor=this.currRoboCorridor.clone();
			    			eastCor.setTheChar('>');	
			    		}
			    		move=this.goNorth();
			    		//The move is successful
			    		if(move.isResult()){
			    			++this.moveCount;
			    			 
			    			if(null!=westCor){
			    				this.gameStack.push(westCor);
			    			}
			    			if(null!=eastCor){
			    				this.gameStack.push(eastCor);
			    			}
			    			
			    		}
			    	}
			    	else if(this.maze.canGoWest()){
			    		move=this.turnWest();
			    	}
			    	else if(this.maze.canGoEast()){
			    		
			    	}
			    	//We have to pop until the direction is no longer south
			    	else{
			    		
			    	}
			    	
			    	break;
			    }
			    case '<':{
			    	if(this.maze.canGoWest()){
			    		
			    		prevMove.setTheChar('>');
			    		Corridor northCor=null;
			    		Corridor southCor=null;
			    		
			    		//First we check to see if we can go west
			    		if(this.maze.canGoWest()){
			    			northCor=this.currRoboCorridor.clone();
			    			northCor.setTheChar('^');
			    		}
			    		//First we check to see if we can go east
			    		if(this.maze.canGoEast()){
			    			southCor=this.currRoboCorridor.clone();
			    			southCor.setTheChar('v');	
			    		}
			    		move=this.goWest();
			    		//The move is successful
			    		if(move.isResult()){
			    			++this.moveCount;
			    			 
			    			if(null!=northCor){
			    				this.gameStack.push(northCor);
			    			}
			    			if(null!=southCor){
			    				this.gameStack.push(southCor);
			    			}
			    			
			    		}
			    	}
			    	else if(this.maze.canGoSouth()){
			    		move=this.turnSouth();
			    	}
			    	else if(this.maze.canGoNorth()){
			    		move=this.turnSouth();
			    		if(move.isResult()){
			    			++this.turnCount;
			    		    move=this.turnEast();
			    		    if(move.isResult()){
			    		    	++this.turnCount;
			    		    	move=this.turnNorth();
			    		    	if(move.isResult()){
			    		    		++this.turnCount;
			    		    	}
			    		    }
			    		}
			    		
			    	}
			    	//We have to pop until the direction is no longer south
			    	else{
			    		
			    	}			    	
			    	break;
			    }
			    case '>':{
			    	
			    	break;
			    }
			    case 'v':{
			    	break;
			    }
			    default:{
			    	break;
			    }
			}
			System.out.println("\n");
			this.maze.printMaze();
			this.currRoboCorridor=this.maze.getCurrRoboCorridor();
		}
	}
	
	
	public MoveResult turnWest(){
		return this.maze.setCurrRoboCorridor(this.currRoboCorridor, '<');
	}
	
	public MoveResult goWest(){
		Corridor westernCorridor=this.currRoboCorridor.getWestDirection();
		return this.maze.setCurrRoboCorridor(westernCorridor, '<');
	}
	
	public MoveResult turnSouth(){
		return this.maze.setCurrRoboCorridor(this.currRoboCorridor, 'v');
	}
	
	public MoveResult goSouth(){
		Corridor southernCorridor=this.currRoboCorridor.getSouthDirection();
		return this.maze.setCurrRoboCorridor(southernCorridor, 'v');
	}

	public MoveResult turnEast(){
		return this.maze.setCurrRoboCorridor(this.currRoboCorridor, '>');
	}
	
	public MoveResult goEast(){
		Corridor easternCorridor=this.currRoboCorridor.getEastDirection();
		return this.maze.setCurrRoboCorridor(easternCorridor, '>');
	}
	
	public MoveResult turnNorth(){
		return this.maze.setCurrRoboCorridor(this.currRoboCorridor, '^');
	}
	
	public MoveResult goNorth(){
		Corridor northernCorridor=this.currRoboCorridor.getNorthDirection();
		return this.maze.setCurrRoboCorridor(northernCorridor, '^');
	}
		

}
