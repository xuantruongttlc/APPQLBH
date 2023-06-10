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
public interface NameDAO {
    @Insert
    void inserName(Name name);

    @Query("SELECT * FROM name")
    List<Name> getListName();

    @Query("SELECT * FROM name WHERE name = :namecheck")
    List<Name> checkName(String namecheck);

    @Update
    void updateName(Name name);

    @Delete
    void deleteName(Name name);

    @Query("DELETE FROM name")
    void deleteAll();

    @Query("SELECT * FROM name WHERE name LIKE '%' || :name || '%'")
    List<Name> searchName(String name);
}
