package com.example.clicker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "clicker_prefs";
    private long coins = 0;
    private int perClick = 1;
    private int upgradePrice = 10;

    private TextView coinsText;
    private TextView statsText;
    private Button clickBtn, upgradeBtn, resetBtn;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        coins = prefs.getLong("coins", 0);
        perClick = prefs.getInt("perClick", 1);
        upgradePrice = prefs.getInt("upgradePrice", 10);

        coinsText = findViewById(R.id.coinsText);
        statsText = findViewById(R.id.statsText);
        clickBtn = findViewById(R.id.clickBtn);
        upgradeBtn = findViewById(R.id.upgradeBtn);
        resetBtn = findViewById(R.id.resetBtn);

        clickBtn.setOnClickListener(v -> {
            coins += perClick;
            updateUI();
        });

        upgradeBtn.setOnClickListener(v -> {
            if (coins >= upgradePrice) {
                coins -= upgradePrice;
                perClick++;
                upgradePrice = (int) Math.round(upgradePrice * 1.5);
                updateUI();
            }
        });

        resetBtn.setOnClickListener(v -> {
            coins = 0;
            perClick = 1;
            upgradePrice = 10;
            updateUI();
        });

        updateUI();
    }

    private void updateUI() {
        coinsText.setText("Монет: " + coins);
        statsText.setText("За клик: " + perClick + "\nУлучшение: " + upgradePrice);
        upgradeBtn.setEnabled(coins >= upgradePrice);
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.edit()
                .putLong("coins", coins)
                .putInt("perClick", perClick)
                .putInt("upgradePrice", upgradePrice)
                .apply();
    }
}
