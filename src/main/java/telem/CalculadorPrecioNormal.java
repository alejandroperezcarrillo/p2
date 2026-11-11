package telem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculadorPrecioNormal implements ICalculadorPrecio {

    @Override
    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        return (int) dias * vehiculo.getPrecioBase();
    }
}
