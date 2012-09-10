package net.pikzen.formulas;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Common {
	public static ArrayList<LinkedText> mainCategories = new ArrayList<LinkedText>();
	public static ArrayList<LinkedText> physCategories = new ArrayList<LinkedText>();
	public static ArrayList<Item> physConstants = new ArrayList<Item>();
	public static ArrayList<Item> physNuclear = new ArrayList<Item>();
	public static ArrayList<Item> physMechanics = new ArrayList<Item>();
	private static DatabaseHelper db;
	private static SQLiteDatabase sqliteDb;
	
	private static void GetDBData(Context context)
	{
		Cursor c;
		
		/// Physics - Constants
		sqliteDb = db.getReadableDatabase();
		
		c = sqliteDb.rawQuery("SELECT * FROM items WHERE isConstant = 1 ORDER BY category, name ASC", null);
		Log.i("Database query:", "Constants : " + c.getCount() + " elements");
		physConstants = SetupCategories(getItems(c), context);
		c.close();
		
		c = sqliteDb.rawQuery("SELECT * FROM items WHERE category='p_nuclear'", null);
		Log.i("Database query:", "Nuclear Physics : " + c.getCount() + " elements");
		physNuclear = getItems(c);
		c.close();
		
		c = sqliteDb.rawQuery("SELECT * FROM items WHERE category LIKE '%phys_mech%' ORDER BY category, name ASC", null);
		Log.i("Database query:", "Mechanics : " + c.getCount() + " elements");
		physMechanics = SetupCategories(getItems(c), context);
		c.close();
		
		
		sqliteDb.close();
	}
	
	private static ArrayList<Item> getItems(Cursor c)
	{
		ArrayList<Item> list = new ArrayList<Item>();
		if (c.getCount() == 0)
			return list;
		
		while(c.moveToNext())
		{
			Item i = new Item();
			i.SetName(c.getString(DatabaseHelper.KEY_NAME_INDEX));
			i.SetId(c.getString(DatabaseHelper.KEY_ID_INDEX));
			i.SetCategory(c.getString(DatabaseHelper.KEY_CATEGORY_INDEX));
			i.SetDescription(c.getString(DatabaseHelper.KEY_DESCRIPTION_INDEX));
			i.SetImage(c.getString(DatabaseHelper.KEY_IMAGE_INDEX));
			i.SetConstant(c.getInt(DatabaseHelper.KEY_CONSTANT_INDEX) > 0);
			list.add(i);
		}
		
		return list;
	}
	
	private static ArrayList<Item> SetupCategories(ArrayList<Item> items, Context context)
	{
		String categ = "";
		ArrayList<Item> returned = new ArrayList<Item>();
		Resources res = context.getResources();
		for (int i = 0; i < items.size(); i++)
		{
			String currentCategory = items.get(i).getCateg();
			if (!(currentCategory.contentEquals(categ)))
			{
				
				Item itm = new Item();
				itm.SetName("Sec:" + res.getString(res.getIdentifier(currentCategory, "string", context.getPackageName())));
				returned.add(itm);
				
				categ = items.get(i).getCateg();
			}
			returned.add(items.get(i));
		}
		return returned;
	}
	
	public static void Setup(Context c)
	{
		db = DatabaseHelper.getInstance(c, "sciencedata.db");        
        
		mainCategories.clear();
		physCategories.clear();
		physConstants.clear();
		physNuclear.clear();
		physMechanics.clear();
		
		///////////////// Main Categories //////////////////		
		mainCategories.add(new LinkedText(R.drawable.math, R.string.main_mathematics));
		mainCategories.add(new LinkedText(R.drawable.maths, R.string.main_physics));
		mainCategories.add(new LinkedText(R.drawable.math, R.string.main_conversions));
		
		/////////////////     Physics     //////////////////	
		physCategories.add(new LinkedText(R.drawable.p_constants, R.string.p_constants));		
		physCategories.add(new LinkedText(R.drawable.p_nuclear, R.string.p_nuclear));
		physCategories.add(new LinkedText(R.drawable.p_mechanics, R.string.p_mechanics));
		GetDBData(c);
		
		db.close();
		
	}
}
