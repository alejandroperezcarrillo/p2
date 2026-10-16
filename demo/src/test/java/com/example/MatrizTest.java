package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * Pruebas de los constructores de Matriz.
 */
public class MatrizTest {

    /*
     * Comprobamos el caso más sencillo:
     * el constructor recibe una matriz rectangular.
     */
    @Test
    public void constructorMatrizRectangular() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 }
        };

        Matriz m = new Matriz(datos);

        assertArrayEquals(
                new int[] { 2, 3 },
                m.getDimension());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(datos[i][j], m.getElemento(i, j), 1e-9);
            }
        }        
    }

    @Test
    public void constructorAdmiteUnaFilaYUnaColumna() {
        Matriz unaFila = new Matriz(new double[][] { { 7.5 } });
        Matriz unaColumna = new Matriz(new double[][] {
            { 1.0 },
            { 2.0 },
            { 3.0 }
        });

        assertArrayEquals(new int[] { 1, 1 }, unaFila.getDimension());
        assertEquals(7.5, unaFila.getElemento(0, 0), 1e-9);
        assertArrayEquals(new int[] { 3, 1 }, unaColumna.getDimension());
        assertEquals(1.0, unaColumna.getElemento(0, 0), 1e-9);
        assertEquals(3.0, unaColumna.getElemento(2, 0), 1e-9);
    }

    /*
     * Si las filas no tienen la misma longitud,
     * la matriz toma como número de columnas la longitud
     * de la fila más larga y completa con ceros.
     */
    @Test
    public void constructorRellenaConCeros() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0 },
            { 6.0 }
        };

        Matriz m = new Matriz(datos);

        assertArrayEquals(
                new int[] { 3, 3 },
                m.getDimension());

        assertEquals(4.0, m.getElemento(1, 0), 1e-9);
        assertEquals(5.0, m.getElemento(1, 1), 1e-9);
        assertEquals(0.0, m.getElemento(1, 2), 1e-9);

        assertEquals(6.0, m.getElemento(2, 0), 1e-9);
        assertEquals(0.0, m.getElemento(2, 1), 1e-9);
        assertEquals(0.0, m.getElemento(2, 2), 1e-9);
    }

    @Test
    public void constructorCompletaConCerosLasFilasVaciasEnCualquierPosicion() {
        Matriz m = new Matriz(new double[][] {
            {},
            { 1.0, 2.0 },
            {},
            { 3.0 }
        });

        assertArrayEquals(new int[] { 4, 2 }, m.getDimension());
        assertEquals(0.0, m.getElemento(0, 0), 1e-9);
        assertEquals(0.0, m.getElemento(0, 1), 1e-9);
        assertEquals(1.0, m.getElemento(1, 0), 1e-9);
        assertEquals(2.0, m.getElemento(1, 1), 1e-9);
        assertEquals(0.0, m.getElemento(2, 0), 1e-9);
        assertEquals(0.0, m.getElemento(2, 1), 1e-9);
        assertEquals(3.0, m.getElemento(3, 0), 1e-9);
        assertEquals(0.0, m.getElemento(3, 1), 1e-9);
    }

    /*
     * El constructor debe copiar los datos recibidos.
     * Modificar posteriormente el array original no debe
     * modificar la matriz.
     */
    @Test
    public void constructorHaceUnaCopiaDeLosDatos() {

        double[][] datos = {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        };

        Matriz m = new Matriz(datos);

        datos[0][0] = 100.0;

        assertEquals(1.0, m.getElemento(0, 0), 1e-9);
    }

    @Test
    public void getDimensionDevuelveUnaCopia() {
        Matriz m = new Matriz(new double[][] { { 1.0, 2.0 }, { 3.0, 4.0 } });

        int[] dimension = m.getDimension();
        dimension[0] = 100;
        dimension[1] = 100;

        assertArrayEquals(new int[] { 2, 2 }, m.getDimension());
    }

    @Test
    public void getElementoAdmiteLasCuatroEsquinas() {
        Matriz m = new Matriz(new double[][] {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 }
        });

        assertEquals(1.0, m.getElemento(0, 0), 1e-9);
        assertEquals(3.0, m.getElemento(0, 2), 1e-9);
        assertEquals(4.0, m.getElemento(1, 0), 1e-9);
        assertEquals(6.0, m.getElemento(1, 2), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getElementoRechazaFilaNegativa() {
        new Matriz(new double[][] { { 1.0 } }).getElemento(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getElementoRechazaFilaFueraDeRango() {
        new Matriz(new double[][] { { 1.0 } }).getElemento(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getElementoRechazaColumnaNegativa() {
        new Matriz(new double[][] { { 1.0 } }).getElemento(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getElementoRechazaColumnaFueraDeRango() {
        new Matriz(new double[][] { { 1.0 } }).getElemento(0, 1);
    }

    @Test
    public void toStringRepresentaUnaMatrizDeUnElemento() {
        Matriz m = new Matriz(new double[][] { { 2.5 } });

        assertEquals("[2.5]", m.toString());
    }

    @Test
    public void toStringRepresentaMatrizRectangularConSaltosDeLinea() {
        Matriz m = new Matriz(new double[][] {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        });

        assertEquals("[1.0, 2.0]" + System.lineSeparator() + "[3.0, 4.0]", m.toString());
    }

    @Test
    public void toStringIncluyeLosCerosDeLasFilasIrregulares() {
        Matriz m = new Matriz(new double[][] {
            { 1.0, 2.0 },
            { 3.0 }
        });

        assertEquals("[1.0, 2.0]" + System.lineSeparator() + "[3.0, 0.0]", m.toString());
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array null o vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizNull() {
        new Matriz(null);
    }  
    
    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizVacia() {
        new Matriz(new double[0][0]);
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * todos los arrays con filas vacías.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizConFilasVacias() {
        double[][] datos = {
            { },
            { }
        };

        new Matriz(datos);
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array con filas null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizConFilasNull() {
        double[][] datos = {
            { 1.0, 2.0 },
            null
        };

        new Matriz(datos);
    }
}
