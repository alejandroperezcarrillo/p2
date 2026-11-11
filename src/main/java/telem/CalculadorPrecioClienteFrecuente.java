package telem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculadorPrecioClienteFrecuente implements ICalculadorPrecio {
    @Override
    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        double total = 0;
        int precioBase = vehiculo.getPrecioBase();
        for (int i = 1; i <= dias; i++) {
            if (i <= 3) {
                total += precioBase * 0.8; // 20% descuento primeros 3 días
            } else {
                total += precioBase * 0.6; // 40% descuento a partir del cuarto día
            }
        }
        return (int) Math.round(total);
    }
}
