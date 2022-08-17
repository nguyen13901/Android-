package com.example.contactapp;

import androidx.room.*;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact ORDER BY firstName")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE firstName LIKE '%' || :name || '%' OR lastName LIKE '%' || :name || '%' ORDER BY firstName")
    List<Contact> findByName(String name);

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact findById(int id);

    @Insert
    void insertAll(Contact... contacts);

    @Query("UPDATE Contact SET avatar = :avatar, firstName = :fistName, lastName = :lastName, " +
            "mobile = :mobile, email = :email, mark = :mark WHERE id = :id")
    void update(int id, byte[] avatar, String fistName, String lastName, String mobile, String email, boolean mark);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
