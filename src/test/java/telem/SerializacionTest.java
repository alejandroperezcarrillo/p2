package telem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.Test;

public class SerializacionTest {

    @Test
    public void shouldSerializeAndDeserializeClient() {
        Cliente original = new Cliente("Ana \"Anita\" García", "12345678A");

        JSONObject json = new JSONObject(original.toString());
        Cliente reconstruido = new Cliente(json.toString());

        assertEquals("Ana \"Anita\" García", json.getString("nombre"));
        assertEquals("12345678A", json.getString("dni"));
        assertEquals(original.getNombre(), reconstruido.getNombre());
        assertEquals(original.getDni(), reconstruido.getDni());
    }

    @Test
    public void shouldSerializeAndDeserializeTurismoThroughFactory() {
        Turismo original = new Turismo("TUR123", Categoria.ESPECIAL, 2022, 5, 450.5);
        original.setDisponible(true);

        JSONObject json = new JSONObject(original.toString());
        Vehiculo reconstruido = FactoriaVehiculos.getVehiculo(json.toString());

        assertEquals("Turismo", json.getString("tipo"));
        assertVehicleProperties(reconstruido, "TUR123", Categoria.ESPECIAL, 2022, true);
        assertTrue(reconstruido instanceof Turismo);
        assertEquals(5, ((Turismo) reconstruido).getPlazas());
        assertEquals(450.5, ((Turismo) reconstruido).getCapacidadMaletero(), 0.0);
    }

    @Test
    public void shouldSerializeAndDeserializeMotocicletaThroughFactory() {
        Motocicleta original = new Motocicleta("MOT456", Categoria.SUPERIOR, 2021, 125);

        JSONObject json = new JSONObject(original.toString());
        Vehiculo reconstruido = FactoriaVehiculos.getVehiculo(json.toString());

        assertEquals("Motocicleta", json.getString("tipo"));
        assertVehicleProperties(reconstruido, "MOT456", Categoria.SUPERIOR, 2021, false);
        assertTrue(reconstruido instanceof Motocicleta);
        assertEquals(125, ((Motocicleta) reconstruido).getCilindrada());
    }

    @Test
    public void shouldSerializeAndDeserializeVehiculoIndustrialThroughFactory() {
        VehiculoIndustrial original =
                new VehiculoIndustrial("IND789", Categoria.NORMAL, 2020, 12000, 4);
        original.setDisponible(true);

        JSONObject json = new JSONObject(original.toString());
        Vehiculo reconstruido = FactoriaVehiculos.getVehiculo(json.toString());

        assertEquals("VehiculoIndustrial", json.getString("tipo"));
        assertVehicleProperties(reconstruido, "IND789", Categoria.NORMAL, 2020, true);
        assertTrue(reconstruido instanceof VehiculoIndustrial);
        assertEquals(12000, ((VehiculoIndustrial) reconstruido).getCapacidadCarga());
        assertEquals(4, ((VehiculoIndustrial) reconstruido).getNumeroEjes());
    }

    @Test
    public void shouldSerializeAndDeserializeCompleteRentalWithNestedObjectsAndDates() {
        Turismo vehiculo = new Turismo("TUR321", Categoria.SUPERIOR, 2023, 7, 510.25);
        vehiculo.setDisponible(true);
        Cliente cliente = new Cliente("José \"Pepe\" López", "87654321B");
        LocalDate fechaInicio = LocalDate.of(2026, 8, 14);
        LocalDate fechaFin = LocalDate.of(2026, 8, 18);
        Alquiler original = new Alquiler(vehiculo, cliente, fechaInicio, fechaFin);

        JSONObject json = new JSONObject(original.toString());
        JSONObject clienteJson = json.getJSONObject("cliente");
        JSONObject vehiculoJson = json.getJSONObject("vehiculo");
        Alquiler reconstruido = new Alquiler(json.toString());

        assertEquals(cliente.getNombre(), clienteJson.getString("nombre"));
        assertEquals(cliente.getDni(), clienteJson.getString("dni"));
        assertEquals("Turismo", vehiculoJson.getString("tipo"));
        assertEquals(fechaInicio.toString(), json.getString("fechaInicio"));
        assertEquals(fechaFin.toString(), json.getString("fechaFin"));

        assertEquals(cliente.getNombre(), reconstruido.getCliente().getNombre());
        assertEquals(cliente.getDni(), reconstruido.getCliente().getDni());
        assertEquals(fechaInicio, reconstruido.getFechaInicio());
        assertEquals(fechaFin, reconstruido.getFechaFin());
        assertTrue(reconstruido.getVehiculo() instanceof Turismo);
        assertVehicleProperties(reconstruido.getVehiculo(), "TUR321", Categoria.SUPERIOR, 2023, true);
        assertEquals(7, ((Turismo) reconstruido.getVehiculo()).getPlazas());
        assertEquals(510.25, ((Turismo) reconstruido.getVehiculo()).getCapacidadMaletero(), 0.0);
    }

    private static void assertVehicleProperties(
            Vehiculo vehiculo, String matricula, Categoria categoria, int annio, boolean disponible) {
        assertEquals(matricula, vehiculo.getMatricula());
        assertEquals(categoria, vehiculo.getCategoria());
        assertEquals(annio, vehiculo.getAnnio());
        assertEquals(disponible, vehiculo.isDisponible());
    }
}
