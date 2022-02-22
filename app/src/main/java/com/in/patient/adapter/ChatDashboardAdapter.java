package com.in.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.patient.R;
import com.in.patient.model.ChatDashboardModel;

import net.gotev.uploadservice.extensions.StringExtensionsKt;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Text;

import java.util.List;

public class ChatDashboardAdapter extends RecyclerView.Adapter<ChatDashboardAdapter.ViewHolder> {

    List<ChatDashboardModel.DashboardMessage> list;
    Context context;
    Click click;

    public interface Click {
        void onItemClick(int position);
    }

    public ChatDashboardAdapter(List<ChatDashboardModel.DashboardMessage> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_dashboard_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatDashboardModel.DashboardMessage model = list.get(position);


        if (model.getSend_by().equals("doctor")) {

            holder.second_chat.setVisibility(View.GONE);
            holder.receivedMessage.setText(StringEscapeUtils.unescapeJava(model.getMessage()));
            holder.receivedMessageTime.setText(model.getTime());


        }


        if (model.getSend_by().equals("patient")) {

            holder.first_chat.setVisibility(View.GONE);
            holder.send_message.setText(StringEscapeUtils.unescapeJava(model.getMessage()));
            holder.sendMessageTime.setText(model.getTime());

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView receivedMessage, receivedMessageTime, send_message, sendMessageTime;
        RelativeLayout first_chat, second_chat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            receivedMessage = itemView.findViewById(R.id.receivedMessage);
            receivedMessageTime = itemView.findViewById(R.id.receivedMessageTime);
            send_message = itemView.findViewById(R.id.send_message);
            sendMessageTime = itemView.findViewById(R.id.sendMessageTime);

            first_chat = itemView.findViewById(R.id.first_chat);
            second_chat = itemView.findViewById(R.id.second_chat);

        }
    }
}
