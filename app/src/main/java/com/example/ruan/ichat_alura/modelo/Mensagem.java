package com.example.ruan.ichat_alura.modelo;

import com.google.gson.annotations.SerializedName;

public class Mensagem {
    private int id;
    @SerializedName("text")
    private String texto;

    public Mensagem(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
