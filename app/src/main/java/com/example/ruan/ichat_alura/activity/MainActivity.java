package com.example.ruan.ichat_alura.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ruan.ichat_alura.callback.EnviarMensagemCallback;
import com.example.ruan.ichat_alura.callback.OuvirMensagensCallback;
import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.modelo.Mensagem;
import com.example.ruan.ichat_alura.service.IChatService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private ListView listaDeMensagens;
    private List<Mensagem> mensagens;
//    private ChatService chatService;
    private IChatService chatService;

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

//        chatService = new ChatService(this);
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://192.168.1.34:8080/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        chatService = retrofit.create(IChatService.class);
//        Call<Mensagem> call = chatService.ouvirMensagens();
//        call.enqueue(new OuvirMensagensCallback(this));
        ouvirMensagens();

        Button button = findViewById(R.id.botao_enviar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());
            }
        });
    }

    public void colocaNaList(Mensagem mensagem){
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

//        chatService.ouvirMensagens().enqueue(new OuvirMensagensCallback(this));
        ouvirMensagens();
    }

    public void ouvirMensagens(){
        chatService.ouvirMensagens().enqueue(new OuvirMensagensCallback(this));
    }
}
