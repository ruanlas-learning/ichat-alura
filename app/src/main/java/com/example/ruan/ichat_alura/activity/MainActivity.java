package com.example.ruan.ichat_alura.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeMensagens = findViewById(R.id.lv_mensagens);
        List<Mensagem> mensagens = Arrays.asList(new Mensagem(1, "olá alunos de android"), new Mensagem(2, "Oi"));
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);
    }
}
