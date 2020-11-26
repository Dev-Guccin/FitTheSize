package gachon.example.fitthesize;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClicknew1(View view) {
        Intent intent = new Intent(getApplicationContext(), mypage.class);
        startActivity(intent);
    }

    public void onClicknew2(View view) {
        Intent intent = new Intent(getApplicationContext(), searchafter.class);
        startActivity(intent);
    }
}













