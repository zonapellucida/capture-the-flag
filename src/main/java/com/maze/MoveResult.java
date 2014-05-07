package com.maze;

public class MoveResult {
	
	public static final String WEST_IS_A_WALL="Western corridor is a wall";
	public static final String MUST_FACE_WEST="You are facing the wrong direction, to move west you must be facing west";
	public static final String CANT_TURN_WEST="You cannot turn west";
	
	public static final String SOUTH_IS_A_WALL="Southern corridor is a wall";
	public static final String MUST_FACE_SOUTH="You are facing the wrong direction, to move south you must be facing south";
	public static final String CANT_TURN_SOUTH="You cannot turn south";
	
	public static final String EAST_IS_A_WALL="Eastern corridor is a wall";
	public static final String MUST_FACE_EAST="You are facing the wrong direction, to move east you must be facing east";
	public static final String CANT_TURN_EAST="You cannot turn east";
	
	public static final String NORTH_IS_A_WALL="Northern corridor is a wall";
	public static final String MUST_FACE_NORTH="You are facing the wrong direction, to move north you must be facing north";
	public static final String CANT_TURN_NORTH="You cannot turn north";
	
	public static final String WEST_WALL_AVAILABLE="Western corridor available";
	public static final String SOUTH_WALL_AVAILABLE="Southern corridor available";
	public static final String EAST_WALL_AVAILABLE="Eastern corridor available";
	public static final String NORTH_WALL_AVAILABLE="Northern corridor available";
	
	public static final String SUCCESSFUL_MOVE="Successful move";
	public static final String SUCCESSFUL_TURN="Successful turn";
	public static final String FLAG_CAPTURED="You have captured the flag";
	public static final String CURR_ROBO_IS_NULL="Current Robot position is null";
	public static final String ILLEGAL_MOVE_NON_ADJACENT="Illegal move, you can only move to an adjacent corridor";
	public static final String CANNOT_MOVE_TO_WALL_CORRIDOR="Cannot move to a wall corridor";
	public static final String OUT_OF_BOUNDS_MOVE="Cannot move out of bounds";
	
	public static final String ROBO_ALREADY_WEST="The robot is already facing west";
	public static final String ROBO_ALREADY_SOUTH="The robot is already facing south";
	public static final String ROBO_ALREADY_EAST="The robot is already facing east";
	public static final String ROBO_ALREADY_NORTH="The robot is already facing north";
	
	private boolean result;
	private String message;
	
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
