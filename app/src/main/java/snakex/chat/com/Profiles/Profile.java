package snakex.chat.com.Profiles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.resources.TextAppearance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import snakex.chat.com.EditInfo.EditPage;
import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.Model;
import snakex.chat.com.R;
import snakex.chat.com.databinding.ProfileBinding;

public class Profile extends Fragment {


ProfileBinding binding;
DatabaseReference databaseReference;
FirebaseUser firebaseUser;

String UserId
		, Name
		,Email
		,ProfilePhotoUrl,CoverPhotoUrl;
Model modelData;
ProgressDialog progressDialog;



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	 // Inflate the layout for this fragment

	 binding = ProfileBinding.inflate(inflater, container, false);

  Progress_Dialog();
	 initialise();
	 getData();
	 Click();


	 return binding.getRoot();
}///----------------------------------- On Create end



private void Click() {

	 ProfilePhotoClick();
	 CoverPhotoClick();
	 EditButtonClick();





}

private void ProfilePhotoClick() {
	 binding.profileImage.setOnClickListener(v ->{
			GoToOneActivityShow(ProfilePhotoUrl);
	 });
}

//-------------------------------------



private void CoverPhotoClick() {

	 binding.coverPhoto.setOnClickListener(v ->{

			GoToOneActivityShow(CoverPhotoUrl);

//			GoActivityToFragment();
//			FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
//			fragmentTransaction1.replace(R.id.Profile,fragment).commit();
	 });
}

//-------------------------------------



private void GoToOneActivityShow(String PhotoUrl) {
	 Intent intent = new Intent(getActivity(),PhotoShow.class);
	 intent.putExtra("PhotoUrl",PhotoUrl);
	 startActivity(intent);

}


//------------------------------------------

private void GoActivityToFragment() {

	 Fragment fragment = new OnePhotoShow();

	 FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

	 fragmentTransaction.replace(R.id.Profile,fragment).commit();


}


//------------------------------------------



private void EditButtonClick() {
	 binding.EditButton.setOnClickListener(view -> {


			Intent intent = new Intent(getActivity(), EditPage.class);
			intent.putExtra("UserId",UserId);
			startActivity(intent);
	 });
}



private void getData() {

	 databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				 modelData = snapshot.getValue(Model.class);

				 if (modelData!=null) {


						takeDataModel();
						setData();
				 }else{

						Toast.makeText(getActivity(), "You haven not any data", Toast.LENGTH_SHORT).show();
				 }
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

				 Methods.ShowAlertDialog(getActivity(), error.getMessage(), "Warning !");

			}
	 });


}

private void takeDataModel() {
	 Name = modelData.getName();
	 Email =modelData.getUserEmail();
	 ProfilePhotoUrl = modelData.getProfilePhotoUrl();
	 CoverPhotoUrl = modelData.getCoverPhotoUrl();

}


//-----------------------------------------------------

private void setData() {
	 if (modelData!=null) {
			Picasso.get().load(ProfilePhotoUrl).placeholder(R.drawable.man_icon).into(binding.profileImage);
			Picasso.get().load(CoverPhotoUrl).placeholder(R.drawable.multi_user).into(binding.coverPhoto);
	 }
	 binding.profileName.setText(Name);
	 binding.profileEmail.setText(Email);
}


//-----------------------------------------------------


private void initialise() {

	 firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
	 UserId = firebaseUser.getUid();
	 databaseReference= FirebaseDatabase.getInstance().getReference("user").child(UserId);

}

private void Progress_Dialog() {
	 progressDialog = new ProgressDialog(getActivity());
	 progressDialog.setIcon(R.drawable.edit);
	 progressDialog.setTitle("Loading...");
}

}