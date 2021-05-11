package com.halfkon.recipe_finder.history.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.halfkon.recipe_finder.history.HistoryDataSource;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final HistoryDataSource mDataSource;

    public ViewModelFactory(HistoryDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
