package monitoriafga.trivia.com.monitoriafga;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class resultadoPesquisa extends AppCompatActivity {

    private TextView termoPesquisado;
    private ListView listViewResultadoPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_pesquisa);

        listViewResultadoPesquisa = (ListView) findViewById(R.id.listViewPesquisa);

        Bundle extra = getIntent().getExtras();
        termoPesquisado = (TextView) findViewById(R.id.termoPesquisado);

        String termoDaPesquisa = extra.getString("busca");

        getHeroes(termoDaPesquisa);

    }

    public void getHeroes(String busca){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apiService = retrofit.create(Api.class);

        Call<List<Monitoria>> call = apiService.consultaBanco(busca);
        call.enqueue(new Callback<List<Monitoria>>() {
            @Override
            public void onResponse(Call<List<Monitoria>> call, Response<List<Monitoria>> response) {
                try {

                    List<Monitoria> heroList = response.body();
                    String[] heroes = new String[heroList.size()];
                    String retorno = "1";



                    for (int i = 0; i < heroList.size(); i++) {

                        String hora_inicio = String.valueOf(heroList.get(i).getHora_inicio());
                        String hora_fim = String.valueOf(heroList.get(i).getHora_fim());
                        String monitor = String.valueOf(heroList.get(i).getMonitor());
                        String siglaMateria = String.valueOf(heroList.get(i).getSigla());
                        String sala = String.valueOf(heroList.get(i).getSala());
                        String dia1 = String.valueOf(heroList.get(i).getDia1());
                        String dia2 = String.valueOf(heroList.get(i).getDia2());
                        String dia3 = String.valueOf(heroList.get(i).getDia3());
                        retorno = String.valueOf(heroList.get(i).getResultado());

                        heroes[i] = siglaMateria + "\n" + "Monitor: " + monitor + "\n" + hora_inicio + " - " + hora_fim
                                + "\n" + "Sala: " + sala + "\n" + "Dias: " + dia1 + ", " + dia2 + ", " + dia3 + "\n\n";
                    }

                    if(retorno.equals("1")){
                        termoPesquisado.setText("Nenhum resultado encontrado");
                    }
                    else{
                        termoPesquisado.setText("Resultados Encontrados:");
                        listViewResultadoPesquisa.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
                    }





                }catch (Exception e){
                    e.printStackTrace();
                    termoPesquisado.setText("Error; " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Monitoria>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });

    }

}
