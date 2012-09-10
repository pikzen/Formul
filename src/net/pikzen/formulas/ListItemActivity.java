package net.pikzen.formulas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListItemActivity extends Activity{

	public void onAttachedToWindow()
	{
			super.onAttachedToWindow();
			Window win = getWindow();
			win.setFormat(PixelFormat.RGBA_8888);
			win.addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		final ListView lv = (ListView)findViewById(R.id.commonList);
		lv.setTextFilterEnabled(true);
		category = this.getIntent().getExtras().getInt("category");
		
		
		switch(category)
		{
		case R.string.p_constants:
			lv.setAdapter(new TextSubTextAdapter(this, Common.physConstants));
			break;
		case R.string.p_nuclear:
			target = Common.physNuclear;
			lv.setAdapter(new SimpleTextAdapter(this, Common.physNuclear));
			break;
		case R.string.p_mechanics:
			target = Common.physMechanics;
			lv.setAdapter(new SimpleTextAdapter(this, Common.physMechanics));
			break;
		}
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				if (category != R.string.p_constants && !target.get(position).getName().contains("Sec:"))
				{
					Intent intent = new Intent(v.getContext(), FormulaDisplayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("formula", target.get(position).getName());
					bundle.putString("desc", target.get(position).getDesc());
					bundle.putString("image", target.get(position).getImage());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		
	}
	
	int category;
	ArrayList<Item> target = new ArrayList<Item>();
	

}
