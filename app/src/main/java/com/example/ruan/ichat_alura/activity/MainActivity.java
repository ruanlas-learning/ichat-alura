package com.example.ruan.ichat_alura.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ruan.ichat_alura.app.ChatApplication;
import com.example.ruan.ichat_alura.callback.EnviarMensagemCallback;
import com.example.ruan.ichat_alura.callback.OuvirMensagensCallback;
import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.component.ChatComponent;
import com.example.ruan.ichat_alura.modelo.Mensagem;
import com.example.ruan.ichat_alura.service.IChatService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;

    @BindView(R.id.botao_enviar)
    Button button;
    @BindView(R.id.lv_mensagens)
    ListView listaDeMensagens;
    @BindView(R.id.et_texto)
    EditText editText;
    @BindView(R.id.iv_avatar_usuario)
    ImageView avatar;

    private List<Mensagem> mensagens;
//    private ChatService chatService;
    @Inject
    IChatService chatService;
    private ChatComponent component;

    @Inject
    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this).load("http://api.adorable.io/avatars/285/"+ idDoCliente +".png").into(avatar);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);


//        mensagens = Arrays.asList(new Mensagem(1, "olá alunos de android"), new Mensagem(2, "Oi"));
        mensagens = new ArrayList<>();
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

//        chatService = new ChatService(this);

//        Call<Mensagem> call = chatService.ouvirMensagens();
//        call.enqueue(new OuvirMensagensCallback(this));
        ouvirMensagens();
    }

    @OnClick(R.id.botao_enviar)
    public void enviarMensagem(){
        chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());
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
