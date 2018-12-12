package com.gusnavanila.peluang.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gusnavanila.peluang.Model.WeekReport;
import com.gusnavanila.peluang.R;

import java.util.ArrayList;

public class WeekAdapter extends RecyclerView.Adapter <WeekAdapter.WeekViewHolder> {
    ArrayList<WeekReport> weekReportList;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public WeekAdapter(ArrayList<WeekReport> weekReportList) {
        this.weekReportList = weekReportList;
    }

    /*THIS IS ABSTRACT METHOD FROM OVERRIDE*/
    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_total_week, parent, false);
        WeekViewHolder kontakViewHolder = new WeekViewHolder(itemView, mListener);
        return kontakViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        WeekReport weekReport = weekReportList.get(position);
        holder._incomeWeek.setText("Rp " + weekReport.income + ",00");
        holder._outcomeWeek.setText("Rp " + weekReport.outcome + ",00");
        holder._deviationWeek.setText("Rp " + weekReport.deviation + ",00");
    }

    @Override
    public int getItemCount() {
        return weekReportList.size();
    }

    public static class WeekViewHolder extends RecyclerView.ViewHolder {
        TextView _incomeWeek, _outcomeWeek, _deviationWeek;

        public WeekViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            _incomeWeek = (TextView) itemView.findViewById(R.id.income_week_test);
            _outcomeWeek = (TextView) itemView.findViewById(R.id.outcome_week_test);
            _deviationWeek = (TextView) itemView.findViewById(R.id.deviation_week_test);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
