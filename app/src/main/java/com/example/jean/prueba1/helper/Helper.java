package com.example.jean.prueba1.helper;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;



public class Helper {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE =999 ;

    //Para verificar si el GPP está activado o no
    public static boolean gpsActivo(Context contexto){
        LocationManager mlocManager = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);;
        return mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //Obtiene el imei
    public static String getImei(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    //Verifica si hay conexion a internet
    public static boolean isNetDisponible(Context contexto) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    //Pido permiso para ocupar permiso de READ_PHONE_STATE para leer imei. NECESARIO EN ANDROID 6+
    public static boolean permisoImei(Context c, Activity ac){
        //Pide el permiso. Si es que no lo otorga, retorno false
        if (ContextCompat.checkSelfPermission(c,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ac, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            Log.d("version","PIDO PERMISO Y NO ESTÄ OTORGADO");
            return false;
        } else {
            Log.d("version","PIDO PERMISO Y SI ESTÄ OTORGADO");
            return true;
        }
    }
}
