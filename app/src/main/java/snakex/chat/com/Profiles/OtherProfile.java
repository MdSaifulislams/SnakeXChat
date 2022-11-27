package snakex.chat.com.Profiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import snakex.chat.com.Chats.ChatWithOne;
import snakex.chat.com.Chats.ChatWithOneFriend;
import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.Model;
import snakex.chat.com.R;
import snakex.chat.com.databinding.OtherProfileBinding;


public class OtherProfile extends Fragment {

OtherProfileBinding binding;
DatabaseReference databaseReference;
StorageReference storageReference;
String UserId
		,UserName
		,UserEmail
		,ProfilePhotoUrl
		,CoverPhotoUrl;
Model model;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
   binding = OtherProfileBinding.inflate(getLayoutInflater(),container,false);

	 Methods.progressbarShow(binding.ProgressBar,null);
    getBundleData();
	 initialise();

	 if (UserId!=null){

			getDataToDataBase();
			Click();

	 }


	 return binding.getRoot();
}
////------------------------------------------ on create end



private void Click() {
	 ChatClick();
	 ProfilePhotoClick();
	 CoverPhotoClick();

}

private void ProfilePhotoClick() {
	 binding.profileImage.setOnClickListener(view -> {

			GoToOneActivityShow(ProfilePhotoUrl);
 });

}

//---------------------------------------------

private void CoverPhotoClick() {
	 binding.coverPhoto.setOnClickListener(view -> {

			GoToOneActivityShow(CoverPhotoUrl);
	 });
}
//---------------------------------------------

private void GoToOneActivityShow(String PhotoUrl) {
	 Intent intent = new Intent(getActivity(),PhotoShow.class);
	 intent.putExtra("PhotoUrl",PhotoUrl);
	 startActivity(intent);
}

private void ChatClick() {
	 binding.ChatButton.setOnClickListener(v ->{

//			Fragment fragment =new ChatWithOne();
//
//			Bundle bundle = new Bundle();
//
//			bundle.putString("UserId",UserId);
//			fragment.setArguments(bundle);
//
//			FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//			fragmentTransaction.replace(R.id.OtherProfile,fragment).commit();


			Intent intent = new Intent(getActivity(), ChatWithOneFriend.class);
			intent.putExtra("UserId",UserId);
			startActivity(intent);

	 });

}

//--------------------


private void getDataToDataBase() {
	 databaseReference.child(UserId).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
		    model = snapshot.getValue(Model.class);
				 getModelData();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				 Methods.ShowAlertDialog(getActivity(),error.getMessage(),"Error !");
			}
	 });

}

//-------------------------------------

private void getModelData() {
	 UserName = model.getName();
	 UserEmail = model.getUserEmail();
	 ProfilePhotoUrl = model.getProfilePhotoUrl();
	 CoverPhotoUrl = model.getCoverPhotoUrl();


	 setData();

}

//-----------------------------------

private void setData() {

	 binding.profileName.setText(UserName);
	 binding.profileEmail.setText(UserEmail);

	 Picasso.get().load(ProfilePhotoUrl).placeholder(R.drawable.man_icon).into(binding.profileImage);
	 Picasso.get().load(CoverPhotoUrl).placeholder(R.drawable.man_icon).into(binding.coverPhoto);
	 Methods.progressbarHide(binding.ProgressBar,null);
}

private void getBundleData() {
	 Bundle bundle = this.getArguments();
	 UserId= bundle.getString("UserId");


}

//-------------------------

private void initialise() {

databaseReference = FirebaseDatabase.getInstance().getReference("user");
//storageReference = FirebaseStorage.getInstance().getReference(UserId);

}
}