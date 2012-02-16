package com.mortalpowers.android.barcard;

public class Player {
	int x = 0;
	int y = 0;
	String name = "Unnamed";
	
	public Player(String name) {
		this.name = name;
	}
	
	public void move(int toX, int toY) {
		x = toX;
		y = toY;
	}
	
	public void advance(int count) {
		x += count;
		y += x / GameBoard.width;
		x = x % GameBoard.width;
	}
}
