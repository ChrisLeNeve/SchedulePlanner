package fr.nevechris.scheduleplanner.connection;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import fr.nevechris.scheduleplanner.dao.ExamDao;
import fr.nevechris.scheduleplanner.entity.Exam;

/**
 * Created by Chris on 07/03/2018.
 */

@Database(version = 3, entities = {Exam.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExamDao examDao();
}
