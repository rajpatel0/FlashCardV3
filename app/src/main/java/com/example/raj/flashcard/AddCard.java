package com.example.raj.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                data.putExtra("string1", ((EditText) findViewById(R.id.QuestionBox)).getText().toString());
                data.putExtra("string2", ((EditText) findViewById(R.id.AnswerBox)).getText().toString()); // puts another string into the Intent, with the key as 'string2
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
});

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = getIntent().getStringExtra("stringKey1"); // this string will be 'harry potter`
                String s2 = getIntent().getStringExtra("stringKey2"); // this string will be 'voldemort'
                Intent data = new Intent();
                data.putExtra("string1",s1);
                data.putExtra("string2",s2);
                setResult(RESULT_OK,data);
                finish();

                }
});
}
}