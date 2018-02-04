package monitoriafga.trivia.com.monitoriafga;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView hora;
    private TextView textoResultado;
    private EditText termoBusca;
    private Button listagemCompleta;
    private Button pesquisar;
    private ListView monitoriasAgora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listagemCompleta = (Button) findViewById(R.id.botaoListagemCompleta);
        pesquisar = (Button) findViewById(R.id.buttonPesquisarId);
        termoBusca = (EditText) findViewById(R.id.pesquisarId);
        textoResultado = (TextView) findViewById(R.id.textViewTextoID);
        monitoriasAgora = (ListView) findViewById(R.id.monitoriaNowListView);

        listagemCompleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultadoCompleta.class);
                startActivity(intent);
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String busca = termoBusca.getText().toString();

                if (busca.length() == 0){
                    Toast.makeText(getApplicationContext(),"Nenhuma busca digitada", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intentPesquisa = new Intent(MainActivity.this, resultadoPesquisa.class);
                    intentPesquisa.putExtra("busca",busca);
                    startActivity(intentPesquisa);
                }
            }
        });

        int hora = this.retornaHoraAtual();
        getHeroes(hora);
    }

    public int retornaHoraAtual(){
        int hora;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar agora = Calendar.getInstance();

            hora = agora.get(Calendar.HOUR_OF_DAY);
            hora -= 2;
        }

        else {

            android.text.format.Time agora = new android.text.format.Time(android.text.format.Time.getCurrentTimezone());
            agora.setToNow();

            hora = agora.hour;
            hora -= 2;
        }
        return hora;
    }

    public void getHeroes(int hora){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apiService = retrofit.create(Api.class);

        Call<List<Monitoria>> call = apiService.consultaBancoNow(hora);
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
                        retorno = String.valueOf(heroList.get(i).getResultado());

                        heroes[i] = "\n" + siglaMateria + "\n" + "Monitor: " + monitor + "\n" + hora_inicio + " - " + hora_fim
                                + "\n" + "Sala: " + sala + "\n";
                    }

                    if(retorno.equals("1")){
                        textoResultado.setText("Nenhuma monitoria acontecendo no horario atual");
                    }
                    else{
                        textoResultado.setText("Monitorias acontecendo nesse momento:");
                        monitoriasAgora.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
                    }





                }catch (Exception e){
                    e.printStackTrace();
                    textoResultado.setText("Error; " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Monitoria>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });

    }

}
