package telem;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

/**
 * Modela un alquiler de un vehículo por parte de un cliente, con fechas de inicio y fin.
 * Almacena los alquileres realizados en un array estático y proporciona métodos para calcular el precio del alquiler.
 * Alquiler
 */
public class Alquiler {
    
    private Vehiculo vehiculo;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private static Alquiler[] alquileres = new Alquiler[1000];

    public static final int PRECIO_BASE = 50;
    private static int contadorAlquileres = 0;

    public Alquiler(Vehiculo vehiculo, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public static Alquiler[] obtenerAlquileres() {
        return alquileres; 
    }

    public static void agregarAlquiler(Alquiler alquiler) {
        if (contadorAlquileres < alquileres.length) {
            alquileres[contadorAlquileres] = alquiler;
            contadorAlquileres++;
        } else {
            System.out.println("No se pueden agregar más alquileres. Capacidad máxima alcanzada.");
        }
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin) {
        int dias = (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1; // +1 para incluir el día de inicio
        return dias * PRECIO_BASE;
    }

    @Override 
    public String toString() {
        return new JSONObject(this).toString();
    }

    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, PoliticasDePrecios politica, Vehiculo vehiculo) {
        ICalculadorPrecio calculador = FactoriaCalculadorPrecio.getCalculador(politica);
        return calculador.calcularPrecioAlquiler(fechaInicio, fechaFin, vehiculo);
    }

    public Alquiler(String jsonString) {
        org.json.JSONObject json = new org.json.JSONObject(jsonString);
        
        // Deserializamos el cliente
        this.cliente = new Cliente(json.getJSONObject("cliente").toString());
        
        // Deserializamos el vehículo
        this.vehiculo = FactoriaVehiculos.getVehiculo(json.getJSONObject("vehiculo").toString());
        
        // Deserializamos las fechas
        this.fechaInicio = LocalDate.parse(json.getString("fechaInicio"));
        this.fechaFin = LocalDate.parse(json.getString("fechaFin"));
    }

    public static void main(String[] args) {
        // Crear instancias de Vehiculo y Cliente
        Vehiculo vehiculo1 = new VehiculoIndustrial("ABC123", Categoria.SUPERIOR, 2019, 100, 4);
        Cliente cliente1 = new Cliente("Alejandro Pérez", "23313365J");
        LocalDate hoy = LocalDate.now();

        Alquiler alquiler1 = new Alquiler(vehiculo1, cliente1, hoy, hoy.plusDays(7)); // alquiler de 7 + 1 = 8 días

        // Probando las diferentes políticas de precios con el patrón Estrategia:
        System.out.println("Precio Normal: " + alquiler1.calcularPrecioAlquiler(alquiler1.getFechaInicio(), alquiler1.getFechaFin(), PoliticasDePrecios.Normal, vehiculo1));
        System.out.println("Precio Temporada Alta: " + alquiler1.calcularPrecioAlquiler(alquiler1.getFechaInicio(), alquiler1.getFechaFin(), PoliticasDePrecios.TemporadaAlta, vehiculo1));
        System.out.println("Precio Ofertón: " + alquiler1.calcularPrecioAlquiler(alquiler1.getFechaInicio(), alquiler1.getFechaFin(), PoliticasDePrecios.Oferton, vehiculo1));
        System.out.println("Precio Cliente Frecuente: " + alquiler1.calcularPrecioAlquiler(alquiler1.getFechaInicio(), alquiler1.getFechaFin(), PoliticasDePrecios.ClienteFrecuente, vehiculo1));
    }
}
