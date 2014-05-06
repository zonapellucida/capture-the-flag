package com.maze;

public class Game {
	
	private Maze maze;
	private Corridor start;
	private Corridor flag;
	private Corridor currentPosition;
	
	public Game(){
		this.maze=new Maze();
		this.start=this.maze.getStartCorridor();
		this.currentPosition=this.maze.getStartCorridor();
		
		
	}
	
	
	
	

}
