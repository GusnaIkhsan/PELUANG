package com.gusnavanila.peluang;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    RecyclerView _recycleView, _recycleView2;
    DataHarianAdapter _dataHarianAdapter;
    DataTransaksiHarianAdapter _dataTransaksiHarianAdapter;
    ArrayList<DataHarian> _dataHarian = new ArrayList<DataHarian>();
    ArrayList<DataTransaksiHarian> _dataTransaksiHarian = new ArrayList<DataTransaksiHarian>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        _dataHarian.add(new DataHarian("10 Sep 2018",15000000,100000));
        _dataHarian.add(new DataHarian("15 Sep 2018",3,100000));
        _dataHarian.add(new DataHarian("17 Sep 2018",5,100000));
        _dataHarian.add(new DataHarian("19 Sep 2018",300000,100000));
        _dataHarian.add(new DataHarian("20 Sep 2018",300000,100000));
        _dataHarian.add(new DataHarian("22 Sep 2018",300000,100000));
        _dataHarian.add(new DataHarian("30 Sep 2018",300000,100000));

        _recycleView = rootView.findViewById(R.id.recyclerView);
        _dataHarianAdapter = new DataHarianAdapter(getActivity(),_dataHarian);
        _recycleView.setAdapter(_dataHarianAdapter);
        _recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Sarapan",15000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Makan Siang",15000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Transportasi","Naik Lan",5000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Sarapan",15000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Belanja","Jajan",10000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Bayar Kas","Kas Org",5000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Sarapan",15000));
        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Makan Siang",15000));

        _recycleView2 = rootView.findViewById(R.id.recyclerView2);
        _dataTransaksiHarianAdapter = new DataTransaksiHarianAdapter(getActivity(),_dataTransaksiHarian);
        _recycleView2.setAdapter(_dataTransaksiHarianAdapter);
        _recycleView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
