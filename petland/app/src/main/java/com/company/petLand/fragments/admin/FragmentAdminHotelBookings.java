package com.company.petLand.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.company.petLand.adapters.ClientBookingsAdapter;
import com.company.petLand.databinding.FragmentAdminBookingsBinding;
import com.company.petLand.databinding.FragmentClientOrdersBinding;
import com.company.petLand.helpers.Firebase;
import com.company.petLand.models.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAdminHotelBookings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAdminHotelBookings extends Fragment {
    FragmentAdminBookingsBinding binding;
    ClientBookingsAdapter adapter;
    List<Booking> bookings = new ArrayList<>();


    public FragmentAdminHotelBookings() {
    }


    public static FragmentAdminHotelBookings newInstance() {
        return new FragmentAdminHotelBookings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        adapter = new ClientBookingsAdapter(bookings);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminBookingsBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(adapter);
        Firebase.getHotelBookingsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookings.clear();
                for (DataSnapshot x : snapshot.getChildren()) {
                    Booking booking = x.getValue(Booking.class);
                    bookings.add(booking);
                }
                adapter.notifyDataSetChanged();
                if(bookings.isEmpty()){
                    binding.emptyLayout.setVisibility(View.VISIBLE);
                }else {
                    binding.emptyLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }



    }


