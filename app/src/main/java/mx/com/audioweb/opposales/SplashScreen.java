package mx.com.audioweb.opposales;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity {
    private SessionManager session;
    private Handler mDrawerHandler;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDrawerHandler = new Handler();
        session = new SessionManager(getApplicationContext());
        Boolean hasRun = session.isRegister();
        if (!hasRun) {

            session.createFirst();

            mDrawerHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        /*String url = "http://oppomobility.mx/register.php";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        finish();*/
                        startActivity(new Intent(getApplicationContext(), Register.class));
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }, 600);
        } else if (session.isLoggedIn()) {
            mDrawerHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {

                        startActivity(new Intent(getApplicationContext(), Home.class));
                        Thread.sleep(1000);
                        finish();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }, 2000);

        } else {
            mDrawerHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }, 1000);
        }
        // new PrefetchData().execute();
    }
}
