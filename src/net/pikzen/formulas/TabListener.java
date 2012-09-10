package net.pikzen.formulas;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.app.ActionBar.Tab;

public class TabListener<T extends Fragment> implements ActionBar.TabListener 
{
	private android.support.v4.app.Fragment mFragment;
	private final SherlockFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	
	public TabListener(SherlockFragmentActivity activity, String tag, Class<T> clz)
	{
		mActivity = activity;
		mTag = tag;
		mClass = clz;
	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        mActivity.getSupportFragmentManager().beginTransaction()
        									 .add(android.R.id.content, mFragment, mTag)
        									 .commit();
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
	
}