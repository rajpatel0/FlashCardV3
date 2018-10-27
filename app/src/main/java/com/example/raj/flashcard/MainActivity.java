package com.example.raj.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Takes the data from AddCard and places it in the main code.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && requestCode == RESULT_CANCELED) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("string2");
            ((TextView) findViewById(R.id.flashCardAnswer)).setText(string1);
            ((TextView) findViewById(R.id.textView2)).setText(string2);
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

        //This allows the user to add cards of their own choice.
        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCard.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
        // This code changes the question to the answer on click.
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
}
