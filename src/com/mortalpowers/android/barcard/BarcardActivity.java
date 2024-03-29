package com.mortalpowers.android.barcard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BarcardActivity extends Activity {
	String contents;
	String format;
	ArrayList<Player> players;
	GameBoard gb;
	
	int currentPlayerTurn = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button scan = (Button) findViewById(R.id.button1);
		scan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scanSomething();

			}
		});
		
		
		
		players = new ArrayList<Player>();
		players.add(new Player("1"));
		Player p2 = new Player("2");
		
		players.add(p2);
		
		gb = new GameBoard(this, players);
		LinearLayout l = (LinearLayout) findViewById(R.id.linearLayout1);
		l.addView(gb);
	}

	public void scanSomething() {
		// I need things done! Do I have any volunteers?
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");

		// This flag clears the called app from the activity stack, so users
		// arrive in the expected
		// place next time this application is restarted.
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		
		intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
		intent.putExtra("SCAN_WIDTH", 800);
		intent.putExtra("SCAN_HEIGHT", 200);
		intent.putExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
		intent.putExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
		startActivityForResult(intent, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// The Intents Fairy has delivered us some data!
				contents = intent.getStringExtra("SCAN_RESULT");
				format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				int result;
				try {
					 result = Integer.parseInt(contents);
					if(result > gb.size()) {
						throw new NumberFormatException("advance bigger than game board!");
					}
				} catch (NumberFormatException ex) {
					// Scan again.
					toast("Not a valid game card, please scan again!");
					scanSomething();
					return;
				}
				
				Log.d("barcard","Increased the position of player " + currentPlayerTurn + " by " + result);
				
				players.get(currentPlayerTurn).advance(result);
				currentPlayerTurn = ( currentPlayerTurn + 1) % players.size();
				
				updateViews();
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				Log.d("barcard", "Scan Cancelled.");
			}
		}
	}

	public void toast(String message) {
		toast(message,true);
	}
	public void toast(String message, boolean longToast) {
		
		Toast.makeText(getApplicationContext(), message,
				   longToast ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show(); 
	}
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}

	public void updateViews() {
		TextView contentView = (TextView) findViewById(R.id.contentValue);
		TextView formatView = (TextView) findViewById(R.id.formatValue);
		contentView.setText(contents);
		formatView.setText(format);
		
		gb.updateBoard();
	}
}