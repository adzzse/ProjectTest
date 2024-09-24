package com.example.projecttest;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView progressView;
    private TextView seekbarStatusView;
    private SeekBar seekbarView;
    private Button updateButton;

    private static final int MIN = 10;
    private static final int MAX = 160;
    private static final int STEP_MAX = 5;

    private Handler handler = new Handler();
    private Runnable runnable;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = findViewById(R.id.progress);
        seekbarStatusView = findViewById(R.id.seekbarStatus);
        seekbarView = findViewById(R.id.seekbar);
        updateButton = findViewById(R.id.buttonrun);

        seekbarView.setMax((MAX - MIN) / STEP_MAX);
        seekbarView.setOnSeekBarChangeListener(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
            }
        });
    }

    private void startProgress() {
        final int maxProgress = seekbarView.getMax();
        runnable = new Runnable() {
            int currentProgress = seekbarView.getProgress();

            @Override
            public void run() {
                if (currentProgress < maxProgress) {
                    int step = random.nextInt(STEP_MAX + 1);
                        currentProgress = currentProgress + step;
                    seekbarView.setProgress(currentProgress);
                    onProgressChanged(seekbarView, currentProgress, true);
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.post(runnable); // Start the runnable
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int progressCustom = MIN + (progress * STEP_MAX);
        progressView.setText(String.valueOf(progressCustom));
        seekbarStatusView.setText("Tracking Touch");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        seekbarStatusView.setText("Started Tracking Touch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekbarStatusView.setText("Stopped Tracking Touch");
    }
}
