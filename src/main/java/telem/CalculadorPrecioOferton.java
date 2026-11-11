package telem;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculadorPrecioOferton implements ICalculadorPrecio {
    @Override
    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        double precioBaseDescuento = vehiculo.getPrecioBase() * 0.70; // 30% descuento
        double total = dias * precioBaseDescuento;
        if (dias > 7) {
            total = total * 0.90; // 10% descuento adicional
        }
        return (int) Math.round(total);
    }
}
