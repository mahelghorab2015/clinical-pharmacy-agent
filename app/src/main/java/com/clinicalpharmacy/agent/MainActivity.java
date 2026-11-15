package com.clinicalpharmacy.agent;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.Locale;

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

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 10, 0, 10);
        layout.addView(button, params);

        button.setOnClickListener(v -> openSection(number));
    }

    void openSection(int number) {

        if (number == 2) {
            openInteractionChecker();
            return;
        }

        EditText input = new EditText(this);

        input.setHint(number == 1 ? "Enter drug name" :
            number == 3 ? "Enter weight in kg" :
            number == 4 ? "Enter disease name" :
            "Ask your clinical question");

        String title = number == 1 ? "Drug Information" :
            number == 3 ? "Dose Calculator" :
            number == 4 ? "Clinical Guidelines" :
            "AI Pharmacy Assistant";

        new AlertDialog.Builder(this)
            .setTitle(title)
            .setView(input)
            .setPositiveButton("Continue", (dialog, which) -> {
                new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage("This section is not connected to a verified clinical database yet.")
                    .setPositiveButton("OK", null)
                    .show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    void openInteractionChecker() {

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(20, 10, 20, 10);

        EditText drug1 = new EditText(this);
        drug1.setSingleLine(true);
        drug1.setHint("First drug (e.g. aspirin)");
        box.addView(drug1);

        EditText drug2 = new EditText(this);
        drug2.setSingleLine(true);
        drug2.setHint("Second drug (e.g. Plavix)");
        box.addView(drug2);

        new AlertDialog.Builder(this)
            .setTitle("Drug Interaction Checker")
            .setView(box)
            .setPositiveButton("Check", (dialog, which) -> {

                String result = checkInteraction(
                    drug1.getText().toString(),
                    drug2.getText().toString()
                );

                new AlertDialog.Builder(this)
                    .setTitle("Interaction Result")
                    .setMessage(result)
                    .setPositiveButton("OK", null)
                    .show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    String normalize(String value) {

        String s = value.toLowerCase(Locale.ROOT).trim();

        if (s.equals("plavix")) return "clopidogrel";
        if (s.equals("brilinta")) return "ticagrelor";
        if (s.equals("coumadin")) return "warfarin";

        if (s.equals("advil") || s.equals("brufen")
            || s.equals("nurofen")) return "ibuprofen";

        if (s.equals("nexium")) return "esomeprazole";

        if (s.equals("losec") || s.equals("prilosec"))
            return "omeprazole";

        return s;
    }

    boolean pair(String a, String b, String x, String y) {
        return (a.equals(x) && b.equals(y)) ||
               (a.equals(y) && b.equals(x));
    }

    String checkInteraction(String first, String second) {

        String a = normalize(first);
        String b = normalize(second);

        if (a.isEmpty() || b.isEmpty()) {
            return "Please enter both drug names.";
        }

        if (a.equals(b)) {
            return "You entered the same drug twice.";
        }

        if (pair(a, b, "aspirin", "clopidogrel")) {
            return "IMPORTANT INTERACTION\n\n" +
                "Increased bleeding risk.\n\n" +
                "Both drugs inhibit platelet function.\n\n" +
                "Clinical action:\n" +
                "Assess bleeding risk and confirm indication and duration.\n\n" +
                "Do not stop either medicine without medical advice.";
        }

        if (pair(a, b, "clopidogrel", "warfarin")) {
            return "HIGH BLEEDING RISK\n\n" +
                "Both medicines increase bleeding risk.\n\n" +
                "Clinical action:\n" +
                "Review indication and monitor closely.\n\n" +
                "Prescriber supervision is required.";
        }

        if (pair(a, b, "clopidogrel", "ibuprofen") ||
            pair(a, b, "clopidogrel", "naproxen") ||
            pair(a, b, "clopidogrel", "diclofenac")) {

            return "INCREASED BLEEDING RISK\n\n" +
                "NSAIDs combined with clopidogrel may increase gastrointestinal bleeding.\n\n" +
                "Clinical action:\n" +
                "Review alternative pain management with a pharmacist or prescriber.";
        }

        if (pair(a, b, "clopidogrel", "omeprazole") ||
            pair(a, b, "clopidogrel", "esomeprazole")) {

            return "POTENTIAL REDUCED CLOPIDOGREL EFFECT\n\n" +
                "These drugs may reduce activation of clopidogrel through CYP2C19 inhibition.\n\n" +
                "Clinical action:\n" +
                "Ask the prescriber to review gastroprotection options.";
        }

        return "NO MATCH FOUND\n\n" +
            "This does NOT mean the combination is safe.\n\n" +
            "This is a limited demonstration database.\n" +
            "Check a verified drug interaction database.";
    }
}
