package monitoriafga.trivia.com.monitoriafga;

import com.google.gson.annotations.SerializedName;

public class Monitoria {

    @SerializedName("monitor")
    private String monitor;
    @SerializedName("data_inicio")
    private int data_inicio;
    @SerializedName("data_fim")
    private int data_fim;
    @SerializedName("disciplina")
    private String disciplina;
    @SerializedName("sigla")
    private String sigla;
    @SerializedName("sala")
    private String sala;
    @SerializedName("dia1")
    private String dia1;
    @SerializedName("dia2")
    private String dia2;
    @SerializedName("dia3")
    private String dia3;
    @SerializedName("resultado")
    private String resultado;

    public Monitoria(String monitor, int data_inicio, int data_fim){
        this.data_fim = data_fim;
        this.data_inicio = data_inicio;
        this.monitor = monitor;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public int getHora_fim() {
        return data_fim;
    }

    public void setHora_fim(int hora_fim) {
        this.data_fim = hora_fim;
    }

    public int getHora_inicio() {
        return data_inicio;
    }

    public void setHora_inicio(int hora_inicio) {
        this.data_inicio = hora_inicio;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDia1() {
        return dia1;
    }

    public void setDia1(String dia1) {
        this.dia1 = dia1;
    }

    public String getDia2() {
        return dia2;
    }

    public void setDia2(String dia2) {
        this.dia2 = dia2;
    }

    public String getDia3() {
        return dia3;
    }

    public void setDia3(String dia3) {
        this.dia3 = dia3;
    }
}