package com.gusnavanila.peluang;

//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PreferencesFragment extends Fragment {

    private Button _buttonEditKategori, _buttonEditTema, _buttonAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);

        /*Initialize Component*/
        this._buttonEditKategori = rootView.findViewById(R.id.buttonEditKategori);
        this._buttonEditTema = rootView.findViewById(R.id.buttonEditTema);
        this._buttonAbout = rootView.findViewById(R.id.buttonAbout);

        /*Action handler*/
        this._buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutApp = new Intent(getActivity(),AboutApp.class);
                startActivity(aboutApp);
            }
        });

        return rootView;
    }
}
