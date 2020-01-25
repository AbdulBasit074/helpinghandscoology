package com.example.classdream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.zip.Inflater;

public class Adobter_recycler extends RecyclerView.Adapter<Adobter_recycler.viewholder> {

    private List<Class_Info> Class_data;
    Context c;
    FirebaseDatabase database;
    DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser USER;
    Activity activity;
    int tog;


    public Adobter_recycler(List<com.example.classdream.Class_Info> class_Info, Context c,Activity activ,int tog) {
        Class_data = class_Info;
        this.c = c;
        activity = activ;
        this.tog = tog;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_view,viewGroup,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
    String image_res = Class_data.get(i).getImage();
    String title_res = Class_data.get(i).getClass_Name();
    String class_key = Class_data.get(i).getClass_Key();

    viewholder.mview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           SEARCH_CLASS(v);
            Toast.makeText(c,"click"+i+class_key,Toast.LENGTH_LONG).show();
        }

        private void SEARCH_CLASS(View v)
        {
            //FireBase Setup
            mAuth = FirebaseAuth.getInstance();
            USER = mAuth.getCurrentUser();
            database = FirebaseDatabase.getInstance();
            ////////////////////////////////////////////////////////////////////////////////////////
            mDatabaseRef = database.getReference().child("CLASSES");
            mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.hasChild(class_key))
                    {
                        Intent ope;
                        if(tog==0)
                        {
                             ope = new Intent(v.getContext(),com.example.classdream.Class_Home_STUDENT.class);
                             ope.putExtra("key",class_key);
                             v.getContext().startActivity(ope);
                        }
                        if(tog==1)
                        {
                             ope = new Intent(v.getContext(),com.example.classdream.Class_Home.class);
                             ope.putExtra("key",class_key);
                             v.getContext().startActivity(ope);
                        }

                        activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        Toast.makeText(c,c.toString(),Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                            Toast.makeText(c,"NO CLASS FOUND",Toast.LENGTH_LONG).show();
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    });

    viewholder.setData(image_res,title_res,class_key);
    }

    @Override
    public int getItemCount() {
        return Class_data.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        ImageView class_image;
        TextView class_title;
        TextView class_keys;
        View mview;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            class_image  = itemView.findViewById(R.id.class_image);
            class_title = itemView.findViewById(R.id.class_title);
            class_keys = itemView.findViewById(R.id.class_key);

            mview = itemView;
        }
        private void setData(String img,String title,String key)
        {
               // Glide.with(c).load(img).placeholder(R.drawable.loading).into(class_image);
                class_title.setText(title);
                class_keys.setText(key);
        }

    }





}
