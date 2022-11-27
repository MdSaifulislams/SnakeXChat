package snakex.chat.com.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import snakex.chat.com.Fragment.CallFragment;
import snakex.chat.com.Chats.ChatFragment;
import snakex.chat.com.Profiles.Profile;
import snakex.chat.com.Fragment.UserFragment;
import snakex.chat.com.Methods;

public class FragmentAdapter extends FragmentPagerAdapter {

String[] FragmentName = Methods.FragmentName;


public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
	 super(fm, behavior);
}

//--------------------------------------------------

@NonNull
@Override
public Fragment getItem(int position) {

	 switch (position){

			case  0 :
			  return new ChatFragment();


			case  1 :
			  return new CallFragment();

			case  2 :
			  return new UserFragment();

				case  3 :
			  return new Profile() ;
	 }

	 return null;
}



//--------------------------------------------------



@Override
public int getCount() {
	 return FragmentName.length;
}



//--------------------------------------------------


//@Nullable
//@Override
//public CharSequence getPageTitle(int position) {
//	 return FragmentName[position];
//}
}
