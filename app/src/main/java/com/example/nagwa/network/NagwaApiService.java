package com.example.nagwa.network;

import com.example.nagwa.models.NagwaModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface NagwaApiService {

    @GET("u/0/uc?id=1Wqr5oeX795k7VmCAdrAxoMMFhBBwXt4B&export=download")
    Observable<List<NagwaModel>> getNagwaServies();
}
