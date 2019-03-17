package com.example.mimoh.apidemoapp.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mimoh.apidemoapp.Data;
import com.example.mimoh.apidemoapp.R;
import com.squareup.picasso.Picasso;

public class Home extends Fragment {

    TextView TVname,TVinfo,TVlocation,TVlink,TVhomeuni,TVhomestream,TVhomeyear,TVhomedesg,TVhomeorg,TVhomeprostart,TVhomeproend;
    ImageView IVprofile,IVhomecer;
    ImageButton IBeduedit,IBedudelete;
    Button BTNedu,BTNpro,BTNach;
    Data data;
    LinearLayout LLedu,LLpro;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TVname = view.findViewById(R.id.TVname);
        TVinfo = view.findViewById(R.id.TVinfo);
        TVlocation = view.findViewById(R.id.TVlocation);
        TVlink = view.findViewById(R.id.TVlink);
        IVprofile = view.findViewById(R.id.IVprofile);
        TVhomeuni = view.findViewById(R.id.TVhomeuni);
        TVhomestream = view.findViewById(R.id.TVhomestream);
        TVhomeyear = view.findViewById(R.id.TVhomeyear);
        TVhomedesg = view.findViewById(R.id.TVhomedesg);
        TVhomeorg = view.findViewById(R.id.TVhomeorg);
        TVhomeprostart = view.findViewById(R.id.TVhomeprostart);
        TVhomeproend = view.findViewById(R.id.TVhomeproend);
        IVhomecer = view.findViewById(R.id.IVhomecer);
        BTNedu = view.findViewById(R.id.BTnedu);
        BTNpro = view.findViewById(R.id.BTnpro);
        BTNach = view.findViewById(R.id.BTnach);
        LLedu = view.findViewById(R.id.LLedu);
        LLpro = view.findViewById(R.id.LLpro);
        IBeduedit = view.findViewById(R.id.IBeduedit);
        IBedudelete = view.findViewById(R.id.IBedudelete);

        LLedu.setVisibility(View.VISIBLE);
        LLpro.setVisibility(View.GONE);

        BTNedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLedu.setVisibility(View.VISIBLE);
                LLpro.setVisibility(View.GONE);
            }
        });

        BTNpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLedu.setVisibility(View.GONE);
                LLpro.setVisibility(View.VISIBLE);
            }
        });

        IBeduedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Education education = new Education();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data);
                bundle.putBoolean("edit",true);
                education.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.Flhome,education)
                        .commit();
            }
        });

        IBedudelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Conformation Message");
                builder.setMessage("Are you really want to delete this record?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.setImage_path(null);
                        data.setStart_year(null);
                        data.setEnd_year(null);
                        data.setDegree(null);
                        data.setOrganisation(null);
                        data.setLocation(null);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data",data);
                        bundle.putBoolean("edit",true);
                        Education education = new Education();
                        education.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.Flhome,education)
                                .commit();
                        Toast.makeText(getContext(),"No education data found,Please add.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No",null);
                builder.create().show();
            }
        });


        data = (Data) getArguments().getSerializable("data");

        TVname.setText(data.getName());
        TVinfo.setText(data.getLocation() + " | " + data.getOrganisation());
        TVlocation.setText(data.getLocation());
        TVlink.setText(data.getLinks());

        Picasso.get().load("http://139.59.65.145:9090/user/personaldetail/profilepic/" + data.getUid())
                .resize(140,140).into(IVprofile);

        TVhomeuni.setText(data.getOrganisation());
        TVhomestream.setText(data.getDegree());
        TVhomeyear.setText(data.getStart_year() + " - " + data.getEnd_year());
        TVhomedesg.setText(data.getDesignation());
        TVhomeorg.setText("at " + data.getOrganisation1());
        TVhomeprostart.setText("From " + data.getStart_date());
        TVhomeproend.setText("To " + data.getEnd_date());


        return view;
    }

}
