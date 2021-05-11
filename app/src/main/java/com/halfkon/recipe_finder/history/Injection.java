package com.halfkon.recipe_finder.history;

import android.content.Context;

import com.halfkon.recipe_finder.history.room_db.HistoryDatabase;
import com.halfkon.recipe_finder.history.room_db.LocalHistoryDataSource;
import com.halfkon.recipe_finder.history.viewmodel.ViewModelFactory;

public class Injection {
    public static HistoryDataSource provideHistoryDataSource(Context context) {
        HistoryDatabase database = HistoryDatabase.getInstance(context);
        return new LocalHistoryDataSource(database.historyDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        HistoryDataSource dataSource = provideHistoryDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
