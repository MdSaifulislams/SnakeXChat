package snakex.chat.com.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import snakex.chat.com.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {


public  TextView  messageText
		 ,time;



public MessageViewHolder(@NonNull View itemView) {
	 super(itemView);

	 messageText =itemView.findViewById(R.id.message);
	 time =itemView.findViewById(R.id.time);

}
}
