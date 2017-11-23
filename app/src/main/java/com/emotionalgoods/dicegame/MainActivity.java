package com.emotionalgoods.dicegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static float START_BALANCE = 2000;

    private TextView bankAccount, input1, input2;
    private EditText playerChoice;
    private Button play;

    private enum GameState {
        INPUT, BET, RESULT;
    }

    private GameState currentState = GameState.INPUT;
    private float balance = START_BALANCE;
    private int cpuPick = 0, playerPick = 0;
    private float betAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankAccount = findViewById(R.id.balance);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        playerChoice = findViewById(R.id.playerChoice);
        play = findViewById(R.id.start);

        update();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentState == GameState.RESULT){
                    Toast.makeText(MainActivity.this, "pick a number, 1-6: ", Toast.LENGTH_SHORT).show();
                    currentState = GameState.INPUT;
                    playerChoice.setText("");
                    cpuPick = 0;
                    playerPick = 0;
                }
                update();
            }
        });

        playerChoice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(currentState == GameState.INPUT) {
                    cpuPick = new Random().nextInt(6) + 1;
                    playerPick = Integer.parseInt(playerChoice.getText().toString());
                    if(playerPick <= 0 || playerPick >= 7)
                        Toast.makeText(MainActivity.this, "Invalid ranges. Choose again", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MainActivity.this, "enter your bet amount", Toast.LENGTH_SHORT).show();
                        currentState = GameState.BET;
                    }
                    playerChoice.setText("");
                }
                else if (currentState == GameState.BET){
                    betAmount = Integer.parseInt(playerChoice.getText().toString());
                    if(cpuPick == playerPick)
                        balance += betAmount;
                    else
                        balance -= betAmount;
                    currentState = GameState.RESULT;
                }
                update();
                return false;
            }
        });

    }
    private void update() {
        bankAccount.setText("$" + String.valueOf(balance));
        switch(currentState){
            case RESULT:
                input1.setText(String.valueOf(cpuPick));
                input2.setText(String.valueOf(playerPick));
                break;
            default:
                input1.setText("");
                input2.setText("");
                break;
        }

    }


}
