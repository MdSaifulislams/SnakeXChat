package snakex.chat.com.CreateAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import snakex.chat.com.Home;
import snakex.chat.com.LoginActivity.Login;
import snakex.chat.com.Methods;
import snakex.chat.com.databinding.CreateAccountBinding;

public class CreateAccount extends AppCompatActivity {

CreateAccountBinding binding;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
DatabaseReference databaseReference;
StorageReference storageReference;

String UserId, Name, Email, Password, ProfilePhotoUrl = "ProfilePhotoUrl" ;
Uri ProfilePhotoUri;
boolean UploadCompleteProfilePhoto = true;


@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = CreateAccountBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());

	 initialise();
	 click();
	 AddProfilePhoto();


}                //               ------------- end on create

private void getData() {

	 UserId = firebaseUser.getUid();

}


//--------------------------------------------------------------   click start


private void click() {
//	 AddProfilePhoto();
	 CreateAccountButtonClick();
	 loginButtonClick();
}


//-------------------------

private void AddProfilePhoto() {

	binding.EditButton.setOnClickListener(view -> {


			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 111);
//			GetPhotoToDevice();
			Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
	 });

}

//-------------------------

private void GetPhotoToDevice() {

	 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	 intent.setType("image/*");
	 startActivityForResult(intent, 111);

}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
	 super.onActivityResult(requestCode, resultCode, data);


	 if (resultCode == RESULT_OK) {
			if (data != null) {
				 if (requestCode == 111) {
						ProfilePhotoUri = data.getData();
						binding.ProfilePhoto.setImageURI(ProfilePhotoUri);
				 }
			}
	 }
}

//----------------


private void loginButtonClick() {

	 binding.goLoginButton.setOnClickListener(v -> {

			startActivity(new Intent(CreateAccount.this, Login.class));
			finish();

	 });
}


//----------------

private void CreateAccountButtonClick() {

	 binding.CreateAccountButton.setOnClickListener(v -> {


			takeDataInput();
			checkFullFillInput();


	 });
}


//----------------------------


private void checkFullFillInput() {


	 if (Name.equals("")) {
			binding.name.setError("Name Required");

	 } else if (Email.equals("")) {
			binding.email.setError("Email Required");

	 } else if (Password.equals("")) {
			binding.password.setError("Password Required");


	 } else {
			ShowProgressBar();
			CreateAccountVerified();


	 }


}

private void CheckPhotoUpload() {


	 if (!UploadCompleteProfilePhoto) {

	 } else {
			SuccessComplete();

	 }

}

//--------------------


//--------------------------

private void CreateAccountVerified() {

	 firebaseAuth.createUserWithEmailAndPassword(Email, Password)
			 .addOnSuccessListener(authResult -> {
					Toast.makeText(CreateAccount.this, "Success", Toast.LENGTH_SHORT).show();

					initialise();
					getData();

					if (ProfilePhotoUri != null) {
						 UploadCompleteProfilePhoto = false;
						 setProfilePhotoDataBase();
					}
					CheckPhotoUpload();

			 })
			 .addOnFailureListener(e -> {

					Methods.ShowAlertDialog(CreateAccount.this, e.getLocalizedMessage(), "Warning !");

					hideProgressBar();
			 });
}


//--------------------------------


private void SuccessComplete() {

	 Map<String, Object> userData = new HashMap<>();

	 userData.put("UserId", UserId);
	 userData.put("ProfilePhotoUrl", ProfilePhotoUrl);
	 userData.put("CoverPhotoUrl", "CoverPhotoUrl");
	 userData.put("Name", Name);
	 userData.put("userEmail", Email);
	 userData.put("userPassword", Password);


	 databaseReference.child(UserId).setValue(userData)
			 .addOnCompleteListener(task -> {
					if (task.isSuccessful()) {

						 hideProgressBar();

						 Methods.SetIntent(CreateAccount.this, Home.class);
						 finish();

					} else {
	 Methods.ShowAlertDialog(CreateAccount.this, task.getException().getLocalizedMessage(), "Warning !");
						 hideProgressBar();
//
					}
			 });


}


//---------------------------


private void takeDataInput() {

	 Name = Objects.requireNonNull(binding.name.getText()).toString().trim();
	 Email = Objects.requireNonNull(binding.email.getText()).toString().trim();
	 Password = Objects.requireNonNull(binding.password.getText()).toString().trim();

}

//------------------------------------------

private void setProfilePhotoDataBase() {

	 storageReference = FirebaseStorage.getInstance().getReference(UserId).child("ProfilePhoto");

	 storageReference.putFile(ProfilePhotoUri).addOnSuccessListener(taskSnapshot -> {

			storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

				 ProfilePhotoUrl = String.valueOf(uri);
				 UploadCompleteProfilePhoto = true;
				 CheckPhotoUpload();

			}).addOnFailureListener(e -> {

				 Methods.ShowAlertDialog(CreateAccount.this, e.getLocalizedMessage(), "Warning !");
				 hideProgressBar();


			});


	 }).addOnFailureListener(e -> {

			Methods.ShowAlertDialog(CreateAccount.this, e.getLocalizedMessage(), "Warning !");
			hideProgressBar();
	 });

}



//------------------------------------------------------------------  click end

private void initialise() {

	 firebaseAuth = FirebaseAuth.getInstance();
	 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
	 databaseReference = FirebaseDatabase.getInstance().getReference("user");


}


//-----------------------------------------------


private void hideProgressBar() {

Methods.progressbarHide(binding.progressbar,binding.CreateAccountButton);
}

//---------------------------------------


private void ShowProgressBar() {
	 Methods.progressbarShow(binding.progressbar,binding.CreateAccountButton);
}





}

