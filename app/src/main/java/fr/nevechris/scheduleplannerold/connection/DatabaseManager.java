package fr.nevechris.scheduleplannerold.connection;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chris on 30/10/2017.
 * As per:
 * https://stackoverflow.com/a/19996964/3309799
 */

public class DatabaseManager {
    private int nOpenCounter;

    private static DatabaseManager instance;
    private static DbHelper databaseHelper;
    private SQLiteDatabase database;

    public static synchronized void createDbHelper(Application a) {
        if (databaseHelper == null)
            databaseHelper = new DbHelper(a);
    }

    public static synchronized DbHelper getConnection() {
        if (databaseHelper == null)
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first");
        return databaseHelper;
    }

//    public static synchronized void initializeInstance(DbHelper helper) {
//        if (instance == null) {
//            instance = new DatabaseManager();
//            databaseHelper = helper;
//        }
//    }

//    public static synchronized DatabaseManager getInstance() {
//        if (instance == null) {
//            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
//                    " is not initialized, call initializeInstance(..) method first");
//        }
//        return instance;
//    }

//    public synchronized SQLiteDatabase openDatabase() {
//        nOpenCounter++;
//        if (nOpenCounter == 1) {
//            database = databaseHelper.getWritableDatabase();
//        }
//        return database;
//    }

//    public synchronized void closeDatabase() {
//        nOpenCounter--;
//        if (nOpenCounter == 0)
//            database.close();
//    }
}
