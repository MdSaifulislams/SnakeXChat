package snakex.chat.com.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import snakex.chat.com.ModelClass.MessagesModel;
import snakex.chat.com.R;
import snakex.chat.com.ViewHolders.MessageViewHolder;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

	 Context context;
	 List<MessagesModel> MessageList;
	 View view;
   String MyUserId;
	 final  int leftUri =1;
	 final  int rightUri =2;





public MessageAdapter(Context context, List<MessagesModel> messageList, String myUserId) {
	 this.context = context;
	 MessageList = messageList;
	 MyUserId = myUserId;
}




@NonNull
@Override
public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



	 if (viewType == rightUri){

			view = LayoutInflater.from(context).inflate(R.layout.right_chat_recycler_view,parent,false);
	 }else {

			view = LayoutInflater.from(context).inflate(R.layout.left_chat_recycler_view,parent,false);
	 }

	 return new MessageViewHolder(view);


}


//-------------------------------------------------------

@Override
public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

	 MessagesModel oneMessageModel = MessageList.get(position);

	 holder.messageText.setText(oneMessageModel.getMessageText());
//	 holder.time.setText(oneMessageModel.getMessageText());

}



@Override
public int getItemCount() {
	 return MessageList.size();
}


@Override
public int getItemViewType(int position) {

	 if (MessageList.get(position).getSenderId().equals(MyUserId)){

			return rightUri;

	 }else {
			return leftUri;
	 }


}
}
