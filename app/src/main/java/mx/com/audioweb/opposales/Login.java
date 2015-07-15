package mx.com.audioweb.opposales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {
    SessionManager session;
    Button codigo;
    EditText numcode;
    View focusView;
    boolean cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        codigo = (Button) findViewById(R.id.button4);
        numcode = (EditText) findViewById(R.id.editCode);
        //load the preferences
        codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numcode.setError(null);
                cancel = false;
                focusView = null;
                String coden = numcode.getText().toString();
                Log.d("CODE NUMBER", coden);
                if (TextUtils.isEmpty(coden)) {
                    numcode.setError("Campo Necesario");
                    focusView = numcode;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String phoneNumber = telemamanger.getLine1Number();
                    Log.e("Phone", phoneNumber);

                    new IniciaSesionTask(getApplicationContext(), coden, phoneNumber).execute();
                }
            }
        });
    }

    public class IniciaSesionTask extends AsyncTask<String, Void, Boolean> {

        private Context context;
        private String codigo;
        private String phone;

        public IniciaSesionTask(Context ctx, String codigo, String phone) {
            this.context = ctx;
            this.codigo = codigo;
            this.phone = phone;
        }


        @Override
        protected Boolean doInBackground(String... params) {
            Log.d("LoginTask", "Entra a doInBack..");
            String cita_id;
            try {
                cita_id = ClienteHttp.inicia_sesion(this.codigo, this.phone);
                String id;
                Log.e("Try  ", cita_id);
                if (cita_id != null) {
                    id = cita_id;
                    Log.d("JSON ", id);
                    if (id.equals("1")) {
                        return false;
                    } else {
                        Log.d("Task", "No Registro");
                        return true;
                    }

                } else {
                    //this.message = "Error Login";
                    Log.d("LoginTask", "ErrorLogin");
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                //this.message = "Error Inesperado";
                return true;
            }
        }


        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("LoginTask", "Entra a onPostExecute..");
            if (!result) {
                SessionManager create = new SessionManager(getApplicationContext());
                create.createLoginSession(codigo);
                Toast.makeText(this.context, "Inicio Sesion", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this.context, Home.class));
                finish();
            }
            else{
                numcode.setError("Codigo Invalido");
                focusView = numcode;
                cancel = true;
            }

        }

    }
}
