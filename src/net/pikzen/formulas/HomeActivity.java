package net.pikzen.formulas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TabHost;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.*;
import com.actionbarsherlock.view.Menu;

public class HomeActivity extends SherlockFragmentActivity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        actionBar.addTab(actionBar.newTab()
        		.setText("Physics")
        		.setTabListener(new TabListener<DashBoardFragment>(this, "physics", DashBoardFragment.class)));
        
        setContentView(R.layout.main);
        Common.Setup(this);
       
                
        final GridView gv = (GridView)findViewById(R.id.mainGridView);
        gv.setAdapter(new ImageAdapter(this, Common.mainCategories));
        
        
        gv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long l)
        	{
        		switch(Common.mainCategories.get(position).getText())
        		{
        		case R.string.main_mathematics:
        			Intent mathIntent = new Intent(v.getContext(), MathActivity.class);
        			startActivity(mathIntent);
        			break;
        			
        		case R.string.main_physics:
        			Intent physIntent = new Intent(v.getContext(), PhysicsActivity.class);
        			startActivity(physIntent);
        			break;
        		}
        	}
		});
    }
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.actionbar_search, (android.view.Menu)menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}
	
}