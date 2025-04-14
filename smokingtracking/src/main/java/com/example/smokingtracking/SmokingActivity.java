package com.example.smokingtracking;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.common.PreferencesHelper;
import com.example.common.BaseConstants;
import com.example.common.R;

public class SmokingActivity extends AppCompatActivity { //update

    private ConstraintLayout rootLayout;
    private LottieAnimationView lottieAnimationView;
    private TextView descriptionTextView;
    private TextView mainTextView;
    private Button mainButton;
    private Button resetButton;
    private TextView achievementsTextView;
    private ProgressBar smokeProgressBar;

    private int smokeFreeDays;
    private final int TARGET_DAYS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        mainTextView = findViewById(R.id.mainTextView);
        mainButton = findViewById(R.id.mainButton);
        resetButton = findViewById(R.id.resetButton);
        achievementsTextView = findViewById(R.id.achievementsTextView);
        smokeProgressBar = findViewById(R.id.waterProgressBar);

        setupUI();
    }

    private void setupUI() {
        rootLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        lottieAnimationView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));

        descriptionTextView.setText("Smoke-Free Days 🚭");
        descriptionTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        mainButton.setText("❌ No Smoking Today");
        mainButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        mainButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        resetButton.setText("🔄 Smoked - Reset");
        resetButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.black));
        resetButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        resetButton.setTextSize(14);

        achievementsTextView.setText("Achievements:");
        achievementsTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        smokeProgressBar.setMax(TARGET_DAYS);
        smokeProgressBar.setProgress(0);
        smokeProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_light));
        smokeProgressBar.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));

        smokeFreeDays = PreferencesHelper.getInt(this, BaseConstants.PREF_SMOKE_FREE_DAYS, 0);
        updateUI();

        mainButton.setOnClickListener(v -> addSmokeFreeDay());
        resetButton.setOnClickListener(v -> resetCounter());
    }

    private void addSmokeFreeDay() {
        smokeFreeDays++;
        PreferencesHelper.saveInt(this, BaseConstants.PREF_SMOKE_FREE_DAYS, smokeFreeDays);
        updateUI();
    }

    private void resetCounter() {
        smokeFreeDays = 0;
        PreferencesHelper.saveInt(this, BaseConstants.PREF_SMOKE_FREE_DAYS, smokeFreeDays);
        achievementsTextView.setText("Achievements:");
        animateProgress(0);
        updateUI();
    }

    private void updateUI() {
        mainTextView.setText(String.valueOf(smokeFreeDays));

        if (smokeFreeDays <= TARGET_DAYS) {
            animateProgress(smokeFreeDays);
        } else {
            animateProgress(TARGET_DAYS);
        }

        updateProgressBarColor(smokeFreeDays);
        checkAchievements(smokeFreeDays);
    }

    private void animateProgress(int newProgress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(smokeProgressBar, "progress", smokeProgressBar.getProgress(), newProgress);
        animation.setDuration(500);
        animation.start();
    }

    private void updateProgressBarColor(int days) {
        if (days < 5) {
            smokeProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_light));
        } else if (days < 10) {
            smokeProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_orange_light));
        } else if (days < 30) {
            smokeProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_orange_dark));
        } else {
            smokeProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_light));
        }
    }

    private void checkAchievements(int days) {
        if (days == 1 || days == 5 || days == 10 || days == 30) {
            lottieAnimationView.setAnimation(R.raw.cigarette);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();

            mainButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
            mainTextView.setVisibility(View.GONE);
            achievementsTextView.setVisibility(View.GONE);
            smokeProgressBar.setVisibility(View.GONE);

            // הפעלת רטט
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(300); // רטט של 300ms
            }

            new Handler().postDelayed(() -> {
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);

                mainButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                mainTextView.setVisibility(View.VISIBLE);
                achievementsTextView.setVisibility(View.VISIBLE);
                smokeProgressBar.setVisibility(View.VISIBLE);

                updateAchievements(days);
            }, 2000);
        }
    }

    private void updateAchievements(int days) {
        String existingText = achievementsTextView.getText().toString();
        String newAchievement = "";

        switch (days) {
            case 1:
                newAchievement = "Good Start 🚭";
                break;
            case 5:
                newAchievement = "First Victory 🏆";
                break;
            case 10:
                newAchievement = "Strong Fighter 🥇";
                break;
            case 30:
                newAchievement = "Smoke Free Champion 👑";
                break;
        }

        achievementsTextView.setText(existingText + "\n" + newAchievement);
    }
}