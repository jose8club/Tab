package jose.tab.activity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jose.tab.fragments.LocalFragment;
import jose.tab.fragments.NfcFragment;
import jose.tab.fragments.WebFragment;
import jose.tab.R;
import jose.tab.service.BD;



public class TabsActivity extends AppCompatActivity {

    /**
     * Toolbar de la ventana
     */
    private Toolbar toolbar;

    /**
     * Layout de la tabla
     */
    public TabLayout tabLayout;

    /**
     * Vista de la pestania
     */
    private ViewPager viewPager;

    /**
     * Iconos que iran en cada pestania
     */
    private int[] tabIcons = {
            R.drawable.ic_nfc,
            R.drawable.ic_local,
            R.drawable.ic_web
    };

    /**
     * Establece errores NFC
     */
    public static final String ERROR_ENABLE = "No esta activada la funcionalidad NFC";
    public static final String ERROR_NFC = "Este dispositivo no soporta NFC";
    public static final String ERROR_MSGS = "Mensajes de NFC no encontrados";
    public static final String ERROR_LECT = "No se puede leer NFC";

    /**
     * Adaptador NFC, usado para comprobar si posee NFC el equipo o no
     */
    NfcAdapter nfcAdapter;

    /**
     * Numero serie NFC, sirve como PK de las bases de datos y el string de resultado
     */
    public static String serie;


    /**
     * Uso de base de datos SQLite
     */
    BD db;

    /**
     * Cadena de string que tendra los datos de la obra seleccionada
     */
    String [] fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        //Creacion de base de datos SQLite
        db = new BD(getApplicationContext(),null,null,1);
        //Fin de creacion de base de datos SQLite

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //Opacidad inicial
        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(128);
        tabLayout.getTabAt(2).getIcon().setAlpha(128);

        //Definir opacidad de los iconos
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).getIcon().setAlpha(255);
                        tabLayout.getTabAt(1).getIcon().setAlpha(128);
                        tabLayout.getTabAt(2).getIcon().setAlpha(128);
                        try{
                            cargaDatos();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setAlpha(128);
                        tabLayout.getTabAt(1).getIcon().setAlpha(255);
                        tabLayout.getTabAt(2).getIcon().setAlpha(128);

                        // Carga de los datos SQLite en pantalla
                        cargaSQLite(serie);

                        break;
                    case 2:
                        tabLayout.getTabAt(0).getIcon().setAlpha(128);
                        tabLayout.getTabAt(1).getIcon().setAlpha(128);
                        tabLayout.getTabAt(2).getIcon().setAlpha(255);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Se usa la serie que se obtuvo para buscar la obra de arte desde el numero de serie obtenido
     * desde el Tag NFC
     * @param serie
     */
    private void cargaSQLite(String serie) {
        fila = db.buscar(serie.toString());
        LocalFragment.local_txt_name.setText(fila[1]);
        LocalFragment.local_txt_author.setText(fila[2]);
        LocalFragment.local_txt_creation.setText(fila[3]);
        LocalFragment.local_summary = fila[4];
        LocalFragment.local_txt_type.setText(fila[5]);
        LocalFragment.local_txt_style.setText(fila[6]);
        LocalFragment.local_txt_technique.setText(fila[7]);
        LocalFragment.local_txt_entry.setText(fila[8]);
        LocalFragment.local_txt_nationality.setText(fila[9]);
        LocalFragment.local_txt_dim.setText(fila[10]);
        LocalFragment.local_txt_weight.setText(fila[11]);
        LocalFragment.local_image = fila[12];
    }


    /**
     * Cargar Datos desde data.txt
     */
    private void cargaDatos() throws IOException{
        // Linea que recorrera el archivo
        String linea;
        // Se usa el inputstream para cargar el archivo
        // Y el BufferedReader para la lectura de ese archivo
        InputStream is = this.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // Si el archivo no es nulo se puede leer
        if(is!=null){
            // Mientras el archivo tenga lineas que leerse se continuara leyendo
            while ((linea=reader.readLine())!=null){
                // Uso de SQLite para guardar los datos de cada linea de data.txt como una fila separada
                String mensaje =db.guardar(linea.split(";")[0],
                        linea.split(";")[1],
                        linea.split(";")[2],
                        linea.split(";")[3],
                        linea.split(";")[4],
                        linea.split(";")[5],
                        linea.split(";")[6],
                        linea.split(";")[7],
                        linea.split(";")[8],
                        linea.split(";")[9],
                        linea.split(";")[10],
                        linea.split(";")[11],
                        linea.split(";")[12]);
            }
        }
        is.close();
    }

    /**
     * Resume la actividad manteniendo la consistencia
     */
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(0);
        if(!nfcAdapter.isEnabled()){
            //detecta si el dispositivo tiene activado su NFC
            Toast.makeText(this,ERROR_ENABLE, Toast.LENGTH_LONG).show();
            finish();
        }
        enableForegroundDispatchSystem();
    }

    /**
     * Da pausa a la actividad
     */
    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();
    }

    /**
     * Definicion de la actividad a retomar
     */
    private void enableForegroundDispatchSystem() {
        // definicion de la actividad a retomar
        Intent intent = new Intent(this, TabsActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        // recupera la actividad intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // empareja intent con pendingintent
        IntentFilter[] intentFilters = new IntentFilter[]{};
        // retoma la actividad del nfcadapter
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    /**
     * Deshabilita la actividad del adaptador nfc nfcadapter
     */
    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * Nuevo intento
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            // obtener el numero de serie del tag qu es crucial para el
            // resto del proceso porque actua como PK de las bases de datos
            byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            serie = new String();
            for (int i = 0; i < tagId.length; i++) {
                String x = Integer.toHexString(((int) tagId[i] & 0xff));
                if (x.length() == 1) {
                    x = '0' + x;
                }
                serie += x + ' ';
            }
            //se busca el mensaje mediante el parcelable
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            // si se encuentra el mensaje
            if(parcelables != null && parcelables.length > 0){
                readTextFromMessage((NdefMessage) parcelables[0], serie);
            }else{
                Toast.makeText(this,ERROR_MSGS, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Lee la codificacion del mensaje
     * @param ndefMessage
     * @param serie
     */
    private void readTextFromMessage(NdefMessage ndefMessage, final String serie) {
        //Se convierte el gran mensaje en un arreglo de records
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        // si hay mas de un arreglo de records se procesa
        if(ndefRecords != null && ndefRecords.length > 0){
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            if(tagContent.equals(";;") || tagContent.equals("")){
                NfcFragment.nfc_txt_name.setText(NfcFragment.nfc_txt_name.getHint());
                NfcFragment.nfc_txt_author.setText(NfcFragment.nfc_txt_author.getHint());
                NfcFragment.nfc_txt_creation.setText(NfcFragment.nfc_txt_creation.getHint());
                Toast.makeText(this,ERROR_MSGS, Toast.LENGTH_LONG).show();
            }else{
                String[] exit = tagContent.split(";");
                NfcFragment.nfc_txt_name.setText(exit[0]);
                NfcFragment.nfc_txt_author.setText(exit[1]);
                NfcFragment.nfc_txt_creation.setText(exit[2]);
            }
        }else {
            Toast.makeText(this,ERROR_LECT, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Se obtiene la codificacion de cada mensaje en el formato UTF-8
     * @param ndefRecord
     * @return
     */
    public String getTextFromNdefRecord(NdefRecord ndefRecord) {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding;
            if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
            else textEncoding = "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }


    /**
     * Esta opcion permite el retorno a la pantalla prinicipal
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*
        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        */
        if(id == R.id.action_settings){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Se usa el boton de back para salir de la aplicacion y volver a usar el splashscreen al entrar
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Metodo para establecer cada icono en cada tab disponible
     */
    private void setupTabIcons() {
        // Establece Iconos
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    /**
     * Permite establecer los fragmentos creados en cada tab
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NfcFragment(), "NFC");
        adapter.addFrag(new LocalFragment(), "APP");
        adapter.addFrag(new WebFragment(), "WEB");
        viewPager.setAdapter(adapter);
    }

    /**
     * Clase que permite adaptar los fragmentos en la activity
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}