package com.example.jean.prueba1.activity;

/**
 * Created by jona on 18-02-18.
 */

/**
 * Created by Juned on 2/1/2017.
 */

public class Subject  {

    String SubName = null;
    String SubFullForm = null;
    String SubPrioridad = null;
    String SubEstado = null;

    public Subject(String Sname, String SFullForm, String SFullPrioridad,String SFullEstado) {

        super();

        this.SubName = Sname;

        this.SubFullForm = SFullForm;

        this.SubPrioridad = SFullPrioridad;

        this.SubEstado = SFullEstado;
    }

    public String getSubName() {

        return SubName;

    }
    public void setSubName(String code) {

        this.SubName = code;

    }
    public String getSubFullForm() {

        return SubFullForm;

    }
    public void setSubFullForm(String name) {

        this.SubFullForm = name;

    }

    public String getSubFullPrioridad() {

        return SubPrioridad;

    }
    public void setSubFullPrioridad(String prioridad) {

        this.SubPrioridad = prioridad;

    }

    public String getSubFullEstado() {

        return SubEstado;

    }
    public void setSubFullEstado(String estado) {

        this.SubEstado = estado;

    }

    @Override
    public String toString() {

        return  SubName + " " + SubFullForm  + " " + SubPrioridad + " "+ SubEstado;

    }

}
