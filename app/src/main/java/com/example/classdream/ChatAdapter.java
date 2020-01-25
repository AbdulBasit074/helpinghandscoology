package com.example.classdream;

import android.content.Context;
import android.net.Uri;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final int CHAT_END = 1;
    private static final int CHAT_START = 2;

    private List<Chat> mDataSet;
    private String mId;
    public String profile;
    public String name;
    public String time;
   public  FirebaseAuth mAuth;
   public FirebaseUser USER;
   Context c;


    /**
     * Called when a view has been clicked.
     *
     * @param dataSet Message list
     * @param id      Device id
     */
    ChatAdapter(List<Chat> dataSet, String id,String profile,String name,String time,Context f) {
        mDataSet = dataSet;
        mId = id;
        this.name = name;
        this.time = time;
        this.profile = profile;
        mAuth = FirebaseAuth.getInstance();
        USER = mAuth.getCurrentUser();
        this.c=f;

    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == CHAT_END) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_end, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_start, parent, false);
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position).id.equals(USER.getUid()))
        {
            return CHAT_END;
        }

        return CHAT_START;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = mDataSet.get(position);
        holder.mTextView.setText(chat.getMessage());
        if(USER.getUid()==mId)
        {
            holder.mname.setText("You");
        }
        else
        {
            holder.mname.setText(chat.name);
        }

        Uri uri = Uri.parse(chat.profile);

        Glide.with(c).load(uri).placeholder(R.drawable.loading).into(holder.mprofile);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Inner Class for a recycler view
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mname;
       ImageView mprofile;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.tvMessage);
            mname = (TextView) itemView.findViewById(R.id.tvMessage1);
            mprofile = (ImageView) itemView.findViewById(R.id.ed_pr);


        }
    }
}
