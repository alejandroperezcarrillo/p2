package telem;

import java.time.LocalDate;

public class PruebaFactoria {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE SERIALIZACIÓN Y DESERIALIZACIÓN ===");

        // 1. Crear objetos originales
        Vehiculo turismoOriginal = new Turismo("1234ABC", Categoria.NORMAL, 2020, 5, 450.0);
        Cliente clienteOriginal = new Cliente("Ana Gómez", "87654321B");
        LocalDate hoy = LocalDate.now();
        Alquiler alquilerOriginal = new Alquiler(turismoOriginal, clienteOriginal, hoy, hoy.plusDays(4));

        // 2. Serializar a JSON (usando el toString que invoca a JSONObject)
        String jsonAlquiler = alquilerOriginal.toString();
        System.out.println("\nJSON Generado (Serialización):");
        System.out.println(jsonAlquiler);

        // 3. Deserializar desde el JSON (usando el constructor y la FactoriaVehiculos)
        Alquiler alquilerReconstruido = new Alquiler(jsonAlquiler);

        // 4. Comprobar que los datos coinciden
        System.out.println("\n--- Objeto Reconstruído ---");
        System.out.println("Cliente: " + alquilerReconstruido.getCliente().getNombre() + " (DNI: " + alquilerReconstruido.getCliente().getDni() + ")");
        System.out.println("Vehículo Tipo: " + alquilerReconstruido.getVehiculo().getTipo());
        System.out.println("Matrícula: " + alquilerReconstruido.getVehiculo().getMatricula());
        System.out.println("Fecha Inicio: " + alquilerReconstruido.getFechaInicio());
        System.out.println("Fecha Fin: " + alquilerReconstruido.getFechaFin());

        // Comprobación rápida
        if (alquilerReconstruido.getVehiculo() instanceof Turismo) {
            Turismo t = (Turismo) alquilerReconstruido.getVehiculo();
            System.out.println("Capacidad Maletero del Turismo: " + t.getCapacidadMaletero());
        }
        
        System.out.println("\n¡Deserialización completada con éxito!");
    }
}