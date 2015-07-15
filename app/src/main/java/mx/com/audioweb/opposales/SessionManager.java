package mx.com.audioweb.opposales;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Juan Acosta on 11/27/2014.
 */
public class SessionManager {

    public static final String PREF_NAME = "oppoPref";
    public static final String KEY_CODE = "code";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String FIRST_TIME = "ok";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String code) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_CODE, code);
        editor.commit();
    }
    public void createFirst(){
        editor.putBoolean(FIRST_TIME, true);
        editor.commit();
    }
    public String getCode(){
        String code;
        code = pref.getString(KEY_CODE,null);
        return code;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isRegister() {
        return pref.getBoolean(FIRST_TIME, false);
    }
}
