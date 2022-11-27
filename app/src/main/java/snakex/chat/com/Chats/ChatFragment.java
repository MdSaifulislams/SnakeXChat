package snakex.chat.com.Chats;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import snakex.chat.com.Fragment.CallFragment;
import snakex.chat.com.Profiles.OtherProfile;
import snakex.chat.com.R;
import snakex.chat.com.databinding.ChatFragmentBinding;


public class ChatFragment extends Fragment {

ChatFragmentBinding binding;
DatabaseReference databaseReference;
StorageReference storageReference;




@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	 binding = ChatFragmentBinding.inflate(getLayoutInflater(), container,false);




	 return binding.getRoot();
}
}