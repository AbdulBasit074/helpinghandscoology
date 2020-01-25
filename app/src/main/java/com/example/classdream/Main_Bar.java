package com.example.classdream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;

import java.util.ArrayList;

public class Main_Bar {



public void  initUI(NavigationTabBar ntb5, Resources sd, Activity activity,int Model_no)
{
    final NavigationTabBar ntbSample5 = ntb5;
    final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
    models5.add(
            new NavigationTabBar.Model.Builder(
                    sd.getDrawable(R.drawable.ic_fifth), Color.BLUE
            ).title("Class Room").badgeTitle("icon").
                    build()
    );
    models5.add(
            new NavigationTabBar.Model.Builder(
                    sd.getDrawable(R.drawable.ic_first), Color.BLUE
            ).title("Dictionary").build()
    );
    models5.add(
            new NavigationTabBar.Model.Builder(
                    sd.getDrawable(R.drawable.ic_fourth), Color.BLUE
            ).title("Calculator").build()
    );
    models5.add(
            new NavigationTabBar.Model.Builder(
                    sd.getDrawable(R.drawable.ic_sixth), Color.BLUE
            ).title("Unit Converter").build()
    );
    ntbSample5.setModels(models5);
    ntbSample5.setModelIndex(Model_no, true);
    ntbSample5.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
        @Override
        public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
        }
        @Override
        public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {

            if(index==0)
            {

            }
            if(index==1)
            {
                final Intent i1 = new Intent(activity, dictionary.class);
                activity.startActivity(i1);
                activity.finish();

            }
            if(index==2)
            {
                final Intent i2 = new Intent(activity,ScientificCal.class);
                activity.startActivity(i2);
                activity.finish();
            }
            if(index==3)
            {
                final Intent i3 = new Intent(activity, UnitCoverter.class);
                activity.startActivity(i3);
                activity.finish();
            }

        }
    });






















}




}
