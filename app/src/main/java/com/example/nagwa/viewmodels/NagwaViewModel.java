package com.example.nagwa.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nagwa.models.NagwaModel;
import com.example.nagwa.repositories.NagwaRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NagwaViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    private NagwaRepository repository;
    private MutableLiveData<List<NagwaModel>> nagwaList=new MutableLiveData<>();

    @Inject
    public NagwaViewModel(NagwaRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<NagwaModel>> getNagwaList() {
        return nagwaList;
    }

    public void getNagwaListFromApi(){
        repository.getNagwaServies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nagwaModels -> nagwaList.setValue(nagwaModels),
                        throwable -> Log.d(TAG, throwable.toString()));
    }
}
