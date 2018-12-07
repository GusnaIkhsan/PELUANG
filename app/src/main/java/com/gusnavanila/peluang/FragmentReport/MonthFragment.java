package com.gusnavanila.peluang.FragmentReport;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gusnavanila.peluang.R;

import java.util.Calendar;

public class MonthFragment extends Fragment {
    private Button _buttonMonth;
    private ImageButton _buttonNext, _buttonPrev;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private final String[] monthLabel = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private Calendar cal = Calendar.getInstance();
    private int year = cal.get(Calendar.YEAR);
    private int month = cal.get(Calendar.MONTH);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        /*INITIALIZE MONTH PICKER BUTTON*/
        _buttonMonth = rootView.findViewById(R.id.buttonDate_month);
        _buttonMonth.setText(monthLabel[month] + " " + year);
        _buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthYearPickerDialog dialog = new MonthYearPickerDialog();
                dialog.setListener(mDateSetListener);
                dialog.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearSelected, int monthSelected, int day) {
                _buttonMonth.setText(monthLabel[monthSelected-1] + " " + yearSelected);
                month = monthSelected-1;
                year = yearSelected;
            }
        };

        /*INITIALIZE PREV BUTTON*/
        _buttonPrev = rootView.findViewById(R.id.buttonPrev_month);
        _buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((month - 1) < 0) {
                    month = 12;
                    year -= 1;
                }
                _buttonMonth.setText(monthLabel[month-1] + " " + year);
                month -= 1;
            }
        });

        /*INITIALIZE NEXT BUTTON*/
        _buttonNext = rootView.findViewById(R.id.buttonNext_month);
        _buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((month + 1) > 11) {
                    month = -1;
                    year += 1;
                }
                _buttonMonth.setText(monthLabel[month+1] + " " + year);
                month += 1;
            }
        });

        return rootView;
    }


}


