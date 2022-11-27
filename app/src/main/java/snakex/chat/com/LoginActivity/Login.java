package snakex.chat.com.LoginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

import snakex.chat.com.CreateAccount.CreateAccount;
import snakex.chat.com.Home;
import snakex.chat.com.databinding.LoginActivityBinding;
import snakex.chat.com.Methods;

public class Login extends AppCompatActivity {
 LoginActivityBinding binding;
 FirebaseAuth firebaseAuth;

private FirebaseUser firebaseUser;
Methods methods;

String  Email,Password;



@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = LoginActivityBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());

	 getPermissions();

	 initialise();
  checkUserAreLogin();


	 click();




}                //               ------------- end on create




private void checkUserAreLogin() {






	 if (firebaseUser!=null){

 Methods.SetIntent(Login.this,Home.class);
    finish();

	 }

}


//--------------------------------------------------------------   click start




private void click() {

	 loginButtonClick();
	 CreateAccountButtonClick();
}


//----------------




private void loginButtonClick() {



	 binding.loginButton.setOnClickListener(v-> {

			takeDataInput();
			checkFullFillInput();

	 });

}




//-----------------------------------------


private void takeDataInput() {


	 Email = Objects.requireNonNull(binding.email.getText()).toString().trim();
	 Password = Objects.requireNonNull(binding.password.getText()).toString().trim();


}






//----------------



private void checkFullFillInput() {


	  if (Email.equals("")){
			binding.email.setError("Email Required");

	 }else if (Password.equals("")){
			binding.password.setError("Password Required");

	 }else {

			 Methods.progressbarShow(binding.progressbar , binding.loginButton);

			CreateAccountVerified();
	 }




}


//---------------------------------------



private void CreateAccountVerified() {

	 firebaseAuth.signInWithEmailAndPassword(Email,Password)
			 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						 if (task.isSuccessful()) {

							 SuccessComplete();

						 }else {

             Methods.progressbarHide(binding.progressbar , binding.loginButton);

								Methods.ShowAlertDialog(Login.this,
										Objects.requireNonNull(task.getException()).getLocalizedMessage(),
										"Warning !");

						 }
					}
			 });


}





//--------------------------

private void SuccessComplete() {



	 Methods.SetIntent(Login.this, Home.class);
	 finish();
}

//--------------------------




private void CreateAccountButtonClick() {
	 binding.goCreateAccontuButton.setOnClickListener(v ->{


			Methods.SetIntent(Login.this,CreateAccount.class);
      finish();

	 });

}

//------------------------------------------------------------------  click end





private void initialise() {

	 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
	 firebaseAuth = FirebaseAuth.getInstance();


}



//---------------

private void getPermissions() {


	 Dexter.withContext(Login.this)
			 .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
			 .withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

						 if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
								ShowAlertDialog();
						 }
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
     permissionToken.continuePermissionRequest();
					}
			 })
			 .withErrorListener(error->{

					Toast.makeText(getApplicationContext(), "Erroroccurred!", Toast.LENGTH_SHORT).show();
			 }).onSameThread()
			 .check();
}


//--------------------------------

private void ShowAlertDialog() {

	 AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);

	 alert.setTitle("Need Permission");
	 alert.setMessage("This app needs permission to use this feature. You can grant them in app settings.");


	 alert.setPositiveButton("Go Setting", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

				 Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				 Uri uri =  Uri.fromParts("package",getPackageName(),null);
				 intent.setData(uri);

				 startActivityForResult(intent,101);
			}
	 });


	 alert.show();

}




}