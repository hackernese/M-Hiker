package com.example.m_hiker.CreateHikePage.LocationList;

import android.location.Address;

public class LocationItem {

    public Address addr;

    public String name;
    public String country;

    double longtitude = 0;
    double latitude = 0;

    public boolean is_default = false;

    public LocationItem(Address address){

        this.addr = address;
        this.country = address.getCountryName();
        this.name = address.getAddressLine(0);

        this.longtitude = address.getLongitude();
        this.latitude = address.getLatitude();

    }

    public LocationItem(){
        this.is_default = true;
    }
}
