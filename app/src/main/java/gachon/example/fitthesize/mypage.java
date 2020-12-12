package gachon.example.fitthesize;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class mypage extends Activity {
    private EditText EditText_length;
    private EditText EditText_waist;
    private EditText EditText_thigh;
    private EditText EditText_rise;
    private EditText EditText_hem;

    private Button saveBtn;

    private Context mContext;

    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mypage);
        mContext = this;

        EditText_length = (EditText)findViewById(R.id.user_length);
        EditText_waist = (EditText)findViewById(R.id.user_waist);
        EditText_thigh = (EditText)findViewById(R.id.user_thigh);
        EditText_rise = (EditText)findViewById(R.id.user_rise);
        EditText_hem = (EditText)findViewById(R.id.user_hem);

        saveBtn = (Button)findViewById(R.id.save_btn);

        String length = PreferenceManager.getString(this,"length");
        String waist = PreferenceManager.getString(this,"waist");
        String thigh = PreferenceManager.getString(this,"thigh");
        String rise = PreferenceManager.getString(this,"rise");
        String hem = PreferenceManager.getString(this,"hem");

        EditText_length.setText(length);
        EditText_waist.setText(waist);
        EditText_thigh.setText(thigh);
        EditText_rise.setText(rise);
        EditText_hem.setText(hem);

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PreferenceManager.setString(mContext,"length",EditText_length.getText().toString());
                PreferenceManager.setString(mContext,"waist",EditText_waist.getText().toString());
                PreferenceManager.setString(mContext,"thigh",EditText_thigh.getText().toString());
                PreferenceManager.setString(mContext,"rise",EditText_rise.getText().toString());
                PreferenceManager.setString(mContext,"hem",EditText_hem.getText().toString());

                finish();
            }
        });
    }

    public void onClickBack(View view){
        finish();
    }
}