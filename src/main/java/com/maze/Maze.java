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
	
	public Corridor getCurrRoboCorridor(){
		return this.currRoboCorridor;
	}
	
	public MoveResult setCurrRoboCorridor(Corridor cor, char newDir){
		MoveResult retVal=new MoveResult();
		retVal.setMessage(MoveResult.CURR_ROBO_IS_NULL);
		retVal.setResult(false);
		if(null!=cor){
			int xCor=cor.getxCoordinate();
			int yCor=cor.getyCoordinate();

			int x=this.currRoboCorridor.xCoordinate;
			int y=this.currRoboCorridor.yCoordinate;
			char dir=this.currRoboCorridor.getTheChar();
			
			int width=this.maze.length;
			int height=this.maze[0].length;
			
			char dirCor=cor.getTheChar();
            if(xCor>=width || xCor < 0 || yCor>=height || yCor<0){
            	retVal.setMessage(MoveResult.OUT_OF_BOUNDS_MOVE);
            }
            else{
            	if(dirCor!='*' ){
            		//This is a north or south movement
            		if(x==xCor){
            			//This may be a turn
            			if(y==yCor){
            				//We are currently facing west
            				if(dir=='<'){          					
            					if(newDir=='v'||newDir=='^'){
            						this.currRoboCorridor.setTheChar(newDir);
            						retVal.setMessage(MoveResult.SUCCESSFUL_TURN);
            						retVal.setResult(true);
            					}
            					else if(newDir=='<'){
            						retVal.setMessage(MoveResult.ROBO_ALREADY_WEST);
            					}
            					else{
            						retVal.setMessage(MoveResult.CANT_TURN_EAST);
            					}
            				}
            				//We are currently facing south
            				else if(dir=='v'){
            					if(newDir=='<'||newDir=='>'){
            						this.currRoboCorridor.setTheChar(newDir);
            						retVal.setMessage(MoveResult.SUCCESSFUL_TURN);
            						retVal.setResult(true);
            					}
            					else if(newDir=='v'){
            						retVal.setMessage(MoveResult.ROBO_ALREADY_SOUTH);
            					}
            					else{
            						retVal.setMessage(MoveResult.CANT_TURN_NORTH);
            					}            					
            				}
            				//We are currently facing east
            				else if(dir=='>'){
            					if(newDir=='v'||newDir=='^'){
            						this.currRoboCorridor.setTheChar(newDir);
            						retVal.setMessage(MoveResult.SUCCESSFUL_TURN);
            						retVal.setResult(true);
            					}
            					else if(newDir=='>'){
            						retVal.setMessage(MoveResult.ROBO_ALREADY_EAST);
            					}
            					else{
            						retVal.setMessage(MoveResult.CANT_TURN_WEST);
            					}           					
            				}
            				//We are currently facing north
            				else if(dir=='^'){
            					if(newDir=='<'||newDir=='>'){
            						this.currRoboCorridor.setTheChar(newDir);
            						retVal.setMessage(MoveResult.SUCCESSFUL_TURN);
            						retVal.setResult(true);
            					}
            					else if(newDir=='^'){
            						retVal.setMessage(MoveResult.ROBO_ALREADY_NORTH);
            					}
            					else{
            						retVal.setMessage(MoveResult.CANT_TURN_SOUTH);
            					}           					
            				}
            			}
            			//This is a movement
            			else{
            				//This is a northern movement
            				if(yCor==(y+1)){
            					//The robot must be facing north
            					if(dir=='^' && dir==newDir){
            						retVal.setResult(true);
            						retVal.setMessage(MoveResult.SUCCESSFUL_MOVE);
            						this.changeCurrRoboCorr(cor, newDir);
            					}
            					//The robot is not facing the right way
            					else{
            						retVal.setMessage(MoveResult.MUST_FACE_NORTH);
            					}
            				}
            				//This is a southern movement
            				else if(yCor==(y-1)){
            					//The robot is facing in the right direction to move south
            					if(dir=='V' && dir==newDir){
            						retVal.setResult(true);
            						retVal.setMessage(MoveResult.SUCCESSFUL_MOVE);	
            						this.changeCurrRoboCorr(cor, newDir);
            					}
            					else{
            						retVal.setMessage(MoveResult.MUST_FACE_SOUTH);
            					}
            				}
            				else{
            					retVal.setMessage(MoveResult.ILLEGAL_MOVE_NON_ADJACENT);
            				}
            			}
            		}
            		//This is a west or east movement
            		else if(y==yCor){
            			//Moving east
            			if(xCor==(x+1)){
            				//The robot is facing in the right direction to move east
            				if(dir=='>' && dir==newDir){
            					retVal.setResult(true);
            					retVal.setMessage(MoveResult.SUCCESSFUL_MOVE);
            					this.changeCurrRoboCorr(cor, newDir);
            				}
            				else{
            					retVal.setMessage(MoveResult.MUST_FACE_EAST);
            				}
            			}
            			//This is a western movement
            			else if(xCor==(x-1)){
            				//The robot is facing in the right direction to move west
            				if(dir=='<' && dir==newDir){
            					retVal.setResult(true);
            					retVal.setMessage(MoveResult.SUCCESSFUL_MOVE);	
            					this.changeCurrRoboCorr(cor, newDir);
            				}
            				else{
            					retVal.setMessage(MoveResult.MUST_FACE_WEST);
            				}
            			}
            			else{
            				retVal.setMessage(MoveResult.ILLEGAL_MOVE_NON_ADJACENT);
            			}
            		}
            		else{
            			retVal.setMessage(MoveResult.ILLEGAL_MOVE_NON_ADJACENT);
            		}
            	}
            	else{
            		retVal.setMessage(MoveResult.CANNOT_MOVE_TO_WALL_CORRIDOR);
            	}
            }
		}		
		return retVal;
	}
	
	private void changeCurrRoboCorr(Corridor cor, char newDir){
		//this.currRoboCorridor.setTheChar(' ');	
		this.maze[this.currRoboCorridor.getxCoordinate()][this.currRoboCorridor.yCoordinate].setTheChar(' ');
		
		this.currRoboCorridor=cor;		
		if(cor.getTheChar()!='F'){
			this.currRoboCorridor.setTheChar(newDir);
			this.maze[cor.getxCoordinate()][cor.getyCoordinate()].setTheChar(newDir);
		}
		else{
			this.currRoboCorridor.setTheChar('!');
			this.maze[cor.getxCoordinate()][cor.getyCoordinate()].setTheChar('!');
		}
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
