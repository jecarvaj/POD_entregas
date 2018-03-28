package com.example.jean.prueba1.app;

// hola como estas ?
//PERFECT

public class AppConfig {


// cambiar direccion del server
 // public static String SERVER="http://192.168.0.12/";
public static String SERVER="http://192.168.100.18/";


    // URL de inicio de sesión del servidor
    public static String URL_LOGIN = SERVER+"api/login.php";


    public static String URL_MENU = SERVER+"api/menu.php";

    public static String URL_WAYBILL = SERVER+"api/activityNuevo.php";

  public static String URL_NUEVO = SERVER+"api/nuevo.php";

  public static String URL_FORMULARIO = SERVER+"api/formulario.php";

    public static String URL_ENTREGA = SERVER+"api/getEntregas.php";
}
