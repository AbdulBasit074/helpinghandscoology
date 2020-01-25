package com.example.classdream;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

class Post_Adobter extends RecyclerView.Adapter<Post_Adobter.ViewHolder> {
    private static final int VIEW_END = 1;
    private List<Simple_Post> mDataSet;
    Context c;
    Post_Adobter(List<Simple_Post> dataSet, Context f)
    {
        mDataSet = dataSet;
        this.c=f;
    }

    @Override
    public Post_Adobter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recyclerview, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position)
    {
            return VIEW_END;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Simple_Post post = mDataSet.get(position);


        Glide.with(c.getApplicationContext()).load(post.teacher_profile).into(holder.pro_teacher);

        holder.name_teacher.setText(post.teacher_name);
        holder.time.setText(post.time);
        holder.date.setText(post.date);
        holder.title.setText(post.title);
        holder.inst.setText(post.instruction);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Inner Class for a recycler view
     */
    class ViewHolder extends RecyclerView.ViewHolder {
    ImageView pro_teacher;
    TextView name_teacher;
    TextView date;
    TextView time;
    TextView title;
    TextView inst;


        ViewHolder(View v) {
            super(v);
            pro_teacher =  itemView.findViewById(R.id.teacher_image);
            name_teacher = (TextView) itemView.findViewById(R.id.teacher_name);
            date = (TextView)  itemView.findViewById(R.id.post_date);
            time =  (TextView) itemView.findViewById(R.id.post_time);
            title = (TextView) itemView.findViewById(R.id.teacher_post_title);
            inst = (TextView) itemView.findViewById(R.id.teacher_post);

        }
    }
}
