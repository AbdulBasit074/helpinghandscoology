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
public class Dialog_Text_2 extends AppCompatDialogFragment {
    EditText class_name;
    EditText class_section;
    EditText subject;
    Dialog_text_listner_2 listner2 ;
    String title,button;
    int call_radio;
    @SuppressLint("ValidFragment")
    public Dialog_Text_2(String dadata, String dButton, int call_radio)
    {
        title = dadata ;
        button = dButton;
        this.call_radio = call_radio;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_2,null);
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
                        String Name = class_name.getText().toString();
                        String Section = class_section.getText().toString();
                        String sSUBJECT = subject.getText().toString();

                listner2.ApplyText(Name,Section,sSUBJECT,call_radio);
            }
        });

        class_name = view.findViewById(R.id.data0);
        class_section = view.findViewById(R.id.data1);
        subject = view.findViewById(R.id.data2);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner2 = (Dialog_text_listner_2) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement Dialog");
        }
    }

    public interface Dialog_text_listner_2{
        void ApplyText(String name,String section1,String subject, int call_raio);

    }


}
