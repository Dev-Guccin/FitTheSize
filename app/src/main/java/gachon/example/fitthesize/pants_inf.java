package gachon.example.fitthesize;

import android.graphics.Bitmap;

public class pants_inf {
    Bitmap bmp;
    String id;
    String order;
    String category;
    String category_group;

    public pants_inf(Bitmap bmp, String id, String order, String category, String categorygroup) {
        this.bmp = bmp;
        this.id = id;
        this.order = order;
        this.category = category;
        this.category_group = categorygroup;
    }
}
