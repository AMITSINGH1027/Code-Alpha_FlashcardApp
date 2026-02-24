package com.example.flashcardapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class AddFlashcardActivity extends AppCompatActivity {

    boolean editMode = false;
    String oldQuestion = "";

    EditText editQuestion, editAnswer;

    Button saveBtn;
    DBHelper db;

    Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        editQuestion = findViewById(R.id.editQuestion);
        editAnswer = findViewById(R.id.editAnswer);
        saveBtn = findViewById(R.id.btnSave);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        db = new DBHelper(this);

        // Category List
        String[] categories = {"Programming", "GK", "Maths", "Interview"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        spinnerCategory.setAdapter(adapter);

        // Edit Mode Check
        if(getIntent().hasExtra("editMode")){
            editMode = true;

            oldQuestion = getIntent().getStringExtra("question");
            String ans = getIntent().getStringExtra("answer");
            String cat = getIntent().getStringExtra("category");

            editQuestion.setText(oldQuestion);
            editAnswer.setText(ans);

            // Spinner selection set karna
            for(int i = 0; i < categories.length; i++){
                if(categories[i].equals(cat)){
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        }

        saveBtn.setOnClickListener(v -> {

            String q = editQuestion.getText().toString();
            String a = editAnswer.getText().toString();
            String c = spinnerCategory.getSelectedItem().toString();

            if(editMode){
                db.updateCard(oldQuestion, q, a, c);
            } else {
                db.insertCard(q, a, c);
            }

            finish();
        });
    }
}
