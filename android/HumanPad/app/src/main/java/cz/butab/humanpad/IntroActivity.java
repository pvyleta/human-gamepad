package cz.butab.humanpad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private ImageView smile;
    private ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        smile = (ImageView) findViewById(R.id.smile);
        smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent myIntent = new Intent(IntroActivity.this,MainActivity.class);
                IntroActivity.this.startActivity(myIntent);
            }
        });

        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent myIntent = new Intent(IntroActivity.this,SettingsActivity.class);
                IntroActivity.this.startActivity(myIntent);
            }
        });
    }
}
