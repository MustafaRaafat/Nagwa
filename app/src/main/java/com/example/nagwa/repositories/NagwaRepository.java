package com.example.nagwa.repositories;

import com.example.nagwa.models.NagwaModel;
import com.example.nagwa.network.NagwaApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class NagwaRepository {
    private NagwaApiService apiService;

    @Inject
    public NagwaRepository(NagwaApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<List<NagwaModel>> getNagwaServies(){
        return apiService.getNagwaServies();
    }
}
