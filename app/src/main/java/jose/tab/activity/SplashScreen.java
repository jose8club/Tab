package jose.tab.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import jose.tab.R;

public class SplashScreen extends Activity {

    // Establece la duracoin en pantalla del splash screen
    private static final long SPLASH_SCREEN_DELAY = 2000;

    /**
     * Establece errores NFC
     */
    //public static final String ERROR_ENABLE = "No esta activada la funcionalidad NFC";
    //public static final String ERROR_NFC = "Este dispositivo no soporta NFC";

    /**
     * Adaptador NFC, usado para comprobar si posee NFC el equipo o no
     */
    //NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establercer orientacion de la imagen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Ocultar barra de titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Comenzar la actividad que le sigue al splash screen
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);

                // Cierra la actividad para que el usuario no pueda volver atrás
                // presionando Back button
                finish();
            }
        };

        // Simula un proceso de carga en el inicio de la aplicación.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

        /*
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null){
            //detecta si el dispositivo tiene o no NFC
            Toast.makeText(this, ERROR_NFC, Toast.LENGTH_LONG).show();
            finish();
        }
        if(!nfcAdapter.isEnabled()){
            //detecta si el dispositivo tiene activado su NFC
            Toast.makeText(this,ERROR_ENABLE, Toast.LENGTH_LONG).show();
            finish();
        }
        */
    }

}
