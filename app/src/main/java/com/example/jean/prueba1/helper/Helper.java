package com.example.jean.prueba1.helper;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class Helper {

    //Para verificar si el GPP está activado o no
    public static boolean gpsActivo(Context contexto){
        LocationManager mlocManager = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);;
        return mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //Obtiene el imei, no funciona en android 6 //MEJORAR
    public static String getImei(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static boolean isNetDisponible(Context contexto) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }
}
