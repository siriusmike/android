package com.halfkon.recipe_finder.history.viewmodel;


import androidx.lifecycle.ViewModel;

import com.halfkon.recipe_finder.history.HistoryDataSource;
import com.halfkon.recipe_finder.history.room_db.HistoryRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class HistoryViewModel extends ViewModel {

    private final HistoryDataSource mDataSource;

    public HistoryViewModel(HistoryDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<List<String>> getRecordIds(int limit) {
        return mDataSource.getRecords()
                .map(records -> {
                    List<String> ids = new ArrayList<String>();
                    for (int i = 0; i < records.size() && i < limit; i++) {
                        ids.add(records.get(i).getRecipeId().toString());
                    }
                    return ids;
                });
    }

    public Completable updateRecord(final Integer recipeId) {
        // if there's no record, create a new record.
        // if we already have a record, then,
        // create a new record, with the id of the previous record and the updated timestamp.
        HistoryRecord record = new HistoryRecord(recipeId);
        return mDataSource.insertOrUpdateRecord(record);
    }
}
