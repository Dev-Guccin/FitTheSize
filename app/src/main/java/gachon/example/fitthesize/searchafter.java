package gachon.example.fitthesize;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class searchafter extends Activity {
    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.searchafter);
    }

    public void onClickBack2(View view){
        finish();
    }
}