package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static com.example.classdream.R.color.ACP_CAN;

public class Request_List_adobter extends BaseAdapter {
    Context mContext;
    List<User> list;

    String Class_KEY;
    HashMap<String,String> requ_Lst1;

    public Request_List_adobter(Context mContext,String key, List<User> list,HashMap<String,String> request_list1)
    {
        this.mContext = mContext;
        this.list = list;
        this.Class_KEY = key;
        this.requ_Lst1 = request_list1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext,R.layout.list_items,null);
        TextView name =  v.findViewById(R.id.name_show);
        ImageView image = v.findViewById(R.id.profile_show);

        LinearLayout layout = v.findViewById(R.id.whole);

        User data = list.get(position);
        ///////////////Set Data//////////////////////////
        name.setText(data.NAME);
        Glide.with(mContext).load(data.PROFILE_IMAGE).into(image);
        return v;
    }
}
