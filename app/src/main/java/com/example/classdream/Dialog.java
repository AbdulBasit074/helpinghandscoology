package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;

import com.onesignal.OneSignal;

public class Dialog {

    int i=-1;
    Context contex;
    String data;
    String detail;
    public Dialog(Context contex,String dao,String detail)
    {
        this.contex = contex;
        this.data = dao;
        this.detail = detail;

    }
    public void Dialog_1(String msg1,String msg2)
    {

        final SweetAlertDialog pDialog = new SweetAlertDialog(contex, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(msg1);
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 1, 100) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialog.getProgressHelper().setBarColor(R.color.blue_btn_bg_color);
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(R.color.material_deep_teal_50);
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(R.color.success_stroke_color);
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(R.color.material_deep_teal_20);
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(R.color.material_blue_grey_80);
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(R.color.warning_stroke_color);
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(R.color.success_stroke_color);
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                pDialog.setTitleText(msg2)
                        .setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        if(data.equals("signup"))
                        {
                            Intent start3 = new Intent(contex,LoginActivity_login.class);
                              contex.startActivity(start3);
                            ((LoginActivity_login)contex).finish();
                        }
                        else if(data.equals("verifiedyouraccount"))
                        {

                        }
                        else if(data.equals("forget"))
                        {
                            ((Forget_password)contex).finish();
                        }
                        else if(data.equals("materialfile"))
                        {

                        }
                        else if(data.equals("requestpass"))
                        {
                            Intent start3 = new Intent(contex,classroom.class);
                            contex.startActivity(start3);
                            ((classroom)contex).finish();
                        }
                        else if(data.equals("createclass"))
                        {
                            Intent start3 = new Intent(contex,classroom.class);
                            contex.startActivity(start3);
                            ((classroom)contex).finish();
                        }
                        else if(data.equals("assigmentupload"))
                        {
                            ((Assigments_Upload)contex).finish();
                        }

                    }
                })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();

    }

    public void Dialog_Error(String msg1,String msg2)
    {
        new SweetAlertDialog(contex, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(msg1)
                .setContentText(msg2)
                .show();

    }
    public void Dialog_some_msg(String msg1,String msg2)
    {
        new SweetAlertDialog(contex, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(msg1)
                .setContentText(msg2)
                .setCustomImage(R.drawable.checked)
                .show();

    }
    public void Dialog_4()
    {

    }
    public void Dialog_5()
    {

    }








}
