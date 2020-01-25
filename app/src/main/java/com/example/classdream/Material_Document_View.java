package com.example.classdream;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.classdream.R.drawable.pdf;
import static com.example.classdream.R.drawable.powerpoint;
import static com.example.classdream.R.drawable.profile;
import static com.example.classdream.R.drawable.txt;
import static com.example.classdream.R.drawable.word;


public class Material_Document_View extends AppCompatActivity {


    ProgressBar progressBar;
    Integer pageNumber = 0;
    String pdfFileName;
    ImageView icon,download,saw;
    TextView names;
    String uri;
    String extension;
    String extension_S;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__document__view);
        Bundle bundle = getIntent().getExtras();
        uri = bundle.getString("url");
        extension = bundle.getString("extension");
        name =  bundle.getString("name");
        INITVIEW();
        names.setText(name);
        if(extension.equals(".pdf"))
        {
            Glide.with(getApplicationContext()).load(pdf).into(icon);
            extension_S = "pdf";

            //  icn.setImageResource(pdf);
        }
        else if(extension.equals(".doc") || extension.equals(".docx"))
        {
            Glide.with(getApplicationContext()).load(word).into(icon);
            if(extension.equals(".doc"))
            {
                extension_S = "doc";

            }
            if(extension.equals(".docx"))
            {
                extension_S = "docx";
            }
        }
        else if(extension.equals(".ppt") || extension.equals(".pptx"))
        {
            Glide.with(getApplicationContext()).load(powerpoint).into(icon);

            if(extension.equals(".ppt"))
            {
                extension_S = "ppt";
            }
            if(extension.equals(".pptx"))
            {
                extension_S = "pptx";
            }
        }
        else if(extension.equals(".txt"))
        {
            Glide.with(getApplicationContext()).load(txt).into(icon);
            extension_S = "txt";

        }
        else if(extension.equals(".xlsx") || extension.equals(".xlsm") )
        {
            Glide.with(getApplicationContext()).load(R.drawable.excel).into(icon);
            if(extension.equals(".xlsx"))
            {
                extension_S = "xlsx";

            }
            if(extension.equals(".xlsm"))
            {
                extension_S = "xlsm";
            }
        }
        else if(extension.equals(".jpg") ||extension.equals(".jpeg")
                || extension.equals(".png") || extension.equals(".gif"))
        {
            Glide.with(getApplicationContext()).load(profile).into(icon);
            if(extension.equals(".jpj"))
            {
                extension_S = "jpg";

            }
            if(extension.equals(".jpeg"))
            {
                extension_S = "jpeg";
            }
            if(extension.equals(".png"))
            {
                extension_S = "png";

            }
            if(extension.equals(".gif"))
            {
                extension_S = "gif";
            }
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(uri), "application/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        saw.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(uri), "application/" + extension_S);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Dialog dialog = new Dialog(Material_Document_View.this,"materialfile","nothing");
                    dialog.Dialog_Error("Sorry","Please Download and View");

                }
            }

        });

    }
    private void INITVIEW()
    {
    icon = findViewById(R.id.icon_file_view);
    saw = findViewById(R.id.saw);
    download = findViewById(R.id.download);
    names = findViewById(R.id.file_name_view);
    }
}
