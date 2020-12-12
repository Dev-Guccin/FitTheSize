package gachon.example.fitthesize;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    //List<String> data;
    List<pants_inf> data;
    LayoutInflater inf;

    public MyAdapter(Context context, int layout, List<pants_inf> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        //Button iv = (Button)convertView.findViewById(R.id.testbtn);
        //iv.setText(data.get(position));
        ImageView iv = (ImageView)convertView.findViewById(R.id.testimg);
        iv.setImageBitmap(data.get(position).bmp);

        // 이미지 누르면 넘어가게 ~
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { context.startActivity( new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://store.musinsa.com/app/goods/"+data.get(position).id)).addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return convertView;
    }
}
