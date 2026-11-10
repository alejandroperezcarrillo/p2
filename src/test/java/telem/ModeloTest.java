package telem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class ModeloTest {

    @Test
    public void shouldCreateVehiclesAndExposeTheirProperties() {
        Turismo turismo = new Turismo("TUR123", Categoria.ESPECIAL, 2022, 5, 450.5);
        Motocicleta motocicleta = new Motocicleta("MOT456", Categoria.NORMAL, 2021, 125);
        VehiculoIndustrial industrial =
                new VehiculoIndustrial("IND789", Categoria.SUPERIOR, 2020, 12000, 4);

        assertVehicleProperties(turismo, "TUR123", Categoria.ESPECIAL, 2022);
        assertVehicleProperties(motocicleta, "MOT456", Categoria.NORMAL, 2021);
        assertVehicleProperties(industrial, "IND789", Categoria.SUPERIOR, 2020);

        assertEquals(5, turismo.getPlazas());
        assertEquals(450.5, turismo.getCapacidadMaletero(), 0.0);
        assertEquals(125, motocicleta.getCilindrada());
        assertEquals(12000, industrial.getCapacidadCarga());
        assertEquals(4, industrial.getNumeroEjes());
    }

    @Test
    public void shouldCreateClientAndExposeItsProperties() {
        Cliente cliente = new Cliente("Ana García", "12345678A");

        assertEquals("Ana García", cliente.getNombre());
        assertEquals("12345678A", cliente.getDni());
    }

    @Test
    public void shouldCreateRentalAndExposeItsProperties() {
        Vehiculo vehiculo = new Turismo("TUR123", Categoria.NORMAL, 2022, 5, 450.5);
        Cliente cliente = new Cliente("Ana García", "12345678A");
        LocalDate inicio = LocalDate.of(2026, 3, 10);
        LocalDate fin = LocalDate.of(2026, 3, 12);

        Alquiler alquiler = new Alquiler(vehiculo, cliente, inicio, fin);

        assertSame(vehiculo, alquiler.getVehiculo());
        assertSame(cliente, alquiler.getCliente());
        assertEquals(inicio, alquiler.getFechaInicio());
        assertEquals(fin, alquiler.getFechaFin());
    }

    @Test
    public void shouldCalculateRentalPriceIncludingStartAndEndDates() {
        Alquiler alquiler = new Alquiler(
                new Turismo("TUR123", Categoria.NORMAL, 2022, 5, 450.5),
                new Cliente("Ana García", "12345678A"),
                LocalDate.of(2026, 3, 10),
                LocalDate.of(2026, 3, 12));

        assertRentalPrice(alquiler, LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 10));
        assertRentalPrice(alquiler, LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 12));
        assertRentalPrice(alquiler, LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 16));
    }

    @Test
    public void shouldReturnTypeAndBasePriceForEachVehicleCategory() {
        assertVehicleTypeAndPrices(
                new Turismo("TUR123", Categoria.NORMAL, 2022, 5, 450.5),
                "Turismo", 50, 80, 100);
        assertVehicleTypeAndPrices(
                new Motocicleta("MOT456", Categoria.NORMAL, 2021, 125),
                "Motocicleta", 50, 70, 150);
        assertVehicleTypeAndPrices(
                new VehiculoIndustrial("IND789", Categoria.NORMAL, 2020, 12000, 4),
                "VehiculoIndustrial", 50, 100, 200);
    }

    @Test
    public void shouldAllowChangingVehicleAvailability() {
        Vehiculo vehiculo = new Motocicleta("MOT456", Categoria.NORMAL, 2021, 125);

        assertFalse(vehiculo.isDisponible());
        vehiculo.setDisponible(true);
        assertTrue(vehiculo.isDisponible());
    }

    private static void assertVehicleProperties(
            Vehiculo vehiculo, String matricula, Categoria categoria, int annio) {
        assertEquals(matricula, vehiculo.getMatricula());
        assertEquals(categoria, vehiculo.getCategoria());
        assertEquals(annio, vehiculo.getAnnio());
    }

    private static void assertRentalPrice(
            Alquiler alquiler, LocalDate inicio, LocalDate fin) {
        long diasIncluyendoExtremos = ChronoUnit.DAYS.between(inicio, fin) + 1;
        int precioEsperado = Math.toIntExact(diasIncluyendoExtremos * Alquiler.PRECIO_BASE);

        assertEquals(precioEsperado, alquiler.calcularPrecioAlquiler(inicio, fin));
    }

    private static void assertVehicleTypeAndPrices(
            Vehiculo vehiculo, String tipo, int precioNormal, int precioEspecial, int precioSuperior) {
        assertEquals(tipo, vehiculo.getTipo());
        assertEquals(precioNormal, vehiculo.getPrecioBase());

        vehiculo = vehicleWithCategory(vehiculo, Categoria.ESPECIAL);
        assertEquals(precioEspecial, vehiculo.getPrecioBase());

        vehiculo = vehicleWithCategory(vehiculo, Categoria.SUPERIOR);
        assertEquals(precioSuperior, vehiculo.getPrecioBase());
    }

    private static Vehiculo vehicleWithCategory(Vehiculo vehiculo, Categoria categoria) {
        if (vehiculo instanceof Turismo) {
            return new Turismo(
                    vehiculo.getMatricula(), categoria, vehiculo.getAnnio(),
                    ((Turismo) vehiculo).getPlazas(),
                    ((Turismo) vehiculo).getCapacidadMaletero());
        }
        if (vehiculo instanceof Motocicleta) {
            return new Motocicleta(
                    vehiculo.getMatricula(), categoria, vehiculo.getAnnio(),
                    ((Motocicleta) vehiculo).getCilindrada());
        }
        if (vehiculo instanceof VehiculoIndustrial) {
            return new VehiculoIndustrial(
                    vehiculo.getMatricula(), categoria, vehiculo.getAnnio(),
                    ((VehiculoIndustrial) vehiculo).getCapacidadCarga(),
                    ((VehiculoIndustrial) vehiculo).getNumeroEjes());
        }
        throw new IllegalArgumentException("Tipo de vehículo no soportado: " + vehiculo.getClass());
    }
}
