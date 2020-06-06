package com.vlad.mycontactstask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import data.ContactsDataBase;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    Context context;
    private List<Contact> contactList;
    private ContactsDataBase contactsDataBase;

    public ContactsAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_list_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.contactImageView.setImageResource(contactList.get(position).getPhoto());
        holder.nameTextView.setText(contactList.get(position).getName());
        holder.surnameTextView.setText(contactList.get(position).getSurname());
        holder.phoneTextView.setText(contactList.get(position).getPhoneNumber());
        holder.emailTextView.setText(contactList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView contactImageView;
        private TextView nameTextView;
        private TextView surnameTextView;
        private TextView phoneTextView;
        private TextView emailTextView;
        private ImageButton imageButtonDelete;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImageView = itemView.findViewById(R.id.contactImageView);
            nameTextView = itemView.findViewById(R.id.contactNameTextView);
            surnameTextView = itemView.findViewById(R.id.contactSurnameTextView);
            phoneTextView = itemView.findViewById(R.id.contactPhoneTextView);
            emailTextView = itemView.findViewById(R.id.contactEmailTextView);
            imageButtonDelete = itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this);
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContact();
                }
            });
            contactsDataBase = Room.databaseBuilder(context,ContactsDataBase.class,"ContactsDB")
                    .allowMainThreadQueries().build();
        }

        private void deleteContact() {
            String contactName = contactList.get(getAdapterPosition()).getName();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to delete " + contactName + " from contacts?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contactsDataBase.getContactDAO().deleteContact(contactList.get(getAdapterPosition()));
                    contactList.clear();
                    contactList.addAll(contactsDataBase.getContactDAO().getAllContacts());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Contact contact = contactList.get(position);
            Intent intent = new Intent(context, ContactInfoActivity.class);
            intent.putExtra("contact",contact);
            context.startActivity(intent);
        }
    }
}
