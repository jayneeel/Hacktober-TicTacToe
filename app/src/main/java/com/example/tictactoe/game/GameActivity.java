package com.example.tictactoe.game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.tictactoe.R.drawable.o;
import static com.example.tictactoe.R.drawable.x;

import com.bumptech.glide.Glide;
import com.example.tictactoe.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    String p1,p2;
    ImageView img, imgWinner;
    static ImageView et1, et2, et3, et4, et5, et6, et7, et8, et9;

    ImageView[] cellImageViews = new ImageView[9];
    public Button restart;
    public TextView status;
    int xplayer=0;
    int oplayer=1;
    int count=8;
    int currentPlayer=xplayer;
    Integer[] positions={-1,-1,-1,-1,-1,-1,-1,-1,-1};
    boolean activeGame=true;

    SoundPool soundPool;
    int playSound, winnerSound, tieSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        status = findViewById(R.id.status);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);
        et8 = findViewById(R.id.et8);
        et9 = findViewById(R.id.et9);
        restart = findViewById(R.id.button3);

        cellImageViews[0] = et1;
        cellImageViews[1] = et2;
        cellImageViews[2] = et3;
        cellImageViews[3] = et4;
        cellImageViews[4] = et5;
        cellImageViews[5] = et6;
        cellImageViews[6] = et7;
        cellImageViews[7] = et8;
        cellImageViews[8] = et9;

        imgWinner = findViewById(R.id.imgWinner);

        soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION,0);
        playSound = soundPool.load(this, R.raw.move_sound,1);
        winnerSound = soundPool.load(this,R.raw.winner, 2);
        tieSound = soundPool.load(this, R.raw.match_tie,3);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.release();
                soundPool=null;
                recreate();
            }
        });

        et1.setOnClickListener(this);
        et2.setOnClickListener(this);
        et3.setOnClickListener(this);
        et4.setOnClickListener(this);
        et5.setOnClickListener(this);
        et6.setOnClickListener(this);
        et7.setOnClickListener(this);
        et8.setOnClickListener(this);
        et9.setOnClickListener(this);
        p1 = getIntent().getStringExtra("player1");
        p2 = getIntent().getStringExtra("player2");

        String versus=p1+"    V/S    "+p2+"\n\n"+"(X  V/S  O)";
        status.setText(versus);


    }

    @Override
    public void onClick(View v)
    {
        if (!activeGame)
        {
            return;
        }
        img=findViewById(v.getId());
        int tag= Integer.parseInt(v.getTag().toString());

        //Sound Added Whenever user plays.
        soundPool.play(playSound,1,1,0,0,1);

        if (currentPlayer==xplayer)
        {
            img.setImageResource(x);
            img.setEnabled(false);
            positions[tag]=xplayer;
            currentPlayer=oplayer;
            check();
        }else{
            img.setImageResource(o);
            img.setEnabled(false);
            positions[tag]=oplayer;
            currentPlayer=xplayer;
            check();
        }
    }

    public void check()
    {

        int[][] winPos ={{0,1,2},{3,4,5},{6,7,8},{2,4,6},{0,4,8},{0,3,6},{1,4,7},{2,5,8}};

            for (int i=0;i<8;i++)
            {
                int pos1=winPos[i][0];
                int pos2=winPos[i][1];
                int pos3=winPos[i][2];

                if(positions[pos1]==positions[pos2] && positions[pos1]==positions[pos3])
                {
                    if (positions[pos1]!=-1)
                    {

                        activeGame=false;
                        int winnerImageResource = -1;

                        if (positions[pos1]==xplayer)
                        {
                            displayToast(p1+" WON");

                            winnerImageResource = R.drawable.x_win;

                            // Show Name of Winner
                            String versus=p1+"    V/S    "+p2+"\n\n(" + p1+" WON)";
                            status.setText(versus);

                            //Winner Sound will Play
                            soundPool.play(winnerSound,1,1,0,0,1);

                            loadWinnerGif();

                            for (int cellIndex : winPos[i]) {
                                cellImageViews[cellIndex].setImageResource(winnerImageResource);
                            }

                            return;
                        }else{
                            displayToast(p2+" WON");

                            winnerImageResource = R.drawable.o_win;

                            // Show Name of Winner
                            String versus=p1+"    V/S    "+p2+"\n\n(" + p2+" WON)";
                            status.setText(versus);

                            //Winner Sound will Play
                            soundPool.play(winnerSound,1,1,0,0,1);

                            loadWinnerGif();

                            for (int cellIndex : winPos[i]) {
                                cellImageViews[cellIndex].setImageResource(winnerImageResource);
                            }


                            return;
                        }

                    }

                }

            }
            List<Integer> posiList=new ArrayList<>(Arrays.asList(positions));
            if(!posiList.contains(-1))
            {
                displayToast("Match Tie");

                //Match Tie Sound Effect will Play.
                soundPool.play(tieSound,1,1,0,0,1);

                activeGame=false;
            }
    }

    public void displayToast(String txt)
    {
        LayoutInflater inflater=getLayoutInflater();
        View v1=inflater.inflate(R.layout.customtoast,(ViewGroup)findViewById(R.id.customt));
        TextView textView=v1.findViewById(R.id.textView);
        textView.setText(txt);
        ImageView imageView=v1.findViewById(R.id.imageView);
        if (txt == "Match Tie"){
            imageView.setImageResource(R.drawable.go);
            String versus=p1+"    V/S    "+p2+"\n\n(Match TIE)";
            status.setText(versus);
        }

        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(v1);
        toast.show();
    }

    private void loadWinnerGif(){
        Glide.with(GameActivity.this)
                .asGif()
                .load(R.drawable.winner) // Change Winning GIF from here
                .centerCrop()
                .into(imgWinner);
    }

}

