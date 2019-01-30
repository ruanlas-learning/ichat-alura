package com.example.ruan.ichat_alura.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.service.ChatService;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private ListView listaDeMensagens;
    private List<Mensagem> mensagens;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeMensagens = findViewById(R.id.lv_mensagens);
//        mensagens = Arrays.asList(new Mensagem(1, "olá alunos de android"), new Mensagem(2, "Oi"));
        mensagens = new ArrayList<>();
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

        final EditText editText = findViewById(R.id.et_texto);

        chatService = new ChatService(this);
        chatService.ouvirMensagens();

        Button button = findViewById(R.id.botao_enviar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString()));
            }
        });
    }

    public void colocaNaList(Mensagem mensagem){
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

        chatService.ouvirMensagens();
    }
}
