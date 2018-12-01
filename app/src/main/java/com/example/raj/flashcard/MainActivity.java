package com.example.raj.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    // Allows us to access the database throughout the code
    FlashcardDatabase flashcardDatabase;
    //holds all flashcard objects
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;

    //Takes the data from AddCard and places it in the main code.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 ) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String answer = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String question = data.getExtras().getString("string2");
            ((TextView) findViewById(R.id.flashCardAnswer)).setText(answer);
            ((TextView) findViewById(R.id.textView2)).setText(question);
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();
        }

        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.flashCardAnswer).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.flashCardAnswer)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.textView2)).setVisibility(View.VISIBLE);
                }
                else {
                        ((TextView) findViewById(R.id.flashCardAnswer)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.textView2)).setVisibility(View.INVISIBLE);
                    }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        //This allows the user to add cards of their own choice.
        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCard.class);
                intent.putExtra("stringKey1", ((TextView) findViewById(R.id.flashCardAnswer)).getText().toString());
                intent.putExtra("stringKey2", ((TextView) findViewById(R.id.textView2)).getText().toString());
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
        // This code changes the question to the answer on click.
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.textView2)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashCardAnswer)).setText(allFlashcards.get(0).getAnswer());
        }
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.flashCardAnswer).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.flashCardAnswer)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.textView2)).setVisibility(View.VISIBLE);
                }
                else {
                        ((TextView) findViewById(R.id.flashCardAnswer)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.textView2)).setVisibility(View.INVISIBLE);
                    }
            }
        });
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashCardAnswer)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.textView2)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                //  makes it so the question is the one being shown
                if (findViewById(R.id.flashCardAnswer).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.flashCardAnswer)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.textView2)).setVisibility(View.VISIBLE);
                }



            }
        });
    }
}
