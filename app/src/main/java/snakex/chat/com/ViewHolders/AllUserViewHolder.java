package snakex.chat.com.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import snakex.chat.com.R;

public class AllUserViewHolder extends RecyclerView.ViewHolder {

public  CircleImageView profileImage;

public TextView Name;
public ImageButton ChatIcon;


public AllUserViewHolder(@NonNull View itemView) {

	 super(itemView);



	 profileImage = itemView.findViewById(R.id.AllUserProfileImage);
		Name = itemView.findViewById(R.id.Name);
	 ChatIcon = itemView.findViewById(R.id.ChatIcon);


}
}
