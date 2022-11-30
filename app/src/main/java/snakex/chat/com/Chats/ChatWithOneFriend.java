package snakex.chat.com.Chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import snakex.chat.com.Adapters.MessageAdapter;
import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.MessagesModel;
import snakex.chat.com.ModelClass.Model;
import snakex.chat.com.R;
import snakex.chat.com.databinding.ChatWithOneBinding;

public class ChatWithOneFriend extends AppCompatActivity {
ChatWithOneBinding binding;

DatabaseReference databaseReference;
DatabaseReference databaseReferencesend;
FirebaseUser firebaseUser;
StorageReference storageReference;
Intent intent ;
String OtherUserId
    ,MyUserId
		,UserName
		,UserEmail
		,ProfilePhotoUrl
		,CoverPhotoUrl
		, messageText
		,MessageMils
		,messageKey
		;





Model model;
MessagesModel messagesModelUplod ,messagesModelReceived ;
List<MessagesModel> MessageList;

@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = ChatWithOneBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());



	 Methods.progressbarShow(binding.ProgressBar,null);
	 getIntentData();
	 initialise();
	 getData();
	 Click();

	 if (OtherUserId !=null){

			getOtherDataToDataBase();

			getMessageData();

	 }



}

private void Click() {
   BackClick();
	 messageInputClick();
	 sentButtonClick();


}

private void messageInputClick() {

	 binding.MessageText.setOnClickListener(view -> {

			binding.MessageText.setWidth(220);
			binding.MessageText.setHint("Type you thing");
	 });

}

//---------------------------

private void BackClick() {

	 binding.backButton.setOnClickListener(view -> {

			finish();

 });

}

private void sentButtonClick() {

	 binding.Send.setOnClickListener(v ->{
      getMessageDataInput();
			sentMessageData();
	 });

}

private void getMessageDataInput() {

	 messageText = binding.MessageText.getText().toString();
	 MessageMils = String.valueOf(System.currentTimeMillis());
	 messageKey = databaseReference.push().getKey();

}

//-------------------------------


private void sentMessageData() {

 messagesModelUplod = new MessagesModel(MyUserId,OtherUserId,MessageMils,messageText,messageKey);


 databaseReferencesend.child("Message").child(MyUserId).child(OtherUserId).setValue(messagesModelUplod)
		 .addOnCompleteListener(task -> {

				binding.MessageText.setText("");
 });


}







private void getMessageData() {
	 databaseReferencesend.child("Message").child(MyUserId).child(OtherUserId).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				 messagesModelReceived = snapshot.getValue(MessagesModel.class);

				 if (messagesModelReceived!=null) {
						MessageList.clear();
						MessageList.add(messagesModelReceived);

						setAdapter();
				 }
			}





			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
	 });
}

///-------------------------------------

private void setAdapter() {

	 MessageAdapter messageAdapter = new MessageAdapter(ChatWithOneFriend.this,MessageList,MyUserId);

	 LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatWithOneFriend.this);
	 linearLayoutManager.setStackFromEnd(true);
	 linearLayoutManager.setSmoothScrollbarEnabled(true);
	 binding.recyclerView.setLayoutManager(linearLayoutManager);
	 binding.recyclerView.setAdapter(messageAdapter);


}

//-----------------------------------------------------------


private void getOtherDataToDataBase() {
	 databaseReference.child(OtherUserId).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				 model = snapshot.getValue(Model.class);
				 getModelData();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				 Methods.ShowAlertDialog(ChatWithOneFriend.this,error.getMessage(),"Error !");
			}
	 });

}


//-----------------------------------------

private void getModelData() {
	 UserName = model.getName();
	 ProfilePhotoUrl = model.getProfilePhotoUrl();

	 setData();

}

//-----------------------------------

private void setData() {

	 binding.Name.setText(UserName);


	 Picasso.get().load(ProfilePhotoUrl).placeholder(R.drawable.man_icon).into(binding.UserProfileImage);

	 Methods.progressbarHide(binding.ProgressBar,null);
}



//-----------------------------------------
private void getData() {
	 MyUserId = firebaseUser.getUid();
}

//---------------------------------



private void getIntentData() {
	 intent = getIntent();

OtherUserId = 	 intent.getStringExtra("UserId");


}

private void initialise() {
  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
	 databaseReference = FirebaseDatabase.getInstance().getReference("user");
	 databaseReferencesend = FirebaseDatabase.getInstance().getReference();


	 MessageList = new ArrayList<>();

}
}