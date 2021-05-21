package com.halfkon.recipe_finder.history;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.halfkon.recipe_finder.history.room_db.HistoryRecord;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface HistoryRecordDao {
    @Query("SELECT * FROM records  ORDER BY dt desc")
    Flowable<List<HistoryRecord>> getRecords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRecord(HistoryRecord record);

    @Query("DELETE FROM records")
    void deleteAllRecords();
}