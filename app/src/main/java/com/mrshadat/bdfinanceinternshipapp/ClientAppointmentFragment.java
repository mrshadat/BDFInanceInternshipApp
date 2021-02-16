package com.mrshadat.bdfinanceinternshipapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentClientAppointmentBinding;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentHomeBinding;
import com.mrshadat.bdfinanceinternshipapp.models.Appointment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientAppointmentFragment extends Fragment {

    FragmentClientAppointmentBinding clientAppointmentBinding;

    private long days;
    private String dob = "";
    Appointment appointment;

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(i, i1, i2);
                    //calendar.set(0, 0, 0, hour, minute);
                    Date date = calendar.getTime();
                    days = (new Date().getTime() - date.getTime()) / 86400000;
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm");
                    /*
                     * dd/MM/yyyy
                     * DD - > day of the year
                     * MMM - > OCT, NOV
                     * MMMM -> OCTOBER
                     *
                     * for time -> hh:mm a
                     * for time -> HH:mm a
                     * */
                    dob = sdf.format(date);

                    //dob = i2+"/"+(i1+1)+"/"+i;
                    clientAppointmentBinding.buttonDateAppointment.setText(dob);
                }
            };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientAppointmentFragment newInstance(String param1, String param2) {
        ClientAppointmentFragment fragment = new ClientAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        clientAppointmentBinding = FragmentClientAppointmentBinding.inflate(LayoutInflater.from(getActivity()));
        return clientAppointmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference roofref = FirebaseDatabase.getInstance().getReference().child("appointment");

        clientAppointmentBinding.buttonDateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog =
                        new DatePickerDialog(getActivity(),
                                dateSetListener,
                                year, month, day);
                dialog.show();
            }

        });

        clientAppointmentBinding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appointmentID = roofref.push().getKey();

                String mobile = clientAppointmentBinding.apointmentMobileEt.getText().toString();
                String name = clientAppointmentBinding.appointmentNameEt.getText().toString();
                String date = dob;
                String message = clientAppointmentBinding.appointmentMessageEt.getText().toString();

                appointment = new Appointment(mobile, name, date, message);
                roofref.child(appointmentID).setValue(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Appointment saved!", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }
}