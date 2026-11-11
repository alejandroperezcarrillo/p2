package telem;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class PoliticasDePreciosTest {

    private static final LocalDate INICIO = LocalDate.of(2026, 6, 1);
    private static final Vehiculo VEHICULO_SUPERIOR =
            new Turismo("TUR123", Categoria.SUPERIOR, 2022, 5, 450.5);

    @Test
    public void shouldCalculateNormalPriceForInclusiveRentalDays() {
        CalculadorPrecioNormal calculador = new CalculadorPrecioNormal();

        assertEquals(100, calculador.calcularPrecioAlquiler(INICIO, INICIO, VEHICULO_SUPERIOR));
        assertEquals(300, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(2), VEHICULO_SUPERIOR));
    }

    @Test
    public void shouldDoubleTheBasePriceDuringHighSeason() {
        CalculadorPrecioTemporadaAlta calculador = new CalculadorPrecioTemporadaAlta();

        assertEquals(200, calculador.calcularPrecioAlquiler(INICIO, INICIO, VEHICULO_SUPERIOR));
        assertEquals(600, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(2), VEHICULO_SUPERIOR));
    }

    @Test
    public void shouldApplyOfferDiscountAndAdditionalDiscountAfterSevenDays() {
        CalculadorPrecioOferton calculador = new CalculadorPrecioOferton();
        Vehiculo vehiculo = new Motocicleta("MOT456", Categoria.ESPECIAL, 2022, 125);

        assertEquals(343, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(6), vehiculo));
        assertEquals(353, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(7), vehiculo));
    }

    @Test
    public void shouldApplyFrequentCustomerDiscountByRentalDay() {
        CalculadorPrecioClienteFrecuente calculador = new CalculadorPrecioClienteFrecuente();
        Vehiculo vehiculo = new Motocicleta("MOT456", Categoria.ESPECIAL, 2022, 125);

        assertEquals(168, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(2), vehiculo));
        assertEquals(210, calculador.calcularPrecioAlquiler(INICIO, INICIO.plusDays(3), vehiculo));
    }

    @Test
    public void shouldSelectTheCalculatorCorrespondingToEachPricingPolicy() {
        Alquiler alquiler = new Alquiler(
                VEHICULO_SUPERIOR,
                new Cliente("Ana García", "12345678A"),
                INICIO,
                INICIO.plusDays(7));

        assertEquals(800, alquiler.calcularPrecioAlquiler(
                INICIO, INICIO.plusDays(7), PoliticasDePrecios.Normal, VEHICULO_SUPERIOR));
        assertEquals(1600, alquiler.calcularPrecioAlquiler(
                INICIO, INICIO.plusDays(7), PoliticasDePrecios.TemporadaAlta, VEHICULO_SUPERIOR));
        assertEquals(353, alquiler.calcularPrecioAlquiler(
                INICIO, INICIO.plusDays(7), PoliticasDePrecios.Oferton,
                new Motocicleta("MOT456", Categoria.ESPECIAL, 2022, 125)));
        assertEquals(540, alquiler.calcularPrecioAlquiler(
                INICIO, INICIO.plusDays(7), PoliticasDePrecios.ClienteFrecuente, VEHICULO_SUPERIOR));
    }
}
