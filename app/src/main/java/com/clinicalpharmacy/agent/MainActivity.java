package com.clinicalpharmacy.agent;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 35, 25, 25);
        layout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("💊 Clinical Pharmacy AI");
        title.setTextSize(27);
        title.setTextColor(Color.BLUE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(5, 20, 5, 30);
        layout.addView(title);

        addButton("DRUG INFORMATION", 1);
        addButton("DRUG INTERACTIONS", 2);
        addButton("DOSE CALCULATOR", 3);
        addButton("CLINICAL GUIDELINES", 4);
        addButton("AI PHARMACY ASSISTANT", 5);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    void addButton(String name, int number) {
        Button button = new Button(this);
        button.setText(name);
        button.setTextSize(17);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                -1, -2
            );

        params.setMargins(0, 10, 0, 10);
        layout.addView(button, params);

        button.setOnClickListener(v -> openSection(number));
    }

    void openSection(int number) {

        EditText input = new EditText(this);
        input.setHint("Enter information here");

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(20, 10, 20, 10);

        box.addView(input);

        String title = "";

        switch (number) {

            case 1:
                title = "Drug Information";
                input.setHint("Enter drug name");
                break;

            case 2:
                title = "Drug Interactions";
                input.setHint("Enter two drug names");
                break;

            case 3:
                title = "Dose Calculator";
                input.setHint("Enter weight in kg");
                break;

            case 4:
                title = "Clinical Guidelines";
                input.setHint("Enter disease name");
                break;

            case 5:
                title = "AI Pharmacy Assistant";
                input.setHint("Ask your clinical question");
                break;
        }

        new AlertDialog.Builder(this)
            .setTitle(title)
            .setView(box)
            .setPositiveButton("Continue", (dialog, which) -> {

                String question = input.getText().toString();

                new AlertDialog.Builder(this)
                    .setTitle("Clinical Pharmacy AI")
                    .setMessage(
                        "Your input:\n" + question +
                        "\n\nThis is a starter version. " +
                        "Connect a verified clinical database " +
                        "to obtain reliable medical answers."
                    )
                    .setPositiveButton("OK", null)
                    .show();

            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}
