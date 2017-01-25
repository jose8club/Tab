package jose.tab.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jose.tab.R;
import jose.tab.activity.TabsActivity;
import jose.tab.service.BD;


public class LocalFragment extends Fragment{

    /**
     * Textos de la base de datos
     */
    public static TextView local_txt_name, local_txt_author, local_txt_creation,
             local_txt_type, local_txt_style, local_txt_technique,
             local_txt_entry, local_txt_nationality, local_txt_dim,
             local_txt_weight;

    /**
     * Boton de resumen
     */
    Button local_btn_summary;

    /**
     * Boton de imagen
     */
    Button local_btn_image;

    /**
     * URL del servidor web a usar
     */
    private static final String URL = "http://jose8android.esy.es/";

    /**
     * URL de la Imagen
     */
    private static final String IMG = URL + "obras/imagenes/";

    /**
     * String usados para resumen y local
     */
    public static String local_summary, local_image;

    public LocalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        //return inflater.inflate(R.layout.fragment_local, container, false);
        //Textview inicializados
        local_txt_name = (TextView)view.findViewById(R.id.local_txt_name);
        local_txt_author = (TextView)view.findViewById(R.id.local_author_txt);
        local_txt_creation = (TextView)view.findViewById(R.id.local_txt_creation);
        local_txt_type = (TextView)view.findViewById(R.id.local_txt_type);
        local_txt_style = (TextView)view.findViewById(R.id.local_txt_style);
        local_txt_technique = (TextView)view.findViewById(R.id.local_txt_technique);
        local_txt_entry = (TextView)view.findViewById(R.id.local_txt_entry);
        local_txt_nationality = (TextView)view.findViewById(R.id.local_txt_nationality);
        local_txt_dim = (TextView)view.findViewById(R.id.local_txt_dim);
        local_txt_weight = (TextView)view.findViewById(R.id.local_txt_weight);

        //botones inicialiados

        //1) Boton resumen local
        local_btn_summary = (Button) view.findViewById(R.id.local_btn_summary);
        local_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        local_btn_summary.getBackground().setAlpha(255);

                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_summary,null);
                        final TextView dialog_txt = (TextView)sumView.findViewById(R.id.dialog_txt);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        //Funcionamiento del dialog
                        dialog_txt.setText(local_summary);

                        //Salir del dialog
                        dialog_btn_foot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                view.getBackground().setAlpha(128);
                                Runnable clickButton = new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog_btn_foot.getBackground().setAlpha(255);
                                        dialog.dismiss();
                                    }
                                };
                                view.postDelayed(clickButton, 80);
                            }
                        });

                    }
                };
                view.postDelayed(clickButton, 80);
            }
        });

        //2) Boton imagen local
        local_btn_image = (Button) view.findViewById(R.id.local_btn_image);
        local_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        local_btn_image.getBackground().setAlpha(255);

                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_image, null);
                        final Button dialog_btn_foot = (Button) sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        // Carga de la Imagen con Glide
                        final ImageView dialog_img_local = (ImageView) sumView.findViewById(R.id.dialog_image_local);

                        // Uso de Glide
                        // load: se carga la imagen
                        // crossFade: animacion de entrada
                        // centerCrop: ocupa toda la pantalla de imgview
                        // into: carga la imagen en la imageview
                        Glide.with(getActivity())
                                .load(IMG + local_image)
                                .crossFade()
                                .centerCrop()
                                .into(dialog_img_local);

                        //Salir del dialog
                        dialog_btn_foot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                view.getBackground().setAlpha(128);
                                Runnable clickButton = new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog_btn_foot.getBackground().setAlpha(255);
                                        dialog.dismiss();
                                    }
                                };
                                view.postDelayed(clickButton, 80);
                            }
                        });
                    }
                };
                view.postDelayed(clickButton, 80);
            }
        });
        return view;
    }

}
