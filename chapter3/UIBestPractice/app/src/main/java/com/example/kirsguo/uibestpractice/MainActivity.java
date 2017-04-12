package com.example.kirsguo.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Message> messageList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView messageRecycleView;
    private MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMessages();
        inputText = (EditText)findViewById(R.id.input_text);
        send =(Button)findViewById(R.id.send);
        messageRecycleView = (RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageRecycleView .setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messageList);
        messageRecycleView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)){
                    Message message = new Message(content,Message.TYPE_SENT);
                    messageList.add(message);
                    adapter.notifyItemInserted(messageList.size() - 1);
                    messageRecycleView.scrollToPosition(messageList.size() - 1 );
                    inputText.setText("");

                }
            }

        });
    }
    private void initMessages(){
        Message message1 = new Message("你好",Message.TYPE_RECEIVED);
        messageList.add(message1);
        Message message2 = new Message("你是？",Message.TYPE_SENT);
        messageList.add(message2);
        Message message3 = new Message("我是刘圣杰",Message.TYPE_RECEIVED);
        messageList.add(message3);
    }
}
