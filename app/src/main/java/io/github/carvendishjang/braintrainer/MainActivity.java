package io.github.carvendishjang.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;

    Button restartButton;
    Button goButton;
    TextView timerView;
    TextView mathView;
    TextView messageView;
    TextView scoreView;

    int score;
    int timesTapped;
    int correctAnswer;
    boolean gameActive = false;
    int timeLimit;

    CountDownTimer timer;

    Random rand = new Random();

    public void go(View view) {


        goButton.setVisibility(View.GONE);
        timerView.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        mathView.setVisibility(View.VISIBLE);
        gameActive = true;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView square = (TextView) gridLayout.getChildAt(i);
            square.setVisibility(View.VISIBLE);
        }
        generateMath();
        timer = new CountDownTimer(timeLimit*1000, 1000) {
            @Override
            public void onTick(long millisecondsUntillDone) {
                timerView.setText(String.valueOf(millisecondsUntillDone/1000) + "s");
            }

            @Override
            public void onFinish() {
                messageView.setText("Your score is " + scoreView.getText());
                gameActive = false;
                restartButton.setVisibility(View.VISIBLE);
                reset();
            }
        }.start();

    }

    public void reset(){
        timerView.setText("10s");
        mathView.setText("");
        mathView.setVisibility(View.INVISIBLE);
        scoreView.setText("0/0");


        score = 0;
        timesTapped = 0;

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView square = (TextView) gridLayout.getChildAt(i);
            square.setText("");
        }

        timer.cancel();
    }

    public void restart(View view) {
        timer.start();
        gameActive = true;
        messageView.setText("");
        mathView.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.INVISIBLE);
        generateMath();
    }

    public void generateMath() {
        int x = rand.nextInt(50);
        int y = rand.nextInt(50);
        correctAnswer = x + y;

        mathView.setText(Integer.toString(x) + " + " + Integer.toString(y) + " =");

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            TextView square = (TextView) gridLayout.getChildAt(i);
            int num = correctAnswer;
            while(num == correctAnswer) {
                num = rand.nextInt(100);
            }
            square.setText(String.valueOf(num));
        }

        int ansPos = rand.nextInt(4);
        TextView correct = (TextView) gridLayout.getChildAt(ansPos);
        correct.setText(String.valueOf(correctAnswer));

    }

    public void tap (View view) {

        if (!gameActive) {
            Toast.makeText(this, "Please restart the game.", Toast.LENGTH_SHORT).show();
        } else {
            TextView tappedSquare = (TextView) view;

            int yourAnswer = Integer.parseInt(tappedSquare.getText().toString());

            if (yourAnswer == correctAnswer) {
                score++;
                messageView.setText("Bingo!");
            } else {
                messageView.setText("Wrong!");
            }
            timesTapped++;
            scoreView.setText(String.valueOf(score) + "/" + String.valueOf(timesTapped));

            generateMath();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        goButton = findViewById(R.id.goButton);
        restartButton = findViewById(R.id.restartButton);
        scoreView = findViewById(R.id.scoreView);
        timerView = findViewById(R.id.timerView);
        mathView = findViewById(R.id.mathView);
        messageView = findViewById(R.id.messageView);
        timeLimit = 30;

        score = 0;
        timesTapped = 0;

    }
}
