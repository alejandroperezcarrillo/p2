package telem;

import java.time.LocalDate;

public class GestorAlquileres {
    
    private int contadorAlquileres = 0;
    private Alquiler[] alquileres = new Alquiler[1000];

    // Para simplificar, vamos a definir un array de clientes y 
    // otro de vehículos dentro del gestor.
    // En una aplicación real estarían en una base de datos.
    private Cliente[] clientes = new Cliente[10];
    private Vehiculo[] vehiculos = new Vehiculo[100];


    public GestorAlquileres() {
        inicializarClientes();
        inicializarVehiculos();
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // INICIALIZACIÓN DE CLIENTES Y VEHÍCULOS DE EJEMPLO

    // Definimos métodos privados para inicializar clientes y vehículos de ejemplo
    private void inicializarClientes() {
        clientes[0] = new Cliente("Clara Videncia", "001");
        clientes[1] = new Cliente("Aitor Menta", "002");
        clientes[2] = new Cliente("Lola Mento", "003");
        clientes[3] = new Cliente("Armando Guerra", "004");
        clientes[4] = new Cliente("Ana Lítica", "005");
        clientes[5] = new Cliente("Carmelo Cotón", "006");
    }

    private void inicializarVehiculos() {
        vehiculos[0] = new Turismo("TNA2020", Categoria.NORMAL, 2020, 5, 450.0);
        vehiculos[1] = new Motocicleta("MNA2021", Categoria.NORMAL, 2021, 200);
        vehiculos[2] = new VehiculoIndustrial("CNA2019", Categoria.NORMAL, 2019, 1400, 2);
        vehiculos[3] = new Turismo("TSA2024", Categoria.SUPERIOR, 2024, 5, 550.0);
        vehiculos[4] = new Motocicleta("MSA2021", Categoria.SUPERIOR, 2021, 500);
        vehiculos[5] = new VehiculoIndustrial("CSA2025", Categoria.SUPERIOR, 2025, 2400, 3);
        vehiculos[6] = new Turismo("TEA2026", Categoria.ESPECIAL, 2026, 7, 650.0);
        vehiculos[7] = new Motocicleta("MEA2026", Categoria.ESPECIAL, 2026, 1000);
        vehiculos[8] = new VehiculoIndustrial("CEA2025", Categoria.ESPECIAL, 2025, 4400, 5);

    }

    /////////////////////////////////////////////////////////////////////////////////////
    // MÉTODOS PARA GESTIONAR VEHÍCULOS

     public Vehiculo[] obtenerVehiculos(String tipo) {
        
        Vehiculo[] vehiculosFiltrados = new Vehiculo[vehiculos.length];
        int contador = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                if (vehiculo.getTipo().equals(tipo)) {
                    vehiculosFiltrados[contador++] = vehiculo;
                }
            }
        }

        // Ajustar el tamaño del array para que solo contenga los vehículos encontrados
        Vehiculo[] resultado = new Vehiculo[contador];
        System.arraycopy(vehiculosFiltrados, 0, resultado, 0, contador);
        return resultado;
    }   

    /**
     * Devuelve un array con los vehículos del tipo y categoría especificados.
     * @param tipo
     * @param categoria
     * @return lista de vehículos del tipo y categoría especificados
     */
    public Vehiculo[] obtenerVehiculos(String tipo, Categoria categoria) {
        Vehiculo[] vehiculosFiltrados = new Vehiculo[vehiculos.length];
        int contador = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                if (vehiculo.getTipo().equals(tipo) && vehiculo.getCategoria() == categoria) {
                    vehiculosFiltrados[contador++] = vehiculo;
                }
            }
        }

        Vehiculo[] resultado = new Vehiculo[contador];
        System.arraycopy(vehiculosFiltrados, 0, resultado, 0, contador);
        return resultado;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // MÉTODOS PARA GESTIONAR ALQUILERES

    /**
     * Agrega un nuevo alquiler al array de alquileres.
     * @param alquiler
     */
    public void agregarAlquiler(Alquiler alquiler) {
        if (contadorAlquileres < alquileres.length) {
            alquileres[contadorAlquileres] = alquiler;
            contadorAlquileres++;
        } else {
            System.out.println("No se pueden agregar más alquileres. Capacidad máxima alcanzada.");
        }
    }

    /**
     * Devuelve un array con los alquileres realizados por un cliente específico.
     * @param cliente
     * @return
     */
    public Alquiler[] obtenerAlquileres(Cliente cliente) {
        Alquiler[] filtrados = new Alquiler[contadorAlquileres];
        int contador = 0;
        for (int i = 0; i < contadorAlquileres; i++) {
            if (alquileres[i] != null && alquileres[i].getCliente().getDni().equals(cliente.getDni())) {
                filtrados[contador++] = alquileres[i];
            }
        }
        Alquiler[] resultado = new Alquiler[contador];
        System.arraycopy(filtrados, 0, resultado, 0, contador);
        return resultado;
    }


    /**
     * Informa si un cliente es cliente frecuente en función del número de alquileres previos.
     * @param cliente cliente a evaluar
     * @param umbral número mínimo de alquileres previos que se consideran para determinar si es cliente frecuente
     * @return
     */
    private boolean esClienteFrecuente(Cliente cliente, int umbral) {
        int contador = 0;
        for (int i = 0; i < contadorAlquileres; i++) {
            if (alquileres[i] != null && alquileres[i].getCliente().getDni().equals(cliente.getDni())) {
                contador++;
            }
        }
        return contador >= umbral;
    }


    /////////////////////////////////////////////////////////////////////////////////////
    // INTERFAZ DE USUARIO (CONSOLA) Y LÓGICA DE NEGOCIO

    // La interfaz de usuario y la lógica de negocio suelen implementarse por separado.
    // Aquí se integran ambas por simplicidad, pero en una aplicación real hay que separarlas.


    public void menuPrincipal(){
        
        boolean salir = false;
        
        while(!salir){

            // Se da oportunidad de salir inmediatamente de la app.
            System.out.println("Gestión de alquileres");
            System.out.println("¿Desea salir de la app? (S/N)");

            char s = KeyBoard.readChar();
            if (s == 's' || s == 'S'){
                System.out.println("Saliendo de la aplicación...");
                return;
            }

            // Extensión de la UI para guardar y recuperar datos en ficheros de texto (Ejercicio 5)
            System.out.println("Seleccione una opción:");
            System.out.println("(1) Operar como cliente");
            System.out.println("(2) Guardar alquileres en fichero JSON");
            System.out.println("(3) Recuperar alquileres desde fichero JSON");

            int opcionApp = KeyBoard.readInt();
            if (opcionApp == 2) {
                System.out.println("Introduzca el nombre del fichero (ej: alquileres.json):");
                String fichero = KeyBoard.readString();
                guardarAlquileres(fichero);
                continue;
            } else if (opcionApp == 3) {
                System.out.println("Introduzca el nombre del fichero a cargar:");
                String fichero = KeyBoard.readString();
                recuperarAlquileres(fichero);
                continue;
            }

            // Primero se identifica al cliente:
            Cliente cliente = identificarCliente();

            // Ahora el cliente puede elegir una opción o salir de la aplicación.
            if (cliente != null) {
                System.out.println("Sr " + cliente.getNombre() + " Introduzca una opción");
                System.out.println("(1) seleccionar coche para alquilar");
                System.out.println("(2) entregar un coche alquilado");
                System.out.println("(3) listar su histórico de alquileres");

                int opcion = KeyBoard.readInt();
                System.out.println("opción escogida: " + opcion);

                switch (opcion) {
                    case 1:
                        Vehiculo v = seleccionarVehiculo(cliente);
                        if (v.isDisponible()){
                            System.out.println("¿cuántos días desea alquilar el vehículo?");
                            int dias = KeyBoard.readInt();
                            LocalDate inicio = LocalDate.now();
                            LocalDate fin = inicio.plusDays(dias);

                            // creamos y registramos el alquiler
                            Alquiler q = new Alquiler(v, cliente, inicio, fin);
                            this.agregarAlquiler(q);

                            // marcamos el vehículo como no disponible
                            v.setDisponible(false);

                            // es usuario frecuente?
                            boolean usuarioFrecuente = this.esClienteFrecuente(cliente, 3);

                            PoliticasDePrecios politica;
                            if (usuarioFrecuente){
                                politica = PoliticasDePrecios.ClienteFrecuente;
                                System.out.println("Alquiler realizado. Tarifa Usuario Frecuente.");
                            }
                            else{
                                politica = PoliticasDePrecios.Normal;
                                System.out.println("Alquiler realizado. Tarifa Normal.");
                            }

                            // calculamos el precio usando la factoría de precios (ejercicio 4)
                            ICalculadorPrecio calculador = FactoriaCalculadorPrecio.getCalculador(politica);
                            int precioTotal = calculador.calcularPrecioAlquiler(inicio, fin, v);
                            System.out.println("Precio total del alquiler: " + precioTotal + " €");
                            
                        }
                        else{
                            System.out.println("Debe seleccionar un vehículo disponible");
                        }
                        break;
                    case 2: 
                        System.out.println("Sus alquileres registrados:");
                        Alquiler[] alqCliente = obtenerAlquileres(cliente);
                        
                        if (alqCliente == null || alqCliente.length == 0) {
                            System.out.println("No tiene ningún alquiler registrado actualmente.");
                            break;
                        }

                        for (int i = 0; i < alqCliente.length; i++) {
                            System.out.println(i + " ---> " + alqCliente[i]);
                        }

                        System.out.println("Introduzca el número del alquiler que desea entregar/devolver (-1 para cancelar):");
                        int seleccionAlquiler = KeyBoard.readInt();

                        if (seleccionAlquiler >= 0 && seleccionAlquiler < alqCliente.length) {
                            Alquiler alquilerADevolver = alqCliente[seleccionAlquiler];
                            if (alquilerADevolver != null && alquilerADevolver.getVehiculo() != null) {
                                // Marcamos el vehículo de nuevo como disponible
                                alquilerADevolver.getVehiculo().setDisponible(true);
                                System.out.println("Vehículo con matrícula " + alquilerADevolver.getVehiculo().getMatricula() + " entregado con éxito. Ya vuelve a estar disponible.");
                            }
                        } else {
                            System.out.println("Operación cancelada o índice no válido.");
                        }
                        break;
                    case 3:
                        Alquiler alqs [] = obtenerAlquileres(cliente);
                        for (Alquiler q: alqs){
                            System.out.print(q);
                        }
                    default:
                        break;
                }
            }
            else{
                System.out.println("Debe identificarse correctamente o salir de la app");
            }
        }
    }

    public Cliente identificarCliente(){

        boolean seleccionado = false;
        Cliente cliente = null;

        while (!seleccionado) {
        
            System.out.println("Bienvenido al Gestor de Alquileres");
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null) {
                    System.out.println(i + " ---> " + clientes[i].getNombre() + " - " + clientes[i].getDni());
                }
            }
            System.out.println("Identífiquese como cliente con su número de cliente (-1 para salir):");
            int numeroCliente = KeyBoard.readInt();
            if (numeroCliente == -1) {
                return null;
            }
            else if (numeroCliente < 0 || numeroCliente >= clientes.length || clientes[numeroCliente] == null) {
                System.out.println("identificarCliente:Número de cliente inválido.");
                return null;
            }
            else{
                seleccionado = true;
                cliente = clientes[numeroCliente];
            }
        }
        return cliente;
    }

    public Vehiculo seleccionarVehiculo(Cliente cliente){
        boolean seleccionado = false;
        Vehiculo vehiculo = null;

        while (!seleccionado) {
        
            System.out.println("Estimado Sr./Sra. " + cliente.getNombre());
            System.out.println("Introduzca el número correspondiente al tipo de vehículo que desea alquilar:");
            System.out.println("1. Turismo, 2. Motocicleta, 3. Vehículo Industrial: ");

            int tipoVehiculo = KeyBoard.readInt();
            String tipo = "";
            Vehiculo [] elegibles = new Vehiculo[100];

            switch (tipoVehiculo) {
                case 1:
                    System.out.println("Ha seleccionado Turismo.");
                    tipo = "Turismo";
                    break;
                case 2:
                    System.out.println("Ha seleccionado Motocicleta.");
                    tipo = "Motocicleta";
                    break;
                case 3:
                    System.out.println("Ha seleccionado Vehículo Industrial.");
                    tipo = "VehiculoIndustrial";
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione un tipo de vehículo válido.");
                    continue; // Volver a pedir la selección
            }

            System.out.println("Vehiculos disponibles");

            System.out.println("Estimado Sr./Sra. " + cliente.getNombre() + "\n seleccione categoría.");
            System.out.println("Introduzca el número correspondiente a la categoría que desea alquilar:");
            System.out.println("1. Normal, 2. Superior, 3. Especial, 4.Cualquiera: ");

            int categoriaVehiculo = KeyBoard.readInt();

            switch (categoriaVehiculo) {
                case 1:
                    System.out.println("Ha seleccionado Categoría Normal.");
                    elegibles = obtenerVehiculos(tipo, Categoria.NORMAL);
                    break;
                case 2:
                    System.out.println("Ha seleccionado Categoría Superior.");
                    elegibles = obtenerVehiculos(tipo, Categoria.SUPERIOR);
                    break;
                case 3:
                    System.out.println("Ha seleccionado Categoría Especial.");
                    elegibles = obtenerVehiculos(tipo, Categoria.ESPECIAL);
                    break;
                default:
                    elegibles = obtenerVehiculos(tipo, Categoria.ESPECIAL);
                    break; // Volver a pedir la selección
            }

            System.out.println("Coches elegibles");

            for(int i = 0; i < elegibles.length; i++){
                System.out.println(i + " ---> " + elegibles[i]);
            }

            System.out.println("Introduzca número coche (sólo si está disponible)");
            int seleccion = KeyBoard.readInt();
            vehiculo = elegibles[seleccion];
            seleccionado = true;
        }
        return vehiculo;
    }

    public void listarAlquileres (){
        System.out.println("Listado de alquileres");
        for (Alquiler alq: alquileres){
            System.out.println(alq);
        }
        System.out.println();
    }

    public void listarVehiculos(){
        System.out.println("Listado de vehículos");
        for (Vehiculo veh: vehiculos){
            System.out.println(veh);
        }
        System.out.println();
    }

    /**
     * Guarda los alquileres registrados en un fichero de texto en formato JSON.
     * @param nombreFichero Ruta o nombre del fichero.
     */
    public void guardarAlquileres(String nombreFichero) {
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for (int i = 0; i < contadorAlquileres; i++) {
            if (alquileres[i] != null) {
                jsonArray.put(new org.json.JSONObject(alquileres[i].toString()));
            }
        }
        try (java.io.FileWriter writer = new java.io.FileWriter(nombreFichero)) {
            writer.write(jsonArray.toString(4)); // Indentado para legibilidad
            System.out.println(">> Alquileres guardados correctamente en " + nombreFichero);
        } catch (java.io.IOException e) {
            System.out.println(">> Error al guardar los alquileres: " + e.getMessage());
        }
    }

    /**
     * Recupera los alquileres desde un fichero de texto JSON y los incorpora a la aplicación.
     * @param nombreFichero Ruta o nombre del fichero.
     */
    public void recuperarAlquileres(String nombreFichero) {
        try {
            String contenido = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(nombreFichero)));
            org.json.JSONArray jsonArray = new org.json.JSONArray(contenido);
            
            // Reiniciamos el almacenamiento actual del gestor
            this.contadorAlquileres = 0;
            this.alquileres = new Alquiler[1000];

            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);
                Alquiler alq = new Alquiler(jsonObj.toString());
                this.agregarAlquiler(alq);
            }
            System.out.println(">> Alquileres recuperados correctamente desde " + nombreFichero);
        } catch (java.io.IOException e) {
            System.out.println(">> Error al leer el fichero: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.out.println(">> Error al parsear el formato JSON: " + e.getMessage());
        }
    }
    
    public static void main(String [] args){
        GestorAlquileres app = new GestorAlquileres();
        app.menuPrincipal();
    }
}
