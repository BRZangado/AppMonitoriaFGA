package monitoriafga.trivia.com.monitoriafga;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://faculdadeanasps.com.br/";

    @GET("monitoriaAPI.php")
    Call<List<Monitoria>> getMonitorias();

    @FormUrlEncoded
    @POST("monitoriaPesquisaAPI.php")
    Call<List<Monitoria>> consultaBanco(
            @Field("busca") String busca
    );

    @FormUrlEncoded
    @POST("monitoriaPesquisaAPIhora.php")
    Call<List<Monitoria>> consultaBancoNow(
            @Field("busca") int busca
    );

}