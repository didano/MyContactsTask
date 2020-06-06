package data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vlad.mycontactstask.Contact;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();
}
