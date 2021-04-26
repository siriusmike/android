package com.halfkon.recipe_finder.instructions.network;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.halfkon.recipe_finder.instructions.model.Instructions;

import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class InstructionsApiRepoImpl implements InstructionsApiRepo {
    private static final String HOST = "api.spoonacular.com";
    private final InstructionsApi mInstructionsApi;

    public InstructionsApiRepoImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder()
                        .scheme("https")
                        .host(HOST)
                        .build())
                .build();

        mInstructionsApi = retrofit.create(InstructionsApi.class);
    }

    @Override
    public LiveData<InstructionsApiResponse> getInstructions(Integer id){
        final MutableLiveData<InstructionsApiResponse> liveData = new MutableLiveData<>();

        Call<Instructions> call = mInstructionsApi.getInstructions(id);

        call.enqueue(new Callback<Instructions>() {
            @Override
            public void onResponse(@NonNull Call<Instructions> call,
                                   @NonNull Response<Instructions> response) {
                liveData.setValue(new InstructionsApiResponse(Objects.requireNonNull(response.body())));
            }

            @Override
            public void onFailure(@NonNull Call<Instructions> call,
                                  @NonNull Throwable t) {
                liveData.setValue(new InstructionsApiResponse(t));
            }
        });
        return liveData;
    }
}

