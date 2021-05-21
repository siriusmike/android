package com.halfkon.recipe_finder.history;


import com.halfkon.recipe_finder.history.room_db.HistoryRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Access point for managing user data.
 */
public interface HistoryDataSource {

    Flowable<List<HistoryRecord>> getRecords();

    Completable insertOrUpdateRecord(HistoryRecord record);

    void deleteAllRecords();
}
