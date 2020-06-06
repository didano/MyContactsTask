package com.vlad.mycontactstask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import data.ContactsDataBase;

public class ContactsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    public ArrayList<Contact> contactList;
    private ContactsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ContactsDataBase contactsDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        contactsDataBase = Room.databaseBuilder(getApplicationContext(),ContactsDataBase.class,"ContactsDB")
                .allowMainThreadQueries().build();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        contactList = new ArrayList<>();
        adapter = new ContactsAdapter(contactList,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if(contactsDataBase.getContactDAO().getAllContacts().isEmpty()){
            fillDbWithDefaults();
        }

        contactList.addAll(contactsDataBase.getContactDAO().getAllContacts());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        contactList.clear();
        contactList.addAll(contactsDataBase.getContactDAO().getAllContacts());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reset_button,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        resetList();
        return super.onOptionsItemSelected(item);
    }

    private void resetList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to reset contacts list?");
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactList.clear();
                fillDbWithDefaults();
                contactList.addAll(contactsDataBase.getContactDAO().getAllContacts());
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void fillDbWithDefaults(){
        contactsDataBase.getContactDAO().addContact(new Contact(0,"Stan","Marsh","+3809852222222",
                "stan_marsh@south.park",R.drawable.stan ));
        contactsDataBase.getContactDAO().addContact(new Contact(1,"Kyle","Broflowski","+3809852222221",
                "kyle_broflowski@south.park",R.drawable.kyle ));
        contactsDataBase.getContactDAO().addContact(new Contact(2,"Eric","Kartman","+3809852222223",
                "eric_kartman@south.park",R.drawable.eric ));
        contactsDataBase.getContactDAO().addContact(new Contact(3,"Kenny","McCormick","+3809852222224",
                "th3y_killed_kenny@south.park",R.drawable.kenny ));
        contactsDataBase.getContactDAO().addContact(new Contact(4,"Leopold","Stotch","+3809852222225",
                "butters@south.park",R.drawable.butters ));
        contactsDataBase.getContactDAO().addContact(new Contact(5,"Randy","Marsh","+3809852222226",
                "randy@south.park",R.drawable.randy ));
        contactsDataBase.getContactDAO().addContact(new Contact(6,"Mister","Slave","+3809852222227",
                "slave@south.park",R.drawable.slave ));
        contactsDataBase.getContactDAO().addContact(new Contact(7,"Jerome","McElroy","+3809852222228",
                "chef@south.park",R.drawable.jerome ));
    }
}
