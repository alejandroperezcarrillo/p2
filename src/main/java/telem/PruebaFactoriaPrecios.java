package telem;

import java.time.LocalDate;

public class PruebaFactoriaPrecios {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA FACTORÍA DE POLÍTICAS DE PRECIOS ===");

        Vehiculo turismo = new Turismo("ABC123", Categoria.NORMAL, 2022, 5, 400.0);
        Cliente cliente = new Cliente("María López", "98765432Z");
        LocalDate inicio = LocalDate.of(2026, 7, 1);
        LocalDate fin = inicio.plusDays(7); // Alquiler de 8 días (inicio + 7)

        Alquiler alquiler = new Alquiler(turismo, cliente, inicio, fin);

        // Probando cada política de precios utilizando la factoría a través de Alquiler
        System.out.println("Precio Normal: " + 
            alquiler.calcularPrecioAlquiler(inicio, fin, PoliticasDePrecios.Normal, turismo));
        
        System.out.println("Precio Temporada Alta: " + 
            alquiler.calcularPrecioAlquiler(inicio, fin, PoliticasDePrecios.TemporadaAlta, turismo));
        
        System.out.println("Precio Ofertón: " + 
            alquiler.calcularPrecioAlquiler(inicio, fin, PoliticasDePrecios.Oferton, turismo));
        
        System.out.println("Precio Cliente Frecuente: " + 
            alquiler.calcularPrecioAlquiler(inicio, fin, PoliticasDePrecios.ClienteFrecuente, turismo));

        System.out.println("\n¡Prueba de la factoría de precios ejecutada con éxito!");
    }
}
