package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thulai.MatHang;
import com.example.thulai.Name;

import java.util.List;
@Dao
public interface MhDao{
        @Insert
        void inserNameMH(MatHang mathang);

        @Query("SELECT * FROM nameMH")
        List<MatHang> getListName();

        @Query("SELECT * FROM nameMH WHERE nameMH = :namecheck")
        List<MatHang> checkName(String namecheck);

        @Update
        void updateNameMH(MatHang mathang);

        @Delete
        void deleteNameMH(MatHang mathang);

        @Query("DELETE FROM nameMH")
        void deleteAll();

        @Query("SELECT * FROM nameMH WHERE nameMH LIKE '%' || :nameMH || '%'")
        List<MatHang> searchName(String nameMH);

}
