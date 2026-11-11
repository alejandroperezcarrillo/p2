package telem;

public class FactoriaCalculadorPrecio {

    public static ICalculadorPrecio getCalculador(PoliticasDePrecios politica) {
        switch (politica) {
            case Normal:
                return new CalculadorPrecioNormal();
            case TemporadaAlta:
                return new CalculadorPrecioTemporadaAlta();
            case Oferton:
                return new CalculadorPrecioOferton();
            case ClienteFrecuente:
                return new CalculadorPrecioClienteFrecuente();
            default:
                throw new IllegalArgumentException("Política de precios desconocida: " + politica);
        }
    }
}