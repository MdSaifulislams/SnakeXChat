package snakex.chat.com.Chats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.UserDataModel;
import snakex.chat.com.R;
import snakex.chat.com.databinding.ChatWithOneBinding;


public class ChatWithOne extends Fragment {

ChatWithOneBinding binding;

DatabaseReference databaseReference;
StorageReference storageReference;
String UserId
		,UserName
		,UserEmail
		,ProfilePhotoUrl
		,CoverPhotoUrl;
UserDataModel userDataModel;


@Override
public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	 binding = ChatWithOneBinding.inflate(getLayoutInflater(),container,false);


	 Methods.progressbarShow(binding.ProgressBar,null);
	 getBundleData();
	 initialise();

	 if (UserId!=null){

			getDataToDataBase();

	 }


	 return binding.getRoot();
}


private void getDataToDataBase() {
	 databaseReference.child(UserId).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				 userDataModel = snapshot.getValue(UserDataModel.class);
				 getModelData();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				 Methods.ShowAlertDialog(getActivity(),error.getMessage(),"Error !");
			}
	 });

}


//-----------------------------------------

private void getModelData() {
	 UserName = userDataModel.getName();
	 ProfilePhotoUrl = userDataModel.getProfilePhotoUrl();

	 setData();

}

//-----------------------------------

private void setData() {

	 binding.Name.setText(UserName);


	 Picasso.get().load(ProfilePhotoUrl).placeholder(R.drawable.man_icon).into(binding.UserProfileImage);

	 Methods.progressbarHide(binding.ProgressBar,null);
}



//-----------------------------------------




private void getBundleData() {
	 Bundle bundle = this.getArguments();
	 UserId= bundle.getString("UserId");


}

private void initialise() {

	 databaseReference = FirebaseDatabase.getInstance().getReference("user");

}
}