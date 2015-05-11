package com.hustascii.goldpic.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hustascii.goldpic.R;
//import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by wei on 15-5-3.
 */
public class MaterialProgressDialog extends ProgressDialog {
    private View view;
    private ProgressWheel mProgressWheel;
    public MaterialProgressDialog(Context context) {
        super(context, android.R.style.Theme_Translucent);
// TODO Auto-generated constructor stub
        init();
    }
    public MaterialProgressDialog(Context context, int theme) {
        super(context, theme);
// TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        view = LayoutInflater.from(getContext()).inflate(
                R.layout.material_progress_dialog, null);
        mProgressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
    }

    public void setBarColor(int barColor) {
        mProgressWheel.setBarColor(barColor);
    }

    public void setRimColor(int rimColor) {
        mProgressWheel.setRimColor(rimColor);
    }
    public void setBackgroundColor(int backgroundColor) {
        mProgressWheel.setBackgroundColor(backgroundColor);
    }
    public void setBackgroundResource(int resid) {
        mProgressWheel.setBackgroundResource(resid);
    }
    public void setBarWitdh(int barWidth) {
        mProgressWheel.setBarWidth(barWidth);
    }
    public void setCircleRadius(int circleRadius) {
        mProgressWheel.setCircleRadius(circleRadius);
    }
    @Override
    public void show() {
// TODO Auto-generated method stub
        super.show();
        setContentView(view);
        mProgressWheel.spin();
    }
    @Override
    public void dismiss() {
// TODO Auto-generated method stub
        mProgressWheel.stopSpinning();
        super.dismiss();
    }
}
