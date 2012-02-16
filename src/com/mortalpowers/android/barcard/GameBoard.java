package com.mortalpowers.android.barcard;

import java.util.ArrayList;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameBoard extends GridLayout {
	public static final int width = 10;
	public static final int height = 10;
	private ArrayList<ArrayList<TextView>> data;
	private ArrayList<Player> players;
	
	
	public GameBoard(Context context, ArrayList<Player> players) {
		super(context);
		setRowCount(height);
		setColumnCount(width);
		
		data = new ArrayList<ArrayList<TextView>>();
		
		this.players = players;
		
		for (int i = 0; i < width; i++) {
			ArrayList<TextView> row = new ArrayList<TextView>();
			data.add(row);
			for (int j = 0; j < height; j++) {
				TextView tv = new TextView(context);
				tv.setText(" " + i + "x" + j + " ");
				row.add(tv);
				addView(tv);
			}
		}
		
		updateBoard();
	}
	
	public void updateBoard() {
		String[][] buffer = new String[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				buffer[i][j] = "";
			}
		}
		
		for (Player p : players) {
			buffer[p.x][p.y] = buffer[p.x][p.y] + " " + p.name + " ";
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				TextView t = data.get(i).get(j);
				t.setText("[" + buffer[i][j] + "]");
			}
		}
	}
	
	public void setGridValue(int x, int y, String value) {
		data.get(x).get(y).setText(value);
	}
	
	public String getGridValue(int x, int y) {
		return (String) data.get(x).get(y).getText();
	}
	public int size() {
		return width * height;
	}
}
