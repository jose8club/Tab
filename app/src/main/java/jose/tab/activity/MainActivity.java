package jose.tab.activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jose.tab.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Toolbar a usar
     */
    private Toolbar toolbar;

    /**
     * Botones de entrada y sailda
     */
    private Button btnIn, btnOut;

    // Establece la duracoin en pantalla del splash screen
    private static final long SPLASH_SCREEN_DELAY = 2000;

    /**
     * Establece errores NFC
     */
    public static final String ERROR_ENABLE = "No esta activada la funcionalidad NFC";
    public static final String ERROR_NFC = "Este dispositivo no soporta NFC";

    /**
     * Adaptador NFC, usado para comprobar si posee NFC el equipo o no
     */
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnIn = (Button) findViewById(R.id.btnIn);
        btnOut = (Button) findViewById(R.id.btnOut);

        /**
         * Establecer click a los botones
         */
        btnIn.setOnClickListener(this);
        btnOut.setOnClickListener(this);

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
    }

    /**
     * Metodos al hacer click a los botones
     * @param view
     */
    @Override
    public void onClick(final View view) {
        view.getBackground().setAlpha(128);
        Runnable clickButton = new Runnable() {
            @Override
            public void run() {
                switch (view.getId()) {
                    case R.id.btnIn:
                        view.getBackground().setAlpha(255);
                        startActivity(new Intent(MainActivity.this, TabsActivity.class));
                        break;
                    case R.id.btnOut:
                        view.getBackground().setAlpha(255);
                        finish();
                        //System.exit(0);
                        break;
                }
            }
        };
        view.postDelayed(clickButton, 80);
    }
}
