package mx.com.audioweb.opposales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import mx.com.audioweb.opposales.AudioConference.AudioConferencia;
import mx.com.audioweb.opposales.LiveStreaming.LiveStreaming;


public class Home extends Activity {

    Button ls,ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ac = (Button) findViewById(R.id.button);
        ls = (Button) findViewById(R.id.button2);

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AudioConferencia.class));
            }
        });

        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LiveStreaming.class));
            }
        });
    }


}
