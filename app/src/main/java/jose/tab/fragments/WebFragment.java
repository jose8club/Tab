package jose.tab.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import jose.tab.R;
import jose.tab.activity.TabsActivity;

public class WebFragment extends Fragment {

    /**
     * Textos de la base de datos web
     */
    TextView web_txt_name, web_txt_author, web_txt_creation,
            web_txt_type, web_txt_style, web_txt_technique,
            web_txt_entry, web_txt_nationality, web_txt_dim,
            web_txt_weight;

    /**
     * Boton de resumen
     */
    Button web_btn_summary;

    /**
     * Boton de imagen
     */
    Button web_btn_image;

    /**
     * Boton de historia
     */
    Button web_btn_history;

    /**
     * Boton de video
     */
    Button web_btn_video;

    /**
     * Boton de imagen
     */
    ToggleButton web_btn_audio;

    /**
     * Es el Primary key usado desde el NFC
     */
    String PK;

    public WebFragment() {
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
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        //Textview inicializados
        web_txt_name = (TextView)view.findViewById(R.id.web_txt_name);
        web_txt_author = (TextView)view.findViewById(R.id.web_author_txt);
        web_txt_creation = (TextView)view.findViewById(R.id.web_txt_creation);
        web_txt_type = (TextView)view.findViewById(R.id.web_txt_type);
        web_txt_style = (TextView)view.findViewById(R.id.web_txt_style);
        web_txt_technique = (TextView)view.findViewById(R.id.web_txt_technique);
        web_txt_entry = (TextView)view.findViewById(R.id.web_txt_entry);
        web_txt_nationality = (TextView)view.findViewById(R.id.web_txt_nationality);
        web_txt_dim = (TextView)view.findViewById(R.id.web_txt_dim);
        web_txt_weight = (TextView)view.findViewById(R.id.web_txt_weight);

        //Primary Key asegurada
        PK = TabsActivity.serie;
        modal(PK);

        //botones inicialiados

        //1) Boton resumen web
        web_btn_summary = (Button) view.findViewById(R.id.web_btn_summary);
        web_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        web_btn_summary.getBackground().setAlpha(255);
                        /*Funcionamiento dialog*/
                        /*Fin funcionamiento dialog*/

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_summary_web,null);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

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

        //2) Boton imagen web
        web_btn_image = (Button) view.findViewById(R.id.web_btn_image);
        web_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        web_btn_image.getBackground().setAlpha(255);
                        /*Funcionamiento dialog*/
                        /*Fin funcionamiento dialog*/

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_image_web,null);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

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
                        final ImageView dialog_img_local = (ImageView)sumView.findViewById(R.id.dialog_image_web);
                        dialog_img_local.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                modal("Imagen Web");
                            }
                        });
                    }
                };
                view.postDelayed(clickButton, 80);
            }
        });

        //3) Boton historia web
        web_btn_history = (Button) view.findViewById(R.id.web_btn_history);
        web_btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        web_btn_history.getBackground().setAlpha(255);
                        /*Funcionamiento dialog*/
                        /*Fin funcionamiento dialog*/

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_history_web,null);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

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

        //3) Boton video web
        web_btn_video = (Button) view.findViewById(R.id.web_btn_video);
        web_btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        web_btn_video.getBackground().setAlpha(255);
                        /*Funcionamiento dialog*/
                        /*Fin funcionamiento dialog*/

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_video_web,null);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

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

        //4) Boton audio web
        web_btn_audio = (ToggleButton)view.findViewById(R.id.web_btn_audio);
        web_btn_audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                web_btn_audio.getBackground().setAlpha(128);
                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {
                        web_btn_audio.getBackground().setAlpha(255);
                        if(b){
                            //Encender Audio
                            modal("audio encendido");
                        }else{
                            //Apagar Audio
                            modal("audio apagado");
                        }
                    }
                };
                web_btn_audio.postDelayed(clickButton, 50);
            }
        });
        return view;
    }

    /**
     * Metodo temporal para probar botones
     * sera quitado pronto
     * @param resumen
     */
    private void modal(String resumen) {
        Toast.makeText(getActivity(), resumen, Toast.LENGTH_LONG).show();
    }
}