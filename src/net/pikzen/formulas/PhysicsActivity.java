package net.pikzen.formulas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery.LayoutParams;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

public class PhysicsActivity extends Activity{
		
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.physics);
		
		final GridView gv = (GridView)findViewById(R.id.physicsGridView);
		gv.setAdapter(null);
		gv.setAdapter(new ImageAdapter(this, Common.physCategories));
		final Bundle bundle = new Bundle();
		final Intent intent = new Intent(this, ListItemActivity.class);
		
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long l)
			{
				switch(Common.physCategories.get(position).getText())
				{
				case R.string.p_constants:
					bundle.putInt("category", R.string.p_constants);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
					
				case R.string.p_nuclear:
					bundle.putInt("category", R.string.p_nuclear);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
					
				case R.string.p_mechanics:
					bundle.putInt("category", R.string.p_mechanics);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
					
				}
			} 
		}); 
	}
	
	public View makeView()
	{
		ImageView i = new ImageView(this);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
		return i;
	}


}
