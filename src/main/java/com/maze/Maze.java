package com.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Maze {

	private Corridor[][] maze;
	private Corridor startCorridor;
	private Corridor flagCorridor;
	private Corridor currRoboCorridor;

	public Corridor getStartCorridor(){
	    return this.startCorridor;	
	}
	
	public int getWidth(){
		if(null!=maze){
			return maze.length;
		}
		else{
			return 0;
		}
	}
	
	public int getHeight(){
		if(null!=maze && maze[0]!=null){
			return maze[0].length;			
		}
		else{
			return 0;
		}
	}

	private void setMaze(InputStream inny){
		if(null!=inny){

			InputStreamReader innyReader=new InputStreamReader(inny);
			BufferedReader buffReader=new BufferedReader(innyReader);

			try{

				String firstLine=buffReader.readLine();
				String secondLine=buffReader.readLine();

				if(null!=firstLine && !firstLine.isEmpty() && null!=secondLine && !secondLine.isEmpty()){
					Integer width=Integer.valueOf(firstLine);
					Integer height=Integer.valueOf(secondLine);
					maze=new Corridor[width][height];					

					String aRow=null;
					int currRow=height;	

					while(null!=(aRow=buffReader.readLine())){
						--currRow;
						for(int i=0;i<aRow.length();++i){
							char theChar=aRow.charAt(i);
							Corridor theCorridor=new Corridor();
							maze[i][currRow]=theCorridor;
							theCorridor.setxCoordinate(i);
							theCorridor.setyCoordinate(currRow);
							theCorridor.setTheChar(theChar);
							
							if(isARobotChar(theChar)){
								this.startCorridor=theCorridor;
								this.currRoboCorridor=theCorridor;
							}
							//This is the western edge of the wall, it has to be a wall and there is no more wall to the west
							if(i==0){
								theCorridor.setWestDirection(null);							
								
							}
							//This is the eastern edge of the wall, it has to be a wall and there is no more wall to the east
							else if(i==(width-1)){
								theCorridor.setEastDirection(null);
								Corridor westCor=maze[(i-1)][currRow];
								theCorridor.setWestDirection(westCor);
								
								westCor.setEastDirection(theCorridor);
							}
							//This is in between the western edge and eastern edge of the maze
							else{
								Corridor westCor=maze[(i-1)][currRow];
								theCorridor.setWestDirection(westCor);
								westCor.setEastDirection(theCorridor);								
							}
							
							//This is the top row, there are no more walls to the north
							if((currRow+1)==height){
								theCorridor.setNorthDirection(null);
							}
							//This is the bottom row, there are no more walls to the south
							else if(currRow==0){
								theCorridor.setSouthDirection(null);
								
								Corridor northCor=this.maze[i][(currRow+1)];
								theCorridor.setNorthDirection(northCor);
								
								northCor.setSouthDirection(theCorridor);
							}
							//This is in between the northern edge of the maze and the southern edge of the map
							else{
								Corridor northCor=this.maze[i][(currRow+1)];
								theCorridor.setNorthDirection(northCor);
								northCor.setSouthDirection(theCorridor);								
							}
							
							if(theChar=='F'){
								theCorridor.setHasFlag(true);
								theCorridor.setHasRobot(false);
								this.flagCorridor=theCorridor;
							}
							else if(isARobotChar(theChar)){
								theCorridor.setHasFlag(false);
								theCorridor.setHasRobot(true);								
							}
						}
					}
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
	}

	private boolean isARobotChar(char theChar){
		return (theChar=='<'||theChar=='>'||theChar=='^'||theChar=='v');
	}
	
	public Corridor getRoboPosition(){
		return this.currRoboCorridor;
	}
	
	public boolean canGoWest(){
		boolean retVal=false;
		Corridor westCorridor=this.currRoboCorridor.getWestDirection();
		char theChar=westCorridor.getTheChar();		
		if(theChar==' '||theChar=='F'||(this.isARobotChar(theChar))){
			retVal=true;
		}
		return retVal;
	}
	
	public boolean canGoSouth(){
		boolean retVal=false;
		Corridor southCorridor=this.currRoboCorridor.getSouthDirection();
		char theChar=southCorridor.getTheChar();		
		if(theChar==' '||theChar=='F'||(this.isARobotChar(theChar))){
			retVal=true;
		}
		return retVal;
	}
	
	public boolean canGoEast(){
		boolean retVal=false;
		Corridor eastCorridor=this.currRoboCorridor.getEastDirection();
		char theChar=eastCorridor.getTheChar();		
		if(theChar==' '||theChar=='F'||(this.isARobotChar(theChar))){
			retVal=true;
		}
		return retVal;
	}
	
	public boolean canGoNorth(){
		boolean retVal=false;
		Corridor northCorridor=this.currRoboCorridor.getNorthDirection();
		char theChar=northCorridor.getTheChar();		
		if(theChar==' '||theChar=='F'||(this.isARobotChar(theChar))){
			retVal=true;
		}
		return retVal;
	}
	
	public void printMaze(){
		for(int i=(maze[0].length-1);i>=0;--i){
			for(int j=0;j<maze.length;++j){
				System.out.print(maze[j][i].getTheChar());
			}
			System.out.println("");
		}
	}
	
	public MoveResult goWest(){
		MoveResult retVal=new MoveResult();
		retVal.setResult(false);
		char roboDirection=this.currRoboCorridor.getTheChar();
		if(this.canGoWest()){
			
			if(roboDirection=='<'){

				this.currRoboCorridor=this.currRoboCorridor.getWestDirection();
				this.currRoboCorridor.set
			}
			else{
				retVal.setMessage(MoveResult.MUST_FACE_WEST);
			}
		}
		else{
			retVal.setMessage(MoveResult.WEST_IS_A_WALL);
		}
		return retVal;
	}
	
	public Maze(){
		InputStream innyStream=Maze.class.getResourceAsStream("maze1.txt");
		this.setMaze(innyStream);
		System.out.println("width="+this.getWidth());
		System.out.println("height="+this.getHeight());		
		this.printMaze();
		Corridor startCorridor=this.startCorridor;
		System.out.println("Start corridor, x coordinate="+startCorridor.getxCoordinate()
		+", y coordinate="+startCorridor.getyCoordinate()+", position="+startCorridor.getTheChar());
	}
	
}
