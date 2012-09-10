package net.pikzen.formulas;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<LinkedText> mCategories;
	public ImageAdapter(Context c, ArrayList<LinkedText> categories)
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
	
	public long getItemId(int position)
	{
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if (convertView == null)
		{
			LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.grid_item, null);
            
		}
		else 
		{
			view = (View) convertView;
		}
		
		TextView tv = (TextView)view.findViewById(R.id.grid_item_text);
		tv.setText(mContext.getString(mCategories.get(position).getText()));
		
		ImageView iv = (ImageView)view.findViewById(R.id.grid_item_image);
		iv.setImageResource(mCategories.get(position).getImage());
        iv.setLayoutParams(new LinearLayout.LayoutParams(105,105));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(8,8,8,8);
        
		return view;
	}
	
}
