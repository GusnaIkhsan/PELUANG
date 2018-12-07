package com.gusnavanila.peluang.FragmentReport;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.gusnavanila.peluang.R;

import java.util.Calendar;

public class WeekFragment extends Fragment {
    private Button _buttonWeek;
    private ImageButton _buttonNext_week, _buttonPrev_week;
    private Calendar cal = Calendar.getInstance();
    private int thisWeek, thisYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        thisWeek = cal.get(Calendar.WEEK_OF_YEAR);
        thisYear = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        cal.set(Calendar.YEAR, 2015);
//        cal.set(Calendar.MONTH, Calendar.DECEMBER);
//        cal.set(Calendar.DAY_OF_MONTH, 31);
//
//        int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
//        int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
//        int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;

        /*INITIALIZE WEEK BUTTON*/
        _buttonWeek = rootView.findViewById(R.id.buttonDate);
        _buttonWeek.setText("Minggu ke " + thisWeek);

        /*INITIALIZE PREV BUTTON OF WEEK*/
        _buttonPrev_week = rootView.findViewById(R.id.buttonPrev);
        _buttonPrev_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisWeek == cal.getActualMinimum(Calendar.WEEK_OF_YEAR)) {
                    thisYear -= 1;
                    cal.set(Calendar.YEAR, thisYear);
                    thisWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
                } else {
                    thisWeek -= 1;
                }
                _buttonWeek.setText("Minggu ke " + thisWeek);
            }
        });

        /*INITIALIZE NEXT BUTTON OF WEEK*/
        _buttonNext_week = rootView.findViewById(R.id.buttonNext);
        _buttonNext_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisWeek == cal.getActualMaximum(Calendar.WEEK_OF_YEAR)) {
                    thisYear += 1;
                    cal.set(Calendar.YEAR, thisYear);
                    thisWeek = cal.getActualMinimum(Calendar.WEEK_OF_YEAR);
                } else {
                    thisWeek += 1;
                }
                _buttonWeek.setText("Minggu ke " + thisWeek);
                Log.d("Max week", String.valueOf(cal.getActualMaximum(Calendar.WEEK_OF_YEAR)));
            }
        });

        return rootView;
    }
}
