package com.example.myutils.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.libramotors.libmot.R;

public class EndDrawerToggle implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private DrawerArrowDrawable arrowDrawable;
    private AppCompatImageButton toggleButton;
    private String openDrawerContextDesc;
    private String closeDrawerContextDesc;

    public EndDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes){
        this.drawerLayout = drawerLayout;
        this.openDrawerContextDesc = activity.getString(openDrawerContentDescRes);
        this.closeDrawerContextDesc = activity.getString(closeDrawerContentDescRes);

        arrowDrawable = new DrawerArrowDrawable(toolbar.getContext());
        arrowDrawable.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_END);
        arrowDrawable.setColor(Color.WHITE);//setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        toggleButton = new AppCompatImageButton(toolbar.getContext(),null, R.attr.toolbarNavigationButtonStyle);
        toolbar.addView(toggleButton, new Toolbar.LayoutParams(GravityCompat.END));
        toggleButton.setImageDrawable(arrowDrawable);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });



    }

    public void syncState(){
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            setPosition(1f);
        }else {
            setPosition(0f);
        }
    }

    public void toggle(){
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    public void setPosition(float position){
        if (position == 1f){
            arrowDrawable.setVerticalMirror(true);
            toggleButton.setContentDescription(closeDrawerContextDesc);
        }else {

            arrowDrawable.setVerticalMirror(false);
            toggleButton.setContentDescription(openDrawerContextDesc);
        }

        arrowDrawable.setProgress(position);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        setPosition(Math.min(1f, Math.max(0,slideOffset)));
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        setPosition(1f);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        setPosition(0f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
