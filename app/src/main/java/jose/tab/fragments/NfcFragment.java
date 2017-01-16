package jose.tab.fragments;

import android.content.Intent;
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

public class NfcFragment extends Fragment {

    /**
     * TextView del NFC: Nombre, Autor y Fecha de Creacion
     */
    TextView nfc_txt_name, nfc_txt_author, nfc_txt_creation;


    /**
     * Adaptador NFC, usado para la lectura del tag
     */
    NfcAdapter Adapter;

    /**
     * Mensajes NFC
     */
    public static final String INTENTO_NFC = "Intento de obtener NFC";
    public static final String ERROR_MSGS = "Mensajes de NFC no encontrados";
    public static final String ERROR_LECT = "No se puede leer NFC";


    /**
     * Intent para la lectura
     */
    Intent intent;

    /**
     * Numero serie NFC, sirve como PK de las bases de datos
     */
    String serie;

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

        // Uso de NFC
        Adapter = NfcAdapter.getDefaultAdapter(getActivity());
        intent = new Intent(view.getContext(),NfcFragment.class);
        Reading(intent);
        return view;
    }

    /**
     * El funcionamiento del NFC se hace con el intent de la actividad
     * debido a que no se desea perder la instancia
     * @param intent
     */
    private void Reading(Intent intent) {
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(getActivity(), INTENTO_NFC, Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(),ERROR_MSGS, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Lee la codificacion del mensaje
     * @param ndefMessage
     * @param serie
     */
    private void readTextFromMessage(NdefMessage ndefMessage, String serie) {
        //Se convierte el gran mensaje en un arreglo de records
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        // si hay mas de un arreglo de records se procesa
        if(ndefRecords != null && ndefRecords.length > 0){
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            if(tagContent.equals(";;") || tagContent.equals("")){
                nfc_txt_name.setText("");
                nfc_txt_author.setText("");
                nfc_txt_creation.setText("");
                Toast.makeText(getActivity(),ERROR_MSGS, Toast.LENGTH_LONG).show();
            }else{
                String[] exit = tagContent.split(";");
                nfc_txt_name.setText(exit[0]);
                nfc_txt_author.setText(exit[1]);
                nfc_txt_creation.setText(exit[2]);
                Toast.makeText(getActivity(), "Número de serie: " + serie,  Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Peso del mensaje: "+ ndefMessage.toByteArray().length + " bytes", Toast.LENGTH_LONG ).show();
            }
        }else {
            Toast.makeText(getActivity(),ERROR_LECT, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Se obtiene la codificacion de cada mensaje en el formato UTF-8
     * @param ndefRecord
     * @return
     */
    private String getTextFromNdefRecord(NdefRecord ndefRecord) {
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
}