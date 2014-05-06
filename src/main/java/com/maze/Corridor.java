package com.maze;

public class Corridor {
	

	private Corridor westDirection;
	private Corridor southDirection;
	private Corridor eastDirection;
	private Corridor northDirection;
	
	private boolean isWall;
	
	private boolean hasFlag;
	private boolean hasRobot;
	
	int xCoordinate;
	int yCoordinate;
	
	char theChar;

	public Corridor getWestDirection() {
		return westDirection;
	}

	public void setWestDirection(Corridor westDirection) {
		this.westDirection = westDirection;
	}

	public Corridor getSouthDirection() {
		return southDirection;
	}

	public void setSouthDirection(Corridor southDirection) {
		this.southDirection = southDirection;
	}

	public Corridor getEastDirection() {
		return eastDirection;
	}

	public void setEastDirection(Corridor eastDirection) {
		this.eastDirection = eastDirection;
	}

	public Corridor getNorthDirection() {
		return northDirection;
	}

	public void setNorthDirection(Corridor northDirection) {
		this.northDirection = northDirection;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public boolean isHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public boolean isHasRobot() {
		return hasRobot;
	}

	public void setHasRobot(boolean hasRobot) {
		this.hasRobot = hasRobot;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public char getTheChar() {
		return theChar;
	}

	public void setTheChar(char theChar) {
		this.theChar = theChar;
	}
	

	
}
