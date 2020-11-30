package gachon.example.fitthesize;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    String pantsPart;
    EditText pantsSizeTextview;
    TextView resultTextview;
    Spinner pantsPartSpinner;
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pantsSizeTextview= (EditText)findViewById(R.id.pantsSize);
        resultTextview = (TextView)findViewById(R.id.searchResult);
        resultTextview.setMovementMethod(new ScrollingMovementMethod());
        pantsPartSpinner =  (Spinner)findViewById(R.id.pantsPart);
        btn_search = (Button)findViewById(R.id.gosearchA);

        // 옷 부위 선택 spinner
        pantsPartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pantsPart = (String) parent.getItemAtPosition(position);

                if (pantsPart.equals("총장"))
                    pantsPart="length";
                else if (pantsPart.equals("허리단면"))
                    pantsPart="waist";
                else if (pantsPart.equals("허벅지 단면"))
                    pantsPart="thigh";
                else if (pantsPart.equals("밑위"))
                    pantsPart="rise";
                else if (pantsPart.equals("밑단 단면"))
                    pantsPart="hem";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 검색 버튼 작동
        /**
         * 본인 PC IP 주소 입력
         * */
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://192.168.35.113:3000/"+pantsPart+"/"+pantsSizeTextview.getText().toString());
            }
        });

    }

    // 서버 통신부
   public class JSONTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * @param result
         * result로 결과 json 객체배열이 들어갑니다
         * 일단은 메인화면에 json 결과가 표시되게 해두었습니다
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 일단은 메인화면에 json 결과가 표시되게 해두었습니다
            resultTextview.setText(result);
        }
    }

    public void onClicknew1(View view) {
        Intent intent = new Intent(getApplicationContext(), mypage.class);
        startActivity(intent);
    }

    public void onClicknew2(View view) {
        Intent intent = new Intent(getApplicationContext(), searchafter.class);
        startActivity(intent);
    }

    public class pants{
    }
}













