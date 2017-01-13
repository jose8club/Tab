package jose.tab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jose.tab.R;

public class NfcFragment extends Fragment {

    /**
     * TextView del NFC: Nombre, Autor y Fecha de Creacion
     */
    TextView nfc_txt_name, nfc_txt_author, nfc_txt_creation;

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
