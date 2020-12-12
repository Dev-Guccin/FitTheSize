package gachon.example.fitthesize;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    // get API에 사용될 문자열
    String pantsPart;
    String genderPart;
    String order;

    // 단일검색 사이즈 입력
    EditText pantsSizeTextview;

    // 선택(spinner)
    Spinner pantsPartSpinner;
    Spinner genderPartSpinner;
    Spinner orderSpinner;

    //검색 버튼
    Button btn_search;
    Button btn_AI_search;

    // AI 검색 사이즈
    String saved_length;
    String saved_waist;
    String saved_thigh;
    String saved_rise;
    String saved_hem;

    List<JSONObject> JsonList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // 단일검색 사이즈 입력
        pantsSizeTextview= (EditText)findViewById(R.id.pantsSize);
        // spinner
        pantsPartSpinner =  (Spinner)findViewById(R.id.pantsPart);
        genderPartSpinner = (Spinner)findViewById(R.id.genderPart);
        orderSpinner = (Spinner)findViewById(R.id.orderSpinner);
        // 검색버튼
        btn_search = (Button)findViewById(R.id.gosearchA);
        btn_AI_search = (Button)findViewById(R.id.gosearchB);

        // 성별 선택 Spinner
        genderPartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderPart = (String) parent.getItemAtPosition(position);

                if(genderPart.equals("남자"))
                    genderPart = "m";
                else if(genderPart.equals("여자"))
                    genderPart = "f";
                else
                    genderPart = "a";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // 정렬 선택 Spinner
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order = (String) parent.getItemAtPosition(position);

                if(order.equals("상품평순"))
                    order = "sales";
                else if(order.equals("만족도순"))
                    order = "popular";
                else
                    order = "price";
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
                // aws server
                new JSONTask().execute("http://54.209.118.235:80/"+pantsPart+"/"+pantsSizeTextview.getText().toString()+"/"+order+"/"+genderPart);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // AI 검색 사이즈
        saved_length = PreferenceManager.getString(this,"length");
        saved_waist = PreferenceManager.getString(this,"waist");
        saved_thigh = PreferenceManager.getString(this,"thigh");
        saved_rise = PreferenceManager.getString(this,"rise");
        saved_hem = PreferenceManager.getString(this,"hem");

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

                pantsSizeTextview.setText(PreferenceManager.getString(mContext,pantsPart));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_AI_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //인공지능 요청을 보냄. 이때 사용하는 값은 사용자가 입력한 데이터
                new JSONTask().execute("http://54.209.118.235:80/mysize/"+saved_length+"/"+saved_waist+"/"+saved_thigh+"/"+saved_rise+"/"+saved_hem+"/"+order+"/"+genderPart);
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
            // 가져온데이터를 형식에 맞추어 저장해야한다. 값은 리스트형태의 json형태.[{},{}]
            JSONArray jsonArr = null;
            try {
                jsonArr = new JSONArray(result);//json형태의 배열로 바꾼다.
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonList.clear();//값을 넣기 전에 모든 데이터를 초기화 시켜없애준다.
            for (int i = 0; i < jsonArr.length(); i++) // imgurl을 리스트네 옮겨준다.
            {
                JSONObject jsonObj = null;
                try {
                    jsonObj = jsonArr.getJSONObject(i); // 각 json의 객체를 jsonObj로 옮겨줌
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    //Log.d("test1", jsonObj.getString("id"));
                    JsonList.add(jsonObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Log.d("test2", JsonList.toString());

            //List<String> tmp = new ArrayList<>();
            final List<pants_inf> pants_list = new ArrayList<>();

            Thread t = new Thread() {
                public void run() {
                    URL url = null;
                    URLConnection conn = null;
                    for (int i = 0; i < JsonList.size(); i++) // img url을 리스트에 옮겨준다.
                    {
                        try {
                            //tmp.add(JsonList.get(i).getString("img"));

                            //이미지를 가져오기 위한 코드
                            url = new URL("https:" + JsonList.get(i).getString("img"));
                            conn = url.openConnection();
                            conn.connect();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            //bm.add(BitmapFactory.decodeStream(bis));
                            // 이미지(bmp)랑 id랑 한꺼번에 넣음
                            pants_list.add(new pants_inf(BitmapFactory.decodeStream(bis),
                                    JsonList.get(i).getString("id"),
                                    JsonList.get(i).getString(order),
                                    JsonList.get(i).getString("category"),
                                    order));
                            bis.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
            try {
                // 이미지를 가져오는 작업이 끝나야 메인 스레드가 작동되게,
                // 이 부분이 없으면 밑의 GridView adapter 코드가 먼저 수행됩니다
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            MyAdapter adapter = new MyAdapter (
                    getApplicationContext(),
                    R.layout.row,       // GridView 항목의 레이아웃 row.xml
                    pants_list);          // 바지(Bitmap(이미지) + id) 리스트
                    //tmp);    // 데이터
            GridView gv = (GridView)findViewById(R.id.gridView1);
            gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용
        }
    }

    public void onClicknew1(View view) {
        Intent intent = new Intent(getApplicationContext(), mypage.class);
        startActivity(intent);
    }
}











