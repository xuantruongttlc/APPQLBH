package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;

import com.example.thulai.Name;

@Database(entities = {Name.class}, version = 1)
public abstract class NameDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "name.db";
    private static NameDatabase instance;
    public static synchronized  NameDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NameDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract NameDAO nameDAO();
}
