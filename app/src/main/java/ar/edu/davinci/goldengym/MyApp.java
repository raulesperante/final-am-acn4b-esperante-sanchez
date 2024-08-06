package ar.edu.davinci.goldengym;

import android.app.Application;

public class MyApp extends Application {
    private String gymClass;

    public String getActividad() {
        return gymClass;
    }

    public void setGymClass(String gymClass) {
        this.gymClass = gymClass;
    }
}
