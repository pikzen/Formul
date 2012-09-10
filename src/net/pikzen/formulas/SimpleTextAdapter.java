package net.pikzen.formulas;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SimpleTextAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Item> mCategories;
	
	public SimpleTextAdapter(Context c, ArrayList<Item> categories)
	{
		mContext = c;
		mCategories = categories;

	}
	
	
	public int getCount()
	{
		return mCategories.size(); 
	}
	
	public Object getItem(int position)
	{
		return mCategories.get(position);
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		String item = mCategories.get(position).getName();
		if(item.contains("Sec:"))
		{
			view = li.inflate(R.layout.section_index,null);
			TextView tv = (TextView)view.findViewById(R.id.section);
			tv.setText(item.replace("Sec:", ""));
			tv.setEnabled(false);
			
			view.getBackground().setDither(true);
		}
		else
		{
			
			view = li.inflate(R.layout.simple_text, null);
			TextView tv = (TextView)view.findViewById(R.id.simpleText);
			tv.setText(mCategories.get(position).getName());
		}
		
		return view;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
