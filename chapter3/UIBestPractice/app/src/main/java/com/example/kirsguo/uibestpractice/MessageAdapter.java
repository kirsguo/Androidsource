package com.example.kirsguo.uibestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kirsguo on 2017/4/8.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messageList ;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMessage;
        TextView rightMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout)itemView.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout)itemView.findViewById(R.id.right_layout);
            leftMessage = (TextView)itemView.findViewById(R.id.left_message);
            rightMessage = (TextView)itemView.findViewById(R.id.right_message);
        }

    }

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getType() == Message.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMessage.setText(message.getContent());
        }else if (message.getType() == Message.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMessage.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
