package mx.com.audioweb.opposales;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;


public class Register extends Activity {

    private static final String TAG = "Transmicion en vivo";
    String n,ln,add,cit,st,z,p,m;
    EditText name,lastname,address,city,state,zip,phone,mail;
    Button register;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.editTextName);
        lastname = (EditText) findViewById(R.id.editTextLastName);
        address = (EditText) findViewById(R.id.editTextAddress);
        city = (EditText) findViewById(R.id.editTextCity);
        state = (EditText) findViewById(R.id.edutTextState);
        zip = (EditText) findViewById(R.id.editTextZipCode);
        phone = (EditText) findViewById(R.id.editTextPhone);
        mail = (EditText) findViewById(R.id.editTextMail);
        register = (Button) findViewById(R.id.buttonRegistro);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n =name.getText().toString();
                ln = lastname.getText().toString();
                add = address.getText().toString();
                cit = city.getText().toString();
                st = state.getText().toString();
                z=zip.getText().toString();
                p=phone.getText().toString();
                m = mail.getText().toString();

                if(n.equals("")||ln.equals("")||add.equals("")||cit.equals("")||st.equals("")||z.equals("")||p.equals("")||m.equals("")){
                    Toast.makeText(getApplicationContext(),"Algun campo esta vacio",Toast.LENGTH_SHORT).show();
                }
                else{
                    new RegisterTask(getApplicationContext(),n,ln,add,cit,st,z,p,m,register).execute();
                }
            }
        });
    }

    class RegisterTask extends AsyncTask<String, Void, Boolean> {


        private Context context;
        private String message;
        private String nombre;
        private String apellido;
        private String direccion;
        private String ciudad;
        private String estado;
        private String cp;
        private String celular;
        private String email;
        private Button registerButton;

        public RegisterTask(Context ctx, String nombre,String apellido,String direccion,String ciudad,String estado,String cp,String celular,String email, Button registerButton) {
            this.context = ctx;
            this.nombre =nombre;
            this.apellido = apellido;
            this.direccion = direccion;
            this.ciudad = ciudad;
            this.estado = estado;
            this.cp = cp;
            this.celular = celular;
            this.email= email;
            this.registerButton = registerButton;
        }


        @Override
        protected Boolean doInBackground(String... params) {
            Log.d("LoginTask", "Entra a doInBack..");
            JSONObject jsonObject;
            try {
                Log.d("LoginTask", "Entra a doInBack..TRY");
                jsonObject = ClienteHttp.Register(nombre,apellido,direccion,ciudad, estado,cp,celular,email);

                String value = String.valueOf(jsonObject);
                Log.e("JSON  ", value);
                if (value != null) {
                    String error = String.valueOf(jsonObject.get("status"));
                    if (error == "succes") {

                        return true;
                    }
                    return false;
                } else {
                    this.message = "Error Registro";
                    Log.d("LoginTask", this.message);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                this.message = "Error Raro";
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                this.message = "Error Inesperado";
                return true;
            }
        }


        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("RegistroTask", "Entra a onPostExecute..");
            this.registerButton.setEnabled(true);
            if (!result) {

                Toast.makeText(this.context, "Se Registro Exitosamente!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

            } else {
                Toast.makeText(this.context, this.message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
