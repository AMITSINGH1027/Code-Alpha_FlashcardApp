package com.example.flashcardapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView questionText, answerText, textCategory;
    Button showBtn, nextBtn, prevBtn, addBtn, deleteBtn, editBtn;

    ArrayList<Flashcard> list;
    DBHelper db;
    int index = 0;
    boolean answerVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.textQuestion);
        answerText = findViewById(R.id.textAnswer);
        showBtn = findViewById(R.id.btnShowAnswer);
        nextBtn = findViewById(R.id.btnNext);
        prevBtn = findViewById(R.id.btnPrev);
        addBtn  = findViewById(R.id.btnAdd);
        deleteBtn = findViewById(R.id.btnDelete);
        editBtn = findViewById(R.id.btnEdit);
        textCategory = findViewById(R.id.textCategory);

        // Database start
        db = new DBHelper(this);
        list = db.getAllCards();

        if(list.size() > 0){
            showCard();
        }

        showBtn.setOnClickListener(v -> {
            if(!answerVisible){
                answerText.setText(list.get(index).answer);
                answerVisible = true;
            } else {
                answerText.setText("");
                answerVisible = false;
            }
        });

        nextBtn.setOnClickListener(v -> {
            if(index < list.size()-1){
                index++;
                showCard();
            }
        });

        prevBtn.setOnClickListener(v -> {
            if(index > 0){
                index--;
                showCard();
            }
        });

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddFlashcardActivity.class);
            startActivity(intent);
        });

        deleteBtn.setOnClickListener(v -> {

            if(list.size() > 0) {
                String q = list.get(index).question;

                db.deleteCard(q);

                list = db.getAllCards();

                if(list.size() > 0){
                    index = 0;
                    showCard();
                } else {
                    questionText.setText("No Flashcards");
                    answerText.setText("");
                }
            }
        });

        editBtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, AddFlashcardActivity.class);

            intent.putExtra("editMode", true);
            intent.putExtra("question", list.get(index).question);
            intent.putExtra("answer", list.get(index).answer);
            intent.putExtra("category", list.get(index).category);

            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data when coming back from Add screen
        list = db.getAllCards();
        if(list.size() > 0){
            index = list.size() - 1;
            showCard();
        }
    }

    void showCard(){
        questionText.setText(list.get(index).question);
        textCategory.setText("Category: " + list.get(index).category);
        answerText.setText("");
        answerVisible = false;
    }
}
