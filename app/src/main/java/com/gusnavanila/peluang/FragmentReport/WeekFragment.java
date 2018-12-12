package com.gusnavanila.peluang.FragmentReport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gusnavanila.peluang.AboutApp;
import com.gusnavanila.peluang.Adapter.WeekAdapter;
import com.gusnavanila.peluang.Model.WeekReport;
import com.gusnavanila.peluang.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class WeekFragment extends Fragment {
    private Button _buttonWeek;
    private ImageButton _buttonNext_week, _buttonPrev_week;
    private TextView _textDate;
    private Calendar cal = Calendar.getInstance();
    private int thisWeek, thisYear;
    private String startDate, endDate;

    private RecyclerView _rvTotal;
    private WeekAdapter weekAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<WeekReport> weekReportList;
    private int requestCode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        thisWeek = cal.get(Calendar.WEEK_OF_YEAR);
        thisYear = cal.get(Calendar.YEAR);

        /*INITIALIZE TEXT DATE OF WEEK*/
        setDateRange(0, thisYear);
        _textDate = rootView.findViewById(R.id.textDate);
        _textDate.setText(startDate + " - " + endDate);

        /*INITIALIZE WEEK BUTTON*/
        _buttonWeek = rootView.findViewById(R.id.buttonDate);
        _buttonWeek.setText("Minggu ke " + thisWeek);

        /*INITIALIZE PREV BUTTON OF WEEK*/
        _buttonPrev_week = rootView.findViewById(R.id.buttonPrev);
        _buttonPrev_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisWeek == cal.getActualMinimum(Calendar.WEEK_OF_YEAR)) {
                    thisWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
                    thisYear -= 1;
                } else {
                    thisWeek -= 1;
                }
                setDateRange(-1, thisYear);
                _buttonWeek.setText("Minggu ke " + thisWeek);
                _textDate.setText(startDate + " - " + endDate);
            }
        });

        /*INITIALIZE NEXT BUTTON OF WEEK*/
        _buttonNext_week = rootView.findViewById(R.id.buttonNext);
        _buttonNext_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisWeek == cal.getActualMaximum(Calendar.WEEK_OF_YEAR)) {
                    thisWeek = cal.getActualMinimum(Calendar.WEEK_OF_YEAR);
                    thisYear += 1;
                } else {
                    thisWeek += 1;
                }
                setDateRange(1, thisYear);
                _buttonWeek.setText("Minggu ke " + thisWeek);
                _textDate.setText(startDate + " - " + endDate);
            }
        });

        /*INITIALIZE LAYOUT TOTAL FOR DETAIL PAGE*/
        initDataSet();
        buildRecyclerView(rootView);

        return rootView;
    }

    public void initDataSet() {
        weekReportList = new ArrayList<>();
        weekReportList.add(new WeekReport(165000, 75000, 90000));
    }

    public void buildRecyclerView(View view) {
        _rvTotal = (RecyclerView) view.findViewById(R.id.rvTotal);
        _rvTotal.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        weekAdapter = new WeekAdapter(weekReportList);

        _rvTotal.setLayoutManager(mLayoutManager);
        _rvTotal.setAdapter(weekAdapter);

        weekAdapter.setOnItemClickListener(new WeekAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position, 0);
//                Log.d("RESULT OF TESTCASE", "TIDAK MASUK SINI BUNG");
                Bundle bundle = new Bundle();
                bundle.putInt("income", weekReportList.get(position).income);
                bundle.putInt("outcome", weekReportList.get(position).outcome);
                bundle.putInt("deviation", weekReportList.get(position).deviation);
                Intent intent = new Intent(getActivity(), AboutApp.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void changeItem(int position, int trigger) {
        weekReportList.get(position).changeText(trigger);
        weekAdapter.notifyItemChanged(position);
    }

    public void setDateRange(int value, int year) {
        cal.add(Calendar.WEEK_OF_YEAR, value);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Locale indonesia = new Locale("id", "ID");
        DateFormat df = new SimpleDateFormat("dd MMM yy", indonesia);
        cal.set(Calendar.YEAR, year);
        startDate = df.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        endDate = df.format(cal.getTime());
    }


}

//class ConstraintLayoutListener {
//    private ConstraintLayout clickableArea;
//
//    public ConstraintLayoutListener(ConstraintLayout layout) {
//        this.clickableArea = layout;
//    }
//
//    public void setOnClickListener(View.OnClickListener onClickListener) {
//    }
//}


