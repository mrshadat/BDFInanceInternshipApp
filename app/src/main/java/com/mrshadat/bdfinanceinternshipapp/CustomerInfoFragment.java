package com.mrshadat.bdfinanceinternshipapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mrshadat.bdfinanceinternshipapp.adapters.CustomerAdapter;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentCustomerInfoBinding;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentHomeBinding;
import com.mrshadat.bdfinanceinternshipapp.models.CustomerInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoFragment extends Fragment {
    CustomerInfo customerInfo;
    private Uri filePath;
    FirebaseStorage storage;
    DatabaseReference roofref;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;

    FragmentCustomerInfoBinding customerInfoBinding;
    private long days;
    private String dob = "";
    String customerID;

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(i, i1, i2);
                    //calendar.set(0, 0, 0, hour, minute);
                    Date date = calendar.getTime();
                    days = (new Date().getTime() - date.getTime()) / 86400000;
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
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
                    customerInfoBinding.buttonDate.setText(dob);
                }
            };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerInfoFragment newInstance(String param1, String param2) {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
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
        customerInfoBinding = FragmentCustomerInfoBinding.inflate(LayoutInflater.from(getActivity()));
        return customerInfoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roofref = FirebaseDatabase.getInstance().getReference().child("customer");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        customerInfoBinding.buttonDate.setOnClickListener(new View.OnClickListener() {
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

        customerInfoBinding.buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        customerInfoBinding.buttonCreateVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customerID = roofref.push().getKey();

                String eventName = customerInfoBinding.editTextEventName.getText().toString();
                String mobile = customerInfoBinding.customerMobileEt.getText().toString();
                String address = customerInfoBinding.customerAddressEt.getText().toString();
                String profession = customerInfoBinding.spinnerProfession.getSelectedItem().toString();
                String netWorth = customerInfoBinding.customerNetWorthEt.getText().toString();
                String birthDate = dob;

                customerInfo = new CustomerInfo(eventName, mobile, address, profession, netWorth, birthDate);

                assert customerID != null;
                uploadImage();
                roofref.child(customerID).setValue(customerInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Customer Visit saved!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }*/
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //You will get donwload URL in uri
                                    //Adding that URL to Realtime database
                                    roofref.child(customerID).child("imageUrl").setValue(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}