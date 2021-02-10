package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // 0: yellow, 1: red, 2: empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int activePlayer = 0;
    Boolean gameActive = true;

    public void putIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        //check: only one chance to choose cell & if someone win, the game will stop
        if (gameState[tappedCounter] == 2 && gameActive) {

            //define who is a current player
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000);

            //Depending on who is walking, the chip becomes a certain color(yellow / red)
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000).setDuration(200);

            //GAME
            for (int[] winPos : winningPositions) {
                if (gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[0]] != 2) {
                    String winner = "";
                    if (activePlayer == 1) {
                        winner += "Yellow";
                    } else {
                        winner += "Red";
                    }
                    Toast.makeText(this, "Winner is " + winner, Toast.LENGTH_LONG).show();

                    gameActive = false;

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void playAgain(View view) {
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        //I don't know why, but when I want to recreate GridLayout, GridLayout crashes my app
        //These options don't work
        //GridLayout gridLayout = super.findViewById(R.id.gridLayout);
        //android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        //These option work
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        //Interesting method fill array, without loop
        Arrays.fill(gameState, 2);
        activePlayer = 0;
        Boolean gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}