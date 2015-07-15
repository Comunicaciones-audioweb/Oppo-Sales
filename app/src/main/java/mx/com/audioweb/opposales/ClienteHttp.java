package mx.com.audioweb.opposales;

/**
 * Created by Juan Acosta on 10/22/2014.
 */

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Acosta on 8/11/2014.
 */
public class ClienteHttp {

    public static final String URL = "Insertar URL Aqui";
    private static final String DATEF = "yyyy-MM-dd HH:mm:ss";
    private Gson gson = new GsonBuilder().setDateFormat(DATEF).create();



    public static String inicia_sesion(String code, String phone) {
        BufferedReader bufferedReader = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(URL + "doValidate.php");
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("code", code));
        postParameters.add(new BasicNameValuePair("phone", phone));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                    postParameters);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            bufferedReader = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            bufferedReader.close();

            //JSONArray jsonArray = new JSONArray(stringBuffer.toString());
            String cita_id = new String(stringBuffer.toString());

            return cita_id;

        } catch (ClientProtocolException e) {

            e.printStackTrace();
            Log.d("ClientProtocolException", e.toString());

        } catch (IOException e) {

            e.printStackTrace();

            Log.d("Exception", e.toString());

        } finally {
            if (bufferedReader != null) {
                try {

                    bufferedReader.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }
        return null;
    }

    public static  JSONObject Register(String nombre,String apellido,String direccion,String ciudad,String estado,String cp,String celular,String email)
            throws JSONException {
        BufferedReader bufferedReader = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://www.oppomobility.mx/doRegister.php");
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("nombre", nombre));
        postParameters.add(new BasicNameValuePair("apellido", apellido));
        postParameters.add(new BasicNameValuePair("direccion", direccion));
        postParameters.add(new BasicNameValuePair("ciudad", ciudad));
        postParameters.add(new BasicNameValuePair("estado", estado));
        postParameters.add(new BasicNameValuePair("cp", cp));
        postParameters.add(new BasicNameValuePair("celular", celular));
        postParameters.add(new BasicNameValuePair("email", email));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                    postParameters);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            bufferedReader = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            bufferedReader.close();

            JSONObject jsonObj = new JSONObject(stringBuffer.toString());

            return jsonObj;

        } catch (ClientProtocolException e) {

            e.printStackTrace();
            Log.d("ClientProtocolException", e.toString());

        } catch (IOException e) {

            e.printStackTrace();
			/*
			 * //Toast.makeText(Login.class, e.toString(), //
			 * Toast.LENGTH_LONG).show();
			 */
            Log.d("Exception", e.toString());

        } finally {
            if (bufferedReader != null) {
                try {

                    bufferedReader.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }
        return null;
    }
}
