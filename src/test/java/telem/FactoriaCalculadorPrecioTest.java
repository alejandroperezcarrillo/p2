package telem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class FactoriaCalculadorPrecioTest {

    private static final LocalDate FECHA_INICIO = LocalDate.of(2026, 7, 1);
    private static final LocalDate FECHA_FIN = LocalDate.of(2026, 7, 8);
    private static final Vehiculo TURISMO =
            new Turismo("ABC123", Categoria.ESPECIAL, 2022, 5, 400.0);

    @Test
    public void shouldReturnNormalCalculatorAndCalculateExpectedPrice() {
        ICalculadorPrecio calculador =
                FactoriaCalculadorPrecio.getCalculador(PoliticasDePrecios.Normal);

        assertNotNull(calculador);
        assertTrue(calculador instanceof CalculadorPrecioNormal);
        assertEquals(640, calculador.calcularPrecioAlquiler(FECHA_INICIO, FECHA_FIN, TURISMO));
    }

    @Test
    public void shouldReturnHighSeasonCalculatorAndCalculateExpectedPrice() {
        ICalculadorPrecio calculador =
                FactoriaCalculadorPrecio.getCalculador(PoliticasDePrecios.TemporadaAlta);

        assertNotNull(calculador);
        assertTrue(calculador instanceof CalculadorPrecioTemporadaAlta);
        assertEquals(1280, calculador.calcularPrecioAlquiler(FECHA_INICIO, FECHA_FIN, TURISMO));
    }

    @Test
    public void shouldReturnOfferCalculatorAndCalculateExpectedPrice() {
        ICalculadorPrecio calculador =
                FactoriaCalculadorPrecio.getCalculador(PoliticasDePrecios.Oferton);

        assertNotNull(calculador);
        assertTrue(calculador instanceof CalculadorPrecioOferton);
        assertEquals(403, calculador.calcularPrecioAlquiler(FECHA_INICIO, FECHA_FIN, TURISMO));
    }

    @Test
    public void shouldReturnFrequentCustomerCalculatorAndCalculateExpectedPrice() {
        ICalculadorPrecio calculador =
                FactoriaCalculadorPrecio.getCalculador(PoliticasDePrecios.ClienteFrecuente);

        assertNotNull(calculador);
        assertTrue(calculador instanceof CalculadorPrecioClienteFrecuente);
        assertEquals(432, calculador.calcularPrecioAlquiler(FECHA_INICIO, FECHA_FIN, TURISMO));
    }
}
