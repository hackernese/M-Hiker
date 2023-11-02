package com.example.m_hiker.database;

import android.content.ContentValues;

import java.util.HashMap;

public interface CommonTable {

    ContentValues insertValues();
    void insert();
    int delete();

    int getid();
    String gettablename();
}
