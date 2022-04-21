package com.example.nagwa.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.nagwa.R;
import com.example.nagwa.databinding.ActivityMainBinding;
import com.example.nagwa.ui.adapters.NagwaAdapter;
import com.example.nagwa.viewmodels.NagwaViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private NagwaViewModel viewModel;
    private ActivityMainBinding binding;
    private NagwaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        use view binding to interacts with views
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        adapter=new NagwaAdapter();
        binding.recyclerMain.setAdapter(adapter);

//        call the viewmodel without constructor by using hilt
        viewModel=new ViewModelProvider(this).get(NagwaViewModel.class);

//        call to requist data from api
        viewModel.getNagwaListFromApi();
        viewModel.getNagwaList().observe(this,nagwaModels -> {
            adapter.setData(nagwaModels);
        });


    }
}