package com.halfkon.recipe_finder.history.room_db;

import com.halfkon.recipe_finder.history.HistoryDataSource;
import com.halfkon.recipe_finder.history.HistoryRecordDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


public class LocalHistoryDataSource implements HistoryDataSource {
    private final HistoryRecordDao mHistoryDao;
    public LocalHistoryDataSource(HistoryRecordDao historyDao) {
        mHistoryDao = historyDao;
    }

    @Override
    public Flowable<List<HistoryRecord>> getRecords() {
        return mHistoryDao.getRecords();
    }

    @Override
    public Completable insertOrUpdateRecord(HistoryRecord historyRecord) {
        return mHistoryDao.insertRecord(historyRecord);
    }

    @Override
    public void deleteAllRecords() {
        mHistoryDao.deleteAllRecords();
    }
}
