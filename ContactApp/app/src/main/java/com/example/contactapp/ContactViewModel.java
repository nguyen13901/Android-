package com.example.contactapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> listContact;
    private ContactDao contactDao;
    private AppDatabase appDatabase;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        contactDao = appDatabase.contactDao();
    }

    private void setContacts() {
        List<Contact> data = contactDao.getAll();
        listContact.setValue(data);
    }

    public MutableLiveData<List<Contact>> getData() {
        if(listContact == null) {
            listContact = new MutableLiveData<List<Contact>>();
            setContacts();
        }
        return listContact;
    }

    public void insert(Contact... contacts) {
        contactDao.insertAll(contacts);
        setContacts();
    }

    public void delete(Contact contact) {
        contactDao.delete(contact);
        setContacts();
    }
}
