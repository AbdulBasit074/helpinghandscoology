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
public class Dialog_Text extends AppCompatDialogFragment {
    EditText text;
    Dialog_text_listner listner ;
    String title,button;
    int call_radio;
    @SuppressLint("ValidFragment")
    public Dialog_Text(String dadata, String dButton,int call_radio)
    {
        title = dadata ;
        button = dButton;
        this.call_radio = call_radio;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);
        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        String Name = text.getText().toString() ;
                        listner.ApplyText(Name,call_radio);
            }
        });

        text = view.findViewById(R.id.data);
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (Dialog_text_listner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement Dialog");
        }
    }

    public interface Dialog_text_listner{
        void ApplyText(String name,int call_raio);
    }


}
