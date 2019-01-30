package com.example.ruan.ichat_alura.service;

import com.example.ruan.ichat_alura.activity.MainActivity;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ChatService {

    private MainActivity mainActivity;

    public ChatService(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void enviar(final Mensagem mensagem){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String texto = mensagem.getTexto();

                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.1.34:8080/polling").openConnection();
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("content-type", "application/json");

                    JSONStringer json = new JSONStringer()
                            .object()
                            .key("text")
                            .value(texto)
                            .key("id")
                            .value(mensagem.getId())
                            .endObject();

                    OutputStream saida = httpConnection.getOutputStream();
                    PrintStream ps = new PrintStream(saida);

                    ps.println(json.toString());

                    httpConnection.connect();
                    httpConnection.getInputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ouvirMensagens(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.1.34:8080/polling").openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setRequestProperty("Accept", "application/json");


                    httpConnection.connect();
                    Scanner scanner = new Scanner(httpConnection.getInputStream());

                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNextLine()){
                        builder.append(scanner.nextLine());
                    }

                    String json = builder.toString();

                    JSONObject jsonObject = new JSONObject(json);

                    final Mensagem mensagem = new Mensagem(jsonObject.getInt("id"), jsonObject.getString("text"));

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.colocaNaList(mensagem);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
