package jose.tab.fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import jose.tab.R;
import jose.tab.activity.MainActivity;
import jose.tab.activity.TabsActivity;

public class NfcFragment extends Fragment {

    /**
     * TextView del NFC: Nombre, Autor y Fecha de Creacion
     */
    public static TextView nfc_txt_name, nfc_txt_author, nfc_txt_creation;

    public NfcFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);

        //TextView Inicializados
        nfc_txt_name = (TextView)view.findViewById(R.id.nfc_txt_name);
        nfc_txt_author = (TextView)view.findViewById(R.id.nfc_author_txt);
        nfc_txt_creation = (TextView)view.findViewById(R.id.nfc_txt_creation);
        return view;
    }
}