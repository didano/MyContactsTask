package com.vlad.mycontactstask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import data.ContactsDataBase;

public class ContactInfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputLayout nameInputLayout;
    private TextInputLayout surnameInputLayout;
    private TextInputLayout phoneInputLayout;
    private TextInputLayout emailInputLayout;

    private int imageId;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;

    private Contact contact;
    private ContactsDataBase contactsDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        imageView = findViewById(R.id.contactInfoImageView);
        nameInputLayout = findViewById(R.id.contactNameEditText);
        surnameInputLayout = findViewById(R.id.contactSurameEditText);
        phoneInputLayout = findViewById(R.id.contactNumberEditText);
        emailInputLayout = findViewById(R.id.contactEmailEditText);

        contactsDataBase = Room.databaseBuilder(this,ContactsDataBase.class,"ContactsDB")
                .allowMainThreadQueries().build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UiUpdatingFromIntent();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveChanges:
                saveChanges();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveChanges() {
        imageId = contact.getPhoto();
        name = nameInputLayout.getEditText().getText().toString().trim();
        surname = surnameInputLayout.getEditText().getText().toString().trim();
        phoneNumber = phoneInputLayout.getEditText().getText().toString().trim();
        email = emailInputLayout.getEditText().getText().toString().trim();

        if(name.trim().isEmpty()){
            nameInputLayout.setError("Enter name");
            return;
        } else if(surname.trim().isEmpty()){
            surnameInputLayout.setError("Enter surname");
            return;
        } else if (phoneNumber.trim().isEmpty()){
            phoneInputLayout.setError("Enter phone number");
            return;
        } else if (email.trim().isEmpty()) {
            emailInputLayout.setError("Enter email");
            return;
        } else {
            contact.setName(name);
            contact.setSurname(surname);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            contactsDataBase.getContactDAO().updateContact(contact);
            Toast.makeText(this,"Data saved",Toast.LENGTH_SHORT).show();
        }
    }

    private void UiUpdatingFromIntent(){
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
        imageId = contact.getPhoto();
        name = contact.getName();
        surname = contact.getSurname();
        phoneNumber = contact.getPhoneNumber();
        email = contact.getEmail();

        imageView.setImageResource(imageId);
        nameInputLayout.getEditText().setText(name);
        surnameInputLayout.getEditText().setText(surname);
        phoneInputLayout.getEditText().setText(phoneNumber);
        emailInputLayout.getEditText().setText(email);
    }
}
