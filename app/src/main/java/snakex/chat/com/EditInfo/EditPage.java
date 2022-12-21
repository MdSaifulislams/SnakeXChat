package snakex.chat.com.EditInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import snakex.chat.com.LoginActivity.Login;
import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.UserDataModel;
import snakex.chat.com.R;
import snakex.chat.com.databinding.EditPageBinding;

public class EditPage extends AppCompatActivity {
EditPageBinding binding;
UserDataModel userDataModel;
DatabaseReference databaseReference;
StorageReference storageReference;
FirebaseAuth firebaseAuth;
Intent intent;

String
		 UserId
		,Name
    ,NameInput
		,Email
    ,EmailInput
		,ProfilePhotoUrl
		,CoverPhotoUrl
		;
Uri getProfilePhoto = null, getCoverPhoto = null;

boolean UploadCompleteProfilePhoto = true ;
boolean UploadCompleteCoverPhoto = true ;


@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding =EditPageBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());


	 initialise();
	 getData();
//	 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	 Click();







}


//------------------------------------------


private void Click() {

	 SaveButtonClick();
	 ProfilePhoto();
	 CoverPhoto();
	 backClick();
	 logOutClick();

}

private void logOutClick() {
binding.logout.setOnClickListener(v ->{
	 firebaseAuth.signOut();
	 Methods.SetIntent(EditPage.this, Login.class);
	 finish();
});
}

private void backClick() {

	 binding.Back.setOnClickListener(v -> {
	 finish();
	 });
}


private void SaveButtonClick() {

	 binding.save.setOnClickListener(v -> {
		 getDataInput();
	   CheckValidData();

 });

}



//------------------------------------------


private void ProfilePhoto() {

	 binding.ProfileEditButton.setOnClickListener(view -> {

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent,101);


 });

}



//---------------------------------------------------------


private void CoverPhoto() {

	 binding.coverPhotoEditButton.setOnClickListener(view -> {

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent,102);


	 });
}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
	 super.onActivityResult(requestCode, resultCode, data);


			if (resultCode==RESULT_OK){
				 if (data!=null){
						if (requestCode==101) {

							getProfilePhoto = data.getData();
							 binding.profileImage.setImageURI(getProfilePhoto);

						}else if (requestCode==102){

							 getCoverPhoto =data.getData();
							 binding.coverPhoto.setImageURI(getCoverPhoto);

						}

				 }
			}
}



//---------------------------------------------------



private void setProfilePhotoFireBaseStorage() {


	 storageReference.child("ProfilePhoto").putFile(getProfilePhoto)
			 .addOnCompleteListener(task -> {

					if (task.isSuccessful()){

						 storageReference.child("ProfilePhoto").getDownloadUrl()
								 .addOnSuccessListener(uri -> {

							 ProfilePhotoUrl = String.valueOf(uri);
										UploadCompleteProfilePhoto =true;
										CheckValidData();

						 }).addOnFailureListener(e -> {
								Toast.makeText(this,"Failed ProfilePhoto", Toast.LENGTH_SHORT).show();
										Methods.progressbarHide(binding.ProgressBar,binding.save);
						 });


					}else {
						 Toast.makeText(this,"Failed ProfilePhoto", Toast.LENGTH_SHORT).show();
						 Methods.progressbarHide(binding.ProgressBar,binding.save);
					}
			 });

}


//----------------------------------------------



	 private void setCoverPhotoFireBaseStorage() {

	 storageReference.child("CoverPhoto").putFile(getCoverPhoto)
			 .addOnCompleteListener(task -> {

					if (task.isSuccessful()){

						 storageReference.child("CoverPhoto").getDownloadUrl()
								 .addOnSuccessListener(uri -> {

							CoverPhotoUrl = String.valueOf(uri);
							UploadCompleteCoverPhoto = true;

										CheckValidData();

						 }).addOnFailureListener(e -> {

										Toast.makeText(this,"Failed CoverPhoto", Toast.LENGTH_SHORT).show();
										Methods.progressbarHide(binding.ProgressBar,binding.save);
						 });

					}else {
						 Toast.makeText(this,"Failed CoverPhoto", Toast.LENGTH_SHORT).show();
						 Methods.progressbarHide(binding.ProgressBar,binding.save);
					}
			 });

	 }



//------------------------------------------


private void CheckValidData() {


	 if (NameInput.equals("")){
			Methods.SetError(binding.profileName,"");
			Methods.progressbarHide(binding.ProgressBar,binding.save);
	 }else if (EmailInput.equals("")) {
			Methods.SetError(binding.profileEmail, "");
			Methods.progressbarHide(binding.ProgressBar,binding.save);

	 }else if(!UploadCompleteCoverPhoto){


	 }else if(!UploadCompleteProfilePhoto){

	 } else{
      Methods.progressbarHide(binding.ProgressBar,binding.save);
			UpdateData();
	 }

}


//------------------------------------------

private void UpdateData() {

	 Map<String,Object> map = new HashMap<>();
	 map.put("UserId", UserId);
	 map.put("ProfilePhotoUrl",ProfilePhotoUrl);
	 map.put("CoverPhotoUrl",CoverPhotoUrl);
	 map.put("Name",NameInput);
	 map.put("userEmail",EmailInput);





	 databaseReference.updateChildren(map).addOnCompleteListener(task -> {

	if(task.isSuccessful()){
			 Toast.makeText(EditPage.this, "Successfully Completed", Toast.LENGTH_SHORT).show();
			 finish();
	}else {

			 Methods.ShowAlertDialog(EditPage.this
					,task.getException().getLocalizedMessage(),"Warrning !");
		 Methods.progressbarHide(binding.ProgressBar,binding.save);
	}

 });


}

//------------------------------------------


private void getDataInput() {

	 if (getProfilePhoto!=null || getCoverPhoto!=null) {
			Methods.progressbarShow(binding.ProgressBar,binding.save);
			if (getProfilePhoto != null) {

				 UploadCompleteProfilePhoto = false;

				 setProfilePhotoFireBaseStorage();
			}

			if (getCoverPhoto != null) {

				 UploadCompleteCoverPhoto = false;

				 setCoverPhotoFireBaseStorage();
			}
	 }


	NameInput = binding.profileName.getText().toString();
	EmailInput = binding.profileEmail.getText().toString();

}




//------------------------------------



private void getData() {

 UserId =	 intent.getStringExtra("UserId");
	 storageReference = FirebaseStorage.getInstance().getReference(UserId);

	 databaseReference= FirebaseDatabase.getInstance().getReference("user").child(UserId);

	 databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				 userDataModel = snapshot.getValue(UserDataModel.class);

				 if (userDataModel !=null) {

						getDataModel();
				 }else{

						Toast.makeText(EditPage.this, "You haven not any data", Toast.LENGTH_SHORT).show();
				 }
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

				 Methods.ShowAlertDialog(EditPage.this, error.getMessage(), "Warning !");

			}
	 });

}


//------------------------------------
private void getDataModel() {

	 Name = userDataModel.getName();
	 Email = userDataModel.getUserEmail();
	 ProfilePhotoUrl  = userDataModel.getProfilePhotoUrl();
	 CoverPhotoUrl = userDataModel.getCoverPhotoUrl();

	 setData();



}

//----------------------------------------



private void setData() {




Picasso.get().load(ProfilePhotoUrl).placeholder(R.drawable.man_icon).into(binding.profileImage);
Picasso.get().load(CoverPhotoUrl).placeholder(R.drawable.multi_user).into(binding.coverPhoto);


binding.profileName.setText(Name);
binding.profileEmail.setText(Email);

}


//------------------------------------



private void initialise() {
//	 userDataModel = new UserDataModel();
	 intent = getIntent();
	 firebaseAuth = FirebaseAuth.getInstance();



}
}