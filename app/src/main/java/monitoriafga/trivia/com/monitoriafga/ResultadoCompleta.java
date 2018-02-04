package monitoriafga.trivia.com.monitoriafga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadoCompleta extends AppCompatActivity {

    private ListView resultadoCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_completa);

        resultadoCompleto = (ListView) findViewById(R.id.listagemCompleta);

        getHeroes();

    }

    public void getHeroes(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Monitoria>> call = api.getMonitorias();

        call.enqueue(new Callback<List<Monitoria>>() {
            @Override
            public void onResponse(Call<List<Monitoria>> call, Response<List<Monitoria>> response) {
                List<Monitoria> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];
                String[] heroes_end = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    String hora_inicio = String.valueOf(heroList.get(i).getHora_inicio());
                    String hora_fim = String.valueOf(heroList.get(i).getHora_fim());
                    String monitor = String.valueOf(heroList.get(i).getMonitor());
                    String siglaMateria = String.valueOf(heroList.get(i).getSigla());
                    String sala = String.valueOf(heroList.get(i).getSala());
                    String dia1 = String.valueOf(heroList.get(i).getDia1());
                    String dia2 = String.valueOf(heroList.get(i).getDia2());
                    String dia3 = String.valueOf(heroList.get(i).getDia3());

                    heroes[i] = "\n" + siglaMateria + "\n" + "Monitor: " + monitor + "\n" + hora_inicio + " - " + hora_fim
                    + "\n" + "Sala: " + sala + "\n" + "Dias: " + dia1 + ", " + dia2 + ", " + dia3 + "\n";


                }


                //displaying the string array into listview
                resultadoCompleto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));

            }

            @Override
            public void onFailure(Call<List<Monitoria>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
