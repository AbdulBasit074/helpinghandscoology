package com.example.classdream;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


@SuppressLint("ValidFragment")
public class Simle_Post extends AppCompatDialogFragment {
    EditText Post_title;
    EditText Post_instruction;
    Dialog_post_listner listner ;
    String title1,button;
    String insr;

    @SuppressLint("ValidFragment")
    public Simle_Post(String dadata, String dButton,String write)
    {
        title1 = dadata ;
        button = dButton;
        insr = write;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.simple_post_recucler,null);
        builder.setView(view)
                .setTitle(title1)
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ttl = Post_title.getText().toString();
                String ins = Post_instruction.getText().toString();
                listner.ApplyText(ttl,ins);
            }
        });

        Post_title = view.findViewById(R.id.title);
        Post_instruction = view.findViewById(R.id.instruction);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (Dialog_post_listner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement Dialog");
        }
    }
    public interface Dialog_post_listner{
        void ApplyText(String title,String instruction);
    }

}
