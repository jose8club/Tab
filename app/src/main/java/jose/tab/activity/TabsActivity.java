package jose.tab.activity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jose.tab.fragments.LocalFragment;
import jose.tab.fragments.NfcFragment;
import jose.tab.fragments.WebFragment;
import jose.tab.R;


public class TabsActivity extends AppCompatActivity {

    /**
     * Adaptador NFC, usado para la lectura del tag
     */
    NfcAdapter Adapter;

    /**
     * Mensajes NFC
     */
    public static final String INTENTO_NFC = "Intento de obtener NFC";

    /**
     * Toolbar de la ventana
     */
    private Toolbar toolbar;

    /**
     * Layout de la tabla
     */
    private TabLayout tabLayout;

    /**
     * Vista de la pestania
     */
    private ViewPager viewPager;

    /**
     * Iconos que iran en cada pestania
     */
    private int[] tabIcons = {
            R.drawable.ic_tab_nfc,
            R.drawable.ic_tab_plus,
            R.drawable.ic_tab_web
    };

    /**
     * Intent de la activity
     */
    NfcFragment nfc;
    LocalFragment local;
    WebFragment web;

    /**
     * Numero serie NFC, sirve como PK de las bases de datos
     */
    String serie;

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
        setContentView(R.layout.activity_tabs);

        // Uso de NFC
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
        
        nfc = new NfcFragment();
        local = new LocalFragment();
        web = new WebFragment();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setAlpha(128);
                        tabLayout.getTabAt(1).getIcon().setAlpha(255);
                        tabLayout.getTabAt(2).getIcon().setAlpha(128);
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
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(this, INTENTO_NFC, Toast.LENGTH_LONG).show();
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
            nfc.uso(parcelables, serie);

        }
    }

    /**
     * Se define que siempre que se quiera regresar regrese al primer tab el de NFC
     */
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(0);
    }

    /**
     * Esta opcion permite el retorno a la pantalla prinicipal
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo para establecer cada icono en cada tab disponible
     */
    private void setupTabIcons() {
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
        adapter.addFrag(nfc, "NFC");
        adapter.addFrag(local, "LOCAL");
        adapter.addFrag(web, "WEB");
        //adapter.addFrag(new NfcFragment(), "NFC");
        //adapter.addFrag(new LocalFragment(), "LOCAL");
        //adapter.addFrag(new WebFragment(), "WEB");
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