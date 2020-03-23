package com.example.emurgency13.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.emurgency13.Home.DetailsFragmentArgs;
import com.example.emurgency13.Measures.MeasureListActivity;
import com.example.emurgency13.R;

import java.util.List;


public class DetailsFragment extends Fragment implements View.OnClickListener {

    private NavController navController;
    private HomeListViewModel HomeListViewModel;
    private int position;

    private ImageView detailsImage;
    private TextView detailsTitle;
    private TextView detailsDesc;
    private TextView detailsTag;

    private Button detailsStartBtn;
    private String stepid;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        position = DetailsFragmentArgs.fromBundle(getArguments()).getPosition();

        //Initialize UI Elements
        detailsImage = view.findViewById(R.id.details_image);
        detailsTitle = view.findViewById(R.id.details_title);
        detailsDesc = view.findViewById(R.id.details_desc);
        detailsTag = view.findViewById(R.id.details_tag);

        detailsStartBtn = view.findViewById(R.id.details_btn);
        detailsStartBtn.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HomeListViewModel = new ViewModelProvider(getActivity()).get(HomeListViewModel.class);
        HomeListViewModel.getHomeListModelData().observe(getViewLifecycleOwner(), new Observer<List<HomeListModel>>() {
            @Override
            public void onChanged(List<HomeListModel> HomeListModels) {

                Glide.with(getContext())
                        .load(HomeListModels.get(position).getImage())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(detailsImage);

                detailsTitle.setText(HomeListModels.get(position).getName());
                detailsDesc.setText(HomeListModels.get(position).getDesc());
                detailsTag.setText(HomeListModels.get(position).getTag());

                stepid = HomeListModels.get(position).getQuiz_id();

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MeasureListActivity.class);
        startActivity(intent);
    }
}