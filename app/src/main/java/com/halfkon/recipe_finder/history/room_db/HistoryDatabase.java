package com.halfkon.recipe_finder.history.room_db;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.halfkon.recipe_finder.history.HistoryRecordDao;

/**
 * The Room database that contains the Users table
 */
@Database(entities = {HistoryRecord.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {

    private static volatile HistoryDatabase INSTANCE;

    public abstract HistoryRecordDao historyDao();

    public static HistoryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (HistoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HistoryDatabase.class, "History.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
