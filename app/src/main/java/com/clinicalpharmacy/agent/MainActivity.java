package com.clinicalpharmacy.agent;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(25, 30, 25, 25);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("💊 Clinical Pharmacy AI");
        title.setTextSize(27);
        title.setTextColor(Color.BLUE);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        addButton(layout, "Drug Information");
        addButton(layout, "Drug Interactions");
        addButton(layout, "Dose Calculator");
        addButton(layout, "Clinical Guidelines");
        addButton(layout, "AI Pharmacy Assistant");

        setContentView(layout);
    }

    void addButton(LinearLayout layout, String name) {
        Button button = new Button(this);
        button.setText(name);
        layout.addView(button);
    }
}
