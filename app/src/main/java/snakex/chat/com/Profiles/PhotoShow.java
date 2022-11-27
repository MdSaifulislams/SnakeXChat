package snakex.chat.com.Profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import snakex.chat.com.R;
import snakex.chat.com.databinding.ProfileAndCoverPhotoShowBinding;

public class PhotoShow extends AppCompatActivity {


ProfileAndCoverPhotoShowBinding binding;
Intent intent ;
String PhotoUrl;


@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = ProfileAndCoverPhotoShowBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());

	 intent =getIntent();
	 PhotoUrl =	 intent.getStringExtra("PhotoUrl");

	 	 Picasso.get().load(PhotoUrl).placeholder(R.drawable.man_icon).into(binding.image);


}
}