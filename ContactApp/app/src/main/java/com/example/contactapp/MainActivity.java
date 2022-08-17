package com.example.contactapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;
    private ContactViewModel contactViewModel;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm trong danh bạ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()) {
//                    contactAdapter.setContacts(contactDao.getAll());
//                } else {
//                    contactAdapter.setContacts(contactDao.findByName(newText));
//                }
                contactAdapter.findByName(contactDao.getAll(), newText);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
                requestPermissions(permissions, 1001);
            }
        }

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();

        contacts = new ArrayList<>();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                contacts.addAll(contactDao.getAll());
//            }
//        });

        contactAdapter = new ContactAdapter(contacts, this);
        binding.rvContacts.setAdapter(contactAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactAdapter.setContacts(contacts);
            }
        });

        ActivityResultLauncher<Intent> addLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            String uriString = data.getStringExtra("uri");
                            byte[] bytes = null;
                            if (!uriString.isEmpty()) {
                                Uri uri = Uri.parse(uriString);
                                Bitmap bitmap = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                bytes = BitmapHelper.bitmapToByteArray(bitmap);
                            }
                            Contact contact = (Contact) data.getSerializableExtra("contact");
                            contact.setAvatar(bytes);
                            contactViewModel.insert(contact);
                            contactAdapter.setContacts(contactDao.getAll());
                            Toast.makeText(getApplicationContext(), "Thêm liên hệ thành công!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                addLauncher.launch(intent);
            }
        });

        ActivityResultLauncher<Intent> detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 2) {
                            contactAdapter.setContacts(contactDao.getAll());
                            Toast.makeText(getApplicationContext(), "Xóa liên hệ thành công!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", contact.getId());
                detailLauncher.launch(intent);
            }
        });
    }
}