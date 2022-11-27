package snakex.chat.com.Chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.Model;
import snakex.chat.com.R;
import snakex.chat.com.databinding.ChatWithOneBinding;

public class ChatWithOneFriend extends AppCompatActivity {
ChatWithOneBinding binding;

DatabaseReference databaseReference;
StorageReference storageReference;
Intent intent ;
String UserId
		,UserName
		,UserEmail
		,ProfilePhotoUrl
		,CoverPhotoUrl;
Model model;
@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = ChatWithOneBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());



	 Methods.progressbarShow(binding.ProgressBar,null);
	 getIntentData();
	 initialise();

	 if (UserId!=null){

			getDataToDataBase();

	 }



}


private void getDataToDataBase() {
	 databaseReference.child(UserId).addValueEventListener(new ValueEventListener() {
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




private void getIntentData() {
	 intent = getIntent();

UserId = 	 intent.getStringExtra("UserId");


}

private void initialise() {

	 databaseReference = FirebaseDatabase.getInstance().getReference("user");

}
}