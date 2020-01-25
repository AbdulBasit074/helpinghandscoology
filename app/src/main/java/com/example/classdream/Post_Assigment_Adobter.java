package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class Post_Assigment_Adobter extends RecyclerView.Adapter<Post_Assigment_Adobter.ViewHolder> {
    private static final int VIEW_END = 1;
    private List<FILE_INFORMATION_ASSIGMENTS> mDataSet;
    Context c;
    String Key;
    boolean teacher;
    Post_Assigment_Adobter(List<FILE_INFORMATION_ASSIGMENTS> dataSet,String k,boolean tec, Context f)
    {
        mDataSet = dataSet;
        Key = k;
        teacher = tec;
        this.c=f;
    }

    @Override
    public Post_Assigment_Adobter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_assigment_recyclerview, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position)
    {
            return VIEW_END;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FILE_INFORMATION_ASSIGMENTS file = mDataSet.get(position);

        if(file.FILE_EXTENSION.equals(".pdf"))
        {
            holder.icon_file.setImageDrawable(c.getResources().getDrawable(R.drawable.pdf));
        }
        else if(file.FILE_EXTENSION.equals(".doc") || file.FILE_EXTENSION.equals(".docx"))
        {
            holder.icon_file.setImageDrawable(c.getResources().getDrawable(R.drawable.word));
        }
        else if(file.FILE_EXTENSION.equals(".ppt") || file.FILE_EXTENSION.equals(".pptx"))
        {
            holder.icon_file.setImageDrawable(c.getResources().getDrawable(R.drawable.powerpoint));
        }
        else if(file.FILE_EXTENSION.equals(".txt"))
        {
            holder.icon_file.setImageDrawable(c.getResources().getDrawable(R.drawable.txt));
        }
        else if(file.FILE_EXTENSION.equals(".jpg") || file.FILE_EXTENSION.equals(".jpeg")
                || file.FILE_EXTENSION.equals(".png") || file.FILE_EXTENSION.equals(".gif"))
        {
            holder.icon_file.setImageDrawable(c.getResources().getDrawable(R.drawable.profile));
        }
        holder.file_names.setText(file.FILE_NAME);
        holder.time.setText(file.FILE_UPLOAD_TIME);
        holder.date.setText("Open : "+file.FILE_UPLOAD_DATE);
        holder.title.setText(file.FILE_TITLE);
        holder.inst.setText(file.FILE_INSTRUCTION);
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent start3 ;
                start3  = new Intent(c,teacher_assigment_page.class);
                start3.putExtra("key",Key);
                start3.putExtra("teacher",teacher);
                start3.putExtra("url",file.FILE_DOWNLOAD_URL);
                start3.putExtra("extension",file.FILE_EXTENSION);
                start3.putExtra("name",file.FILE_NAME);
                start3.putExtra("date",file.FILE_UPLOAD_DATE);
                start3.putExtra("time",file.FILE_UPLOAD_TIME);
                start3.putExtra("title",file.FILE_TITLE);
                start3.putExtra("instruction",file.FILE_INSTRUCTION);
                start3.putExtra("assigment_key",file.ASSIGMENTS_KEY);

                c.startActivity(start3);

            }
        });
       // holder.last_date.setText(file.);
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Inner Class for a recycler view
     */
    class ViewHolder extends RecyclerView.ViewHolder {
    ImageView icon_file;
    TextView file_names;
    TextView date;
    TextView last_date;
    LinearLayout lay;
    TextView time;
    TextView title;
    TextView inst;


        ViewHolder(View v) {
            super(v);
            icon_file = itemView.findViewById(R.id.file_image);
            file_names = (TextView) itemView.findViewById(R.id.file_name);
            last_date =  (TextView) itemView.findViewById(R.id.last_date_1);
            date = (TextView)  itemView.findViewById(R.id.post_date_1);
            time =  (TextView) itemView.findViewById(R.id.post_time_1);
            title = (TextView) itemView.findViewById(R.id.post_title_1);
            inst = (TextView) itemView.findViewById(R.id.post_inst_1);
            lay =    itemView.findViewById(R.id.assigment_press);
        }
    }
}
