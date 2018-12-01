package com.example.raj.flashcard;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                View answerSideView = findViewById(R.id.textView2);

                // get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);
                findViewById(R.id.flashCardAnswer).setVisibility(View.INVISIBLE);
                findViewById(R.id.textView2).setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        // set the question and answer TextViews with data from the database
                        ((TextView) findViewById(R.id.textView2)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashCardAnswer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                        findViewById(R.id.flashCardAnswer).setVisibility(View.VISIBLE);
                        findViewById(R.id.textView2).setVisibility(View.INVISIBLE);

                        findViewById(R.id.flashCardAnswer).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                findViewById(R.id.flashCardAnswer).startAnimation(leftOutAnim);







            }
        });
    }
}
