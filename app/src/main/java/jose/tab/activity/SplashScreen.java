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

    // Establece la duracion en pantalla del splash screen
    private static final long DURACION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establercer orientacion de la imagen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Ocultar barra de titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {

                // Comenzar la actividad que le sigue al splash screen
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this, TabsActivity.class);
                startActivity(mainIntent);

                // Cierra la actividad para que el usuario no pueda volver atrás
                // presionando Back button
                finish();
            }
        };

        // Simula un proceso de carga en el inicio de la aplicación.
        Timer cronometro = new Timer();
        cronometro.schedule(tarea, DURACION);

    }

}
