package dk.kea.class2015.hjalte.BlackJack;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hjalte on 02-10-2015.
 */
public class CreditsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //setContentView(R.layout.activity_credits);
        CreditsView creditsView = new CreditsView(this);
        creditsView.setBackgroundColor(0xff007700);
        creditsView.setKeepScreenOn(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(creditsView);
    }
}
