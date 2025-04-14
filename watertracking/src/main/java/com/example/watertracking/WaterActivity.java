package com.example.watertracking;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
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

public class WaterActivity extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private LottieAnimationView lottieAnimationView;
    private TextView descriptionTextView;
    private TextView mainTextView;
    private Button mainButton;
    private Button resetButton;
    private TextView achievementsTextView;
    private ProgressBar waterProgressBar;

    private int waterGlasses;
    private final int DAILY_GOAL = 12; // יעד יומי: 12 כוסות

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
        waterProgressBar = findViewById(R.id.waterProgressBar);

        setupUI();
    }

    private void setupUI() {
        rootLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_background));
        lottieAnimationView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));

        descriptionTextView.setText("Glasses of Water Today 💧");
        descriptionTextView.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        mainButton.setText("➕ Add Glass");
        mainButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_dark));
        mainButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        resetButton.setText("🔄 Reset Counter");
        resetButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_dark));
        resetButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        resetButton.setTextSize(14);

        achievementsTextView.setText("Achievements:");
        achievementsTextView.setTextColor(ContextCompat.getColor(this, android.R.color.black));

        // Progress Bar עיצוב
        waterProgressBar.setMax(DAILY_GOAL);
        waterProgressBar.setProgress(0);
        waterProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_dark));
        waterProgressBar.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));

        waterGlasses = PreferencesHelper.getInt(this, BaseConstants.PREF_WATER_GLASSES, 0);
        updateUI();

        mainButton.setOnClickListener(v -> addWaterGlass());
        resetButton.setOnClickListener(v -> resetCounter());
    }

    private void addWaterGlass() {
        waterGlasses++;
        PreferencesHelper.saveInt(this, BaseConstants.PREF_WATER_GLASSES, waterGlasses);
        updateUI();
    }

    private void resetCounter() {
        waterGlasses = 0;
        PreferencesHelper.saveInt(this, BaseConstants.PREF_WATER_GLASSES, waterGlasses);
        achievementsTextView.setText("Achievements:");
        animateProgress(0);
        updateUI();
    }

    private void updateUI() {
        mainTextView.setText(String.valueOf(waterGlasses));

        if (waterGlasses <= DAILY_GOAL) {
            animateProgress(waterGlasses);
        } else {
            animateProgress(DAILY_GOAL);
        }

        updateProgressBarColor(waterGlasses);
        checkAchievements(waterGlasses);
    }

    private void animateProgress(int newProgress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(waterProgressBar, "progress", waterProgressBar.getProgress(), newProgress);
        animation.setDuration(500); // חצי שנייה
        animation.start();
    }

    private void updateProgressBarColor(int glasses) {
        if (glasses < 5) {
            waterProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_light));
        } else if (glasses < 8) {
            waterProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark));
        } else {
            waterProgressBar.setProgressTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_dark)); // אפשר גם צבע כהה עוד יותר אם תרצה
        }
    }

    private void checkAchievements(int glasses) {
        if (glasses == 5 || glasses == 8 || glasses == 12) {
            lottieAnimationView.setAnimation(R.raw.water_animation);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();

            mainButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
            mainTextView.setVisibility(View.GONE);
            achievementsTextView.setVisibility(View.GONE);
            waterProgressBar.setVisibility(View.GONE);

            new Handler().postDelayed(() -> {
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);

                mainButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
                descriptionTextView.setVisibility(View.VISIBLE);
                mainTextView.setVisibility(View.VISIBLE);
                achievementsTextView.setVisibility(View.VISIBLE);
                waterProgressBar.setVisibility(View.VISIBLE);

                updateAchievements(glasses);
            }, 2000);
        }
    }

    private void updateAchievements(int glasses) {
        String existingText = achievementsTextView.getText().toString();
        String newAchievement = "";

        switch (glasses) {
            case 5:
                newAchievement = "Reached 5 glasses! 🥇";
                break;
            case 8:
                newAchievement = "Daily goal achieved! 🏆";
                break;
            case 12:
                newAchievement = "Wow! 12 glasses today! 💦";
                break;
        }

        achievementsTextView.setText(existingText + "\n" + newAchievement);
    }
}