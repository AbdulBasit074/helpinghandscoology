package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

class MaterialAdobter extends RecyclerView.Adapter<MaterialAdobter.ViewHolder> {
    private static final int VIEW_END = 1;
    private List<FILE_INFORMATION> mDataSet;
    Context c;
    View delete;
    MaterialAdobter(List<FILE_INFORMATION> dataSet,Context f,View del) {
        mDataSet = dataSet;
        this.c=f;
        delete = del;

    }

    @Override
    public MaterialAdobter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.materail_items, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position)
    {
            return VIEW_END;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FILE_INFORMATION file = mDataSet.get(position);
        holder.filename.setText(file.FILE_NAME);
        holder.filesize.setText(file.FILE_SIZE+" Bytes");
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


        holder.mview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent start3 = new Intent(c,Material_Document_View.class);
                start3.putExtra("url",file.FILE_DOWNLOAD_URL);
                start3.putExtra("extension",file.FILE_EXTENSION);
                start3.putExtra("name",file.FILE_NAME);

                c.startActivity(start3);
            }
        });
        holder.mview.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                v.setBackgroundColor(c.getResources().getColor(R.color.click));

                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1)
                    {
                        delete.setVisibility(View.INVISIBLE);
                        v.setBackgroundColor(c.getResources().getColor(R.color.again_click));
                    }
                });

                return false;
            }
        });









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
    TextView filename;
    TextView filesize;
    View mview;

        ViewHolder(View v) {
            super(v);
            filename = (TextView) itemView.findViewById(R.id.file_name);
            filesize = (TextView) itemView.findViewById(R.id.file_size);
            icon_file = (ImageView) itemView.findViewById(R.id.icon_file);
            mview = v;
        }
    }
}
