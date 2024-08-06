package ar.edu.davinci.goldengym;

public class Reserva {
    private String actividad;
    private String fecha;
    private String horario;
    private String direccion;

    public Reserva() {}

    public Reserva(String actividad, String fecha, String horario, String direccion) {
        this.actividad = actividad;
        this.fecha = fecha;
        this.horario = horario;
        this.direccion = direccion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Actividad: " + actividad + "\nFecha: " + fecha + "\nHorario: " + horario + "\nDirección: " + direccion;
    }
}
