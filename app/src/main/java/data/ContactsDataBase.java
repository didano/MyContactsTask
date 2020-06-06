package data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vlad.mycontactstask.Contact;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactsDataBase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();
}
