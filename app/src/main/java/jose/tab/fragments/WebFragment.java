package jose.tab.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import jose.tab.R;
import jose.tab.activity.TabsActivity;
import jose.tab.model.Obra;
import jose.tab.service.APIMuseo;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    /**
     * URL del servidor web a usar
     */
    String URL = "http://jose8android.esy.es/";

    /**
     * URL de la Imagen
     */
    String IMG = URL + "obras/imagenes/";

    /**
     * URL del video
     */
    String VIDEO = URL + "obras/video/";

    /**
     * URL del audio
     */
    String AUDIO = URL + "obras/audio/";

    /**
     * Dialog de carga de los datos
     */
    private ProgressDialog load;

    /**
     * String de las respuestas obtenidas
     */
    String name_web,author_web,creation_web,summary_web,
           type_web, style_web, technique_web,
           entry_web, nationality_web, dim_web,
           weight_web, image_web, history_web,
           audio_web, video_web;

    /**
     * El mediaplayer para el audio
     */
    MediaPlayer player;

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

        //Carga de la informacion
        load = new ProgressDialog(getActivity());
        load.setTitle("Carga de Información Web");
        load.setMessage("Espere, cargando informacion");
        load.setCancelable(false);

        //Carga de los datos
        getObra(PK);

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

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_summary_web,null);
                        final TextView dialog_web_txt = (TextView)sumView.findViewById(R.id.dialog_web_txt);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        //Funcionamiento del dialog
                        dialog_web_txt.setText(summary_web);

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

                        // Carga de la Imagen con Glide
                        final ImageView dialog_img_web = (ImageView)sumView.findViewById(R.id.dialog_image_web);

                        // Uso de Glide
                        // load: se carga la imagen
                        // crossFade: animacion de entrada
                        // centerCrop: ocupa toda la pantalla de imgview
                        // into: carga la imagen en la imageview
                        Glide.with(getActivity())
                                .load(IMG + image_web)
                                .crossFade()
                                .centerCrop()
                                .into(dialog_img_web);

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

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_history_web,null);
                        final TextView dialog_web_history_txt = (TextView)sumView.findViewById(R.id.dialog_web_history_txt);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        //Funcionamiento del dialog
                        dialog_web_history_txt.setText(history_web);

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

                        /*Creacion del dialog*/
                        AlertDialog.Builder sum = new AlertDialog.Builder(getContext());
                        final View sumView = inflater.inflate(R.layout.dialog_video_web,null);
                        final Button dialog_btn_foot = (Button)sumView.findViewById(R.id.dialog_btn_foot);

                        final VideoView dialog_video = (VideoView)sumView.findViewById(R.id.dialog_video);
                        sum.setView(sumView);
                        final Dialog dialog = sum.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        //Carga del Video

                        // Crear process dialog para la carga
                        final ProgressDialog di = new ProgressDialog(getActivity());
                        // Poner titulo
                        di.setTitle("Video de Obra de Arte");
                        // Poner mensaje
                        di.setMessage("Cargando...");
                        di.setIndeterminate(false);
                        di.setCancelable(false);
                        // Mostrar Barra de progreso
                        di.show();

                        try {
                            // Incializar MediaController
                            MediaController media = new MediaController(dialog_video.getContext());
                            media.setAnchorView(dialog_video);
                            // Uso del URL del video
                            Uri video = Uri.parse(VIDEO + video_web);
                            dialog_video.setMediaController(media);
                            dialog_video.setVideoURI(video);
                            //media.setEnabled(true);
                            //media.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog_video.requestFocus();
                        dialog_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            // cerrar el dialog e inciar el video
                            public void onPrepared(MediaPlayer mp) {
                                di.dismiss();
                                dialog_video.start();
                            }
                        });

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
                            try {
                                player = new MediaPlayer();
                                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                player.setDataSource(AUDIO + audio_web);
                                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                player.prepareAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                            //Apagar Audio
                            try {
                                player.reset();
                                player.prepare();
                                detener();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                web_btn_audio.postDelayed(clickButton, 50);
            }
        });
        return view;
    }

    /**
     * Es el metodo que traera los datos de la base de datos a ser mostrados
     * en pantalla
     * @param pk
     */
    private void getObra(String pk) {

        // Carga de Dialog
        dialogOn();

        // Funcionamiento del servicio APIMuseo
        try {

            // Creacion del servicio Retrofit con el URL base dado
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Creacion de la instancia a la busqueda de la obra de arte con
            // la Primary key otorgada a la API
            APIMuseo apiMuseo = retrofit.create(APIMuseo.class);
            retrofit2.Call<Obra> call = apiMuseo.getObra(pk);

            // Enviar de manera asincrona la solicitud y notificar el callback (entrega) de
            // la respuesta de la base de datos y mostrar un mensaje en lo que se refiere a si fracasa la busqueda
            call.enqueue(new Callback<Obra>() {
                @Override
                public void onResponse(retrofit2.Call<Obra> call, Response<Obra> response) {

                    // Creacion de la instancia de la obra encontrada
                    Obra arte = response.body();

                    // Obtener campos de la obra encontrada
                    name_web = arte.getNombre();
                    author_web = arte.getAutor();
                    creation_web = arte.getFecha_creacion();
                    summary_web = arte.getResumen();
                    type_web = arte.getTipo_obra();
                    style_web = arte.getEstilo_obra();
                    technique_web = arte.getTecnica_obra();
                    entry_web = arte.getFecha_ingreso();
                    nationality_web = arte.getNacionalidad();
                    dim_web = arte.getDimensiones();
                    weight_web = arte.getPeso();
                    image_web = arte.getImagen();
                    history_web = arte.getHistoria();
                    audio_web = arte.getAudio();
                    video_web = arte.getVideo();

                    // Poner los datos en los campos
                    web_txt_name.setText(name_web);
                    web_txt_author.setText(author_web);
                    web_txt_creation.setText(creation_web);
                    web_txt_type.setText(type_web);
                    web_txt_style.setText(style_web);
                    web_txt_technique.setText(technique_web);
                    web_txt_entry.setText(entry_web);
                    web_txt_nationality.setText(nationality_web);
                    web_txt_dim.setText(dim_web);
                    web_txt_weight.setText(weight_web);

                    // Apagar el dialog
                    dialogOff();
                }

                @Override
                public void onFailure(retrofit2.Call<Obra> call, Throwable t) {
                    Toast.makeText(getActivity(), "No se encontró información", Toast.LENGTH_LONG).show();
                    dialogOff();
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), "La Aplicacion ha fracasado", Toast.LENGTH_LONG).show();
            dialogOff();
        }
    }

    /**
     * Carga del dialog al momento de mostrar la informacion
     */
    private void dialogOn() {
        if (!load.isShowing())load.show();
    }

    /**
     * Fin de dialog de carga
     * ocurre cuando se carga la informacion o cuando no encuentra nada
     */
    private void dialogOff(){
        if (load.isShowing())load.dismiss();
    }

    /**
     * Este metodo es para detener el audio
     */
    private void detener() {
        player.stop();
        player.release();
        player = null;
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