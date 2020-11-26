package gachon.example.fitthesize;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class mypage extends Activity {
    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mypage);
    }

    public void onClickBack(View view){
        finish();
    }
}
