package snakex.chat.com.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import snakex.chat.com.Chats.ChatWithOneFriend;
import snakex.chat.com.ModelClass.UserDataModel;
import snakex.chat.com.Profiles.OtherProfile;
import snakex.chat.com.R;
import snakex.chat.com.ViewHolders.AllUserViewHolder;

public class AllUserViewAdapter extends RecyclerView.Adapter<AllUserViewHolder> {

	 List<UserDataModel> userDataModelList;
	 Context context;
	 FragmentActivity fragmentActivity;
   UserDataModel oneUserUserDataModel;

	 View view;

public AllUserViewAdapter(List<UserDataModel> userDataModelList, Context context, FragmentActivity fragmentActivity) {
	 this.userDataModelList = userDataModelList;
	 this.context = fragmentActivity;
	 this.fragmentActivity = fragmentActivity;

}

@NonNull
@Override
public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

	 view = LayoutInflater.from(fragmentActivity).inflate(R.layout.all_user_view,parent,false);

	 return new AllUserViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {

	oneUserUserDataModel = userDataModelList.get(position);

	 holder.Name.setText(oneUserUserDataModel.getName());

	 Picasso.get().load(oneUserUserDataModel.getProfilePhotoUrl()).error(R.drawable.profile_icon).placeholder(R.drawable.man_icon).into(holder.profileImage);

	 ItemClick(holder,position);


	 ChatClick(holder,position);




}





@Override
public int getItemCount() {
	 return userDataModelList.size();
}

//----------------------------------------------------------



private void ItemClick(AllUserViewHolder holder, int position) {

	 holder.itemView.setOnClickListener(view -> {

			Fragment fragment = new OtherProfile();

			Bundle bundle = new Bundle();
			bundle.putString("UserId", userDataModelList.get(position).getUserId());

     fragment.setArguments(bundle);

			FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.UserFragment,fragment).commit();
 });


}

private void ChatClick(AllUserViewHolder holder, int position) {

	 holder.ChatIcon.setOnClickListener(view -> {

//			Fragment fragment = new ChatWithOne();
//
//			Bundle bundle = new Bundle();
//			bundle.putString("UserId",userDataModelList.get(position).getUserId());
//
//			fragment.setArguments(bundle);
//
//			FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
//			fragmentTransaction.replace(R.id.UserFragment,fragment).commit();


			Intent intent = new Intent(context, ChatWithOneFriend.class);
			intent.putExtra("UserId", userDataModelList.get(position).getUserId());
			context.startActivity(intent);
	 });


}


}

