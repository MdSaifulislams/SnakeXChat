package snakex.chat.com.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import snakex.chat.com.Adapters.AllUserViewAdapter;
import snakex.chat.com.Methods;
import snakex.chat.com.ModelClass.UserDataModel;

import snakex.chat.com.databinding.UserFragmentBinding;



public class UserFragment extends Fragment {


UserFragmentBinding binding;



FirebaseUser firebaseUser;
DatabaseReference databaseReference;

String UserId;
List<UserDataModel> userDataModelList;


@Override
public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	 binding = UserFragmentBinding.inflate(getLayoutInflater(),container,false);

	 initialise();
	 Methods.progressbarShow(binding.ProgressBar,null);
	 getData();




	 return binding.getRoot();
}


//--------------------------------- On Create end

private void getData() {
	 UserId = firebaseUser.getUid();


	 databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
      userDataModelList.clear();
				for( DataSnapshot dataSnapshot : snapshot.getChildren()) {
					 UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);
         userDataModelList.add(userDataModel);


				}

        Methods.progressbarHide(binding.ProgressBar,null);
				 AllUserViewAdapter adapter = new AllUserViewAdapter(userDataModelList,getActivity(),getActivity());
				binding.recyclerView.setAdapter(adapter);


			}



			@Override
			public void onCancelled(@NonNull DatabaseError error) {

				 Methods.progressbarHide(binding.ProgressBar,null);
				 Methods.ShowAlertDialog(getActivity(),error.getMessage(),"Warning !");
			}
	 });
}

//--------------------------------

private void initialise() {
	 userDataModelList = new ArrayList<>();

	 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
	 databaseReference = FirebaseDatabase.getInstance().getReference("user");

}
}