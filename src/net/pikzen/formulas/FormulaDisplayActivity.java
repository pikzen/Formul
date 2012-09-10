package net.pikzen.formulas;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class FormulaDisplayActivity extends Activity{

	public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.formula_item);
						
			Resources res = getResources();
			Bundle extras = getIntent().getExtras();
			
			final TextView formula = (TextView)findViewById(R.id.currentFormula);
			formula.setText(extras.getString("formula"));
			
			final TextView tv = (TextView)findViewById(R.id.formulaText);
			tv.setText(Html.fromHtml(extras.getString("desc")));
			
			final ImageView iv = (ImageView)findViewById(R.id.latexFormula);
			if (extras.getString("image") != null)
			{
				iv.setImageResource(res.getIdentifier(extras.getString("image"), "drawable", this.getPackageName()));
			}
		
			
		}
	
	
}
