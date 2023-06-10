package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.thulai.MatHang;
import com.example.thulai.Name;

@Database(entities = {MatHang.class}, version = 1)
public abstract class MatHangDatabase  extends RoomDatabase {

    static Migration migration_from_1_to_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //ALTER TABLE TEN_BANG ADD TEN_COT DINH_NGHIA_COT;
                database.execSQL("ALTER TABLE nameMH ADD COLUMN SoLuongMh TEXT");
        }
    };
    private static final String DATABASE_NAMEMH = "nameMH.db";
    private static MatHangDatabase instance;
    public static synchronized MatHangDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MatHangDatabase.class, DATABASE_NAMEMH)
                    .allowMainThreadQueries()
                    .addMigrations(migration_from_1_to_2)
                    .build();
        }
        return instance;
    }
    public abstract MhDao mhDAO();
}
