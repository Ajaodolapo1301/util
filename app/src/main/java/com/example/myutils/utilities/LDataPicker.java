package com.example.myutils.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class LDataPicker extends DatePickerDialog {

    int maxYear;
    int maxMonth;
    int maxDay;
    String title;

    public LDataPicker(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, String title) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        this.title = title;
        setTitle(title);

    }

    public void setMaxDate(long maxDate) {
        getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        super.onDateChanged(view, year, monthOfYear, dayOfMonth);
        setTitle(title);

    }


}
