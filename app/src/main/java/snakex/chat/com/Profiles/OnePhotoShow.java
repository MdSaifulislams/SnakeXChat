package snakex.chat.com.Profiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import snakex.chat.com.R;
import snakex.chat.com.databinding.ProfileAndCoverPhotoShowBinding;


public class OnePhotoShow extends Fragment {


ProfileAndCoverPhotoShowBinding binding;
Intent intent;
String PhotoUrl ,pho;
public OnePhotoShow() {
	 // Required empty public constructor
}



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	 // Inflate the layout for this fragment
	 binding = ProfileAndCoverPhotoShowBinding.inflate(getLayoutInflater(), container,false);
//   binding.text.setText("saiful");

//	 intent = getActivity().getIntent();


//	 Bundle bundle = new Bundle();
//	 bundle.putString("key", value);


//	 intent.getString("PhotoUrl");



//	 PhotoUrl =	 intent.getStringExtra("PhotoUrl");
//
//	 Picasso.get().load(PhotoUrl).placeholder(R.drawable.saiful4).into(binding.image);
	 return binding.getRoot();
}
}