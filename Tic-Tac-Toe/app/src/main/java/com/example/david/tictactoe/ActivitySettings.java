/*
    ActivitySettings.java
    David Wartenbe

    Settings activity of application.
*/

package com.example.david.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;

public class ActivitySettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //WARNING
        //IF THESE RADIO BUTTONS BOTH = radio_intermediate THEN ANDROID STUDIO WILL CRASH
        //WARNING
        RadioButton rbIntermediate = (RadioButton) findViewById(R.id.radio_intermediate);
        RadioButton rbImpossible = (RadioButton) findViewById(R.id.radio_impossible);

        //Get Intent message
        Bundle extra = getIntent().getExtras();
        if (extra == null) return; //Should not be needed but stops app from crashing

        String message = extra.getString(Intent.EXTRA_TEXT);
        String i = "Intermediate";
        String im = "Impossible";
        if (message != null && message.equals(i)) {
            rbIntermediate.setChecked(true);
        } else if (message != null && message.equals(im)) {
            rbImpossible.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Reuse menu from about activity
        getMenuInflater().inflate(R.menu.activity_about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.about_back){
            Intent intent = new Intent(this, ActivityMain.class);
            intent.setAction(Intent.EXTRA_TEXT);

            RadioButton rbIntermediate = (RadioButton) findViewById(R.id.radio_intermediate);

            if (rbIntermediate.isChecked()) {
                intent.putExtra(Intent.EXTRA_TEXT, "Intermediate");
            } else {
                intent.putExtra(Intent.EXTRA_TEXT, "Impossible");
            }

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
