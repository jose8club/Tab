package jose.tab.service;

import jose.tab.model.Obra;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alicia on 18/01/2017.
 */

public interface APIMuseo {
    @GET("obras/museo/obtener_obras_por_id.php")
    Call<Obra> getObra(@Query("idobra") String idobra);
}
