package snakex.chat.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import snakex.chat.com.Adapters.FragmentAdapter;
import snakex.chat.com.databinding.HomeBinding;

public class Home extends AppCompatActivity {
HomeBinding binding;
Toolbar TopToolbar;
FirebaseAuth firebaseAuth;
FragmentManager fragmentManager;
FragmentAdapter fragmentAdapter;

String[] FragmentName = Methods.FragmentName;



@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = HomeBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());


	 initialise();
	 setToolBar();
	 setAdapter();
  setTabIcon();
}



//-------------------------------------------------- on create end

private void setAdapter() {

	 fragmentAdapter = new FragmentAdapter(fragmentManager,1);


	 binding.ViewPager.setAdapter(fragmentAdapter);

	 binding.tabLayout.setupWithViewPager(binding.ViewPager);


}




//---------------------------------



private void setTabIcon() {



	 binding.tabLayout.getTabAt(0).setIcon(R.drawable.chat);
	 binding.tabLayout.getTabAt(1).setIcon(R.drawable.call);
	 binding.tabLayout.getTabAt(2).setIcon(R.drawable.multi_user);
	 binding.tabLayout.getTabAt(3).setIcon(R.drawable.profile_icon);



	 binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {

				 switch (tab.getPosition()){

						case 0: tab.setText(FragmentName[0]);
							 break;
						case 1: tab.setText(FragmentName[1]);
							 break;
						case 2: tab.setText(FragmentName[2]);
							 break;
						case 3: tab.setText(FragmentName[3]);
							 break;
				 }
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

				 tab.setText(null);

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
	 });




}


//--------------------------------



private void setToolBar() {

	 TopToolbar = findViewById(R.id.topToolBar);

	 setSupportActionBar(TopToolbar);

	 TopToolbar.setTitle("SnakeX Chat");



}



//-----------------------------


@Override
public boolean onCreateOptionsMenu(Menu menu) {

	 MenuInflater menuInflater = getMenuInflater();

    menuInflater.inflate(R.menu.top_menu,menu);

	 return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {

	 switch (item.getItemId()){

			case R.id.TopSignOut:

             break;

			case R.id.TopRefresh:



	 }



	 return super.onOptionsItemSelected(item);
}



//--------------------------------------







//--------------------------------------


private void initialise() {
	 firebaseAuth = FirebaseAuth.getInstance();
	 fragmentManager = getSupportFragmentManager();


}




}