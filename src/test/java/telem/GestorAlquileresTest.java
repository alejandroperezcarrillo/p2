package telem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;

import org.json.JSONArray;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GestorAlquileresTest {

    @Rule
    public TemporaryFolder carpetaTemporal = new TemporaryFolder();

    @Test
    public void shouldSaveAndRecoverAlquileresAsJsonAndDeleteTemporaryFile() throws Exception {
        GestorAlquileres gestorOriginal = new GestorAlquileres();
        Cliente ana = new Cliente("Ana García", "12345678A");
        Cliente luis = new Cliente("Luis Pérez", "87654321B");

        Turismo turismo = new Turismo("TUR123", Categoria.ESPECIAL, 2023, 5, 420.5);
        Motocicleta motocicleta = new Motocicleta("MOT456", Categoria.NORMAL, 2021, 250);
        VehiculoIndustrial industrial =
                new VehiculoIndustrial("IND789", Categoria.SUPERIOR, 2020, 14000, 4);
        turismo.setDisponible(false);
        motocicleta.setDisponible(false);
        industrial.setDisponible(true);

        LocalDate inicioTurismo = LocalDate.of(2026, 9, 1);
        LocalDate finTurismo = LocalDate.of(2026, 9, 5);
        LocalDate inicioMoto = LocalDate.of(2026, 10, 10);
        LocalDate finMoto = LocalDate.of(2026, 10, 12);
        LocalDate inicioIndustrial = LocalDate.of(2026, 11, 20);
        LocalDate finIndustrial = LocalDate.of(2026, 11, 22);

        gestorOriginal.agregarAlquiler(new Alquiler(turismo, ana, inicioTurismo, finTurismo));
        gestorOriginal.agregarAlquiler(new Alquiler(motocicleta, ana, inicioMoto, finMoto));
        gestorOriginal.agregarAlquiler(new Alquiler(industrial, luis, inicioIndustrial, finIndustrial));

        File fichero = carpetaTemporal.newFile("alquileres.json");
        String nombreFichero = fichero.getAbsolutePath();

        gestorOriginal.guardarAlquileres(nombreFichero);

        assertTrue(fichero.isFile());
        assertTrue(fichero.length() > 0);
        String contenido = Files.readString(fichero.toPath(), StandardCharsets.UTF_8);
        assertEquals(3, new JSONArray(contenido).length());

        GestorAlquileres gestorRecuperado = new GestorAlquileres();
        gestorRecuperado.recuperarAlquileres(nombreFichero);

        Alquiler[] alquileresAna = gestorRecuperado.obtenerAlquileres(ana);
        Alquiler[] alquileresLuis = gestorRecuperado.obtenerAlquileres(luis);
        assertEquals(2, alquileresAna.length);
        assertEquals(1, alquileresLuis.length);

        assertEquals(ana.getNombre(), alquileresAna[0].getCliente().getNombre());
        assertEquals(ana.getDni(), alquileresAna[0].getCliente().getDni());
        assertEquals(inicioTurismo, alquileresAna[0].getFechaInicio());
        assertEquals(finTurismo, alquileresAna[0].getFechaFin());
        assertTrue(alquileresAna[0].getVehiculo() instanceof Turismo);
        Turismo turismoRecuperado = (Turismo) alquileresAna[0].getVehiculo();
        assertEquals("TUR123", turismoRecuperado.getMatricula());
        assertEquals(Categoria.ESPECIAL, turismoRecuperado.getCategoria());
        assertEquals(2023, turismoRecuperado.getAnnio());
        assertFalse(turismoRecuperado.isDisponible());
        assertEquals(5, turismoRecuperado.getPlazas());
        assertEquals(420.5, turismoRecuperado.getCapacidadMaletero(), 0.0);

        assertEquals(inicioMoto, alquileresAna[1].getFechaInicio());
        assertEquals(finMoto, alquileresAna[1].getFechaFin());
        assertTrue(alquileresAna[1].getVehiculo() instanceof Motocicleta);
        Motocicleta motocicletaRecuperada = (Motocicleta) alquileresAna[1].getVehiculo();
        assertEquals("MOT456", motocicletaRecuperada.getMatricula());
        assertEquals(250, motocicletaRecuperada.getCilindrada());
        assertFalse(motocicletaRecuperada.isDisponible());

        assertEquals(luis.getNombre(), alquileresLuis[0].getCliente().getNombre());
        assertEquals(luis.getDni(), alquileresLuis[0].getCliente().getDni());
        assertEquals(inicioIndustrial, alquileresLuis[0].getFechaInicio());
        assertEquals(finIndustrial, alquileresLuis[0].getFechaFin());
        assertTrue(alquileresLuis[0].getVehiculo() instanceof VehiculoIndustrial);
        VehiculoIndustrial industrialRecuperado =
                (VehiculoIndustrial) alquileresLuis[0].getVehiculo();
        assertEquals("IND789", industrialRecuperado.getMatricula());
        assertEquals(Categoria.SUPERIOR, industrialRecuperado.getCategoria());
        assertEquals(2020, industrialRecuperado.getAnnio());
        assertTrue(industrialRecuperado.isDisponible());
        assertEquals(14000, industrialRecuperado.getCapacidadCarga());
        assertEquals(4, industrialRecuperado.getNumeroEjes());
    }
}
