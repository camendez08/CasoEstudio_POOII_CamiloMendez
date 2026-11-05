package com.mycompany.casoestudio_pooii_camilomendez;

import java.time.LocalDateTime;
import java.util.List;

public class CasoEstudio_POOII_CamiloMendez {

    public static void main(String[] args) {

        List<RegistroTransporte> registros = crearRegistros();

        mostrarRegistros(registros);
        mostrarAfluenciaPorEstacion(registros);
        mostrarFlujoPorHora(registros);
        mostrarHorasPico(registros);
        mostrarUsoPorRuta(registros);
        mostrarRutasMasUtilizadas(registros);
        mostrarPatronesViaje(registros);
        mostrarTiempoPromedio(registros);
        mostrarEstadoOcupacion(registros);
    }

    private static List<RegistroTransporte> crearRegistros() {

        return List.of(
                new RegistroTransporte(
                        "U001",
                        "R1",
                        "Estacion Centro",
                        "entrada",
                        LocalDateTime.of(2026, 11, 4, 7, 30)
                ),
                new RegistroTransporte(
                        "U001",
                        "R1",
                        "Estacion Centro",
                        "entrada",
                        LocalDateTime.of(2026, 11, 4, 8, 30)
                ),
                new RegistroTransporte(
                        "U002",
                        "R1",
                        "Estacion Centro",
                        "entrada",
                        LocalDateTime.of(2026, 11, 4, 7, 35)
                ),
                new RegistroTransporte(
                        "U001",
                        "R1",
                        "Estacion Norte",
                        "salida",
                        LocalDateTime.of(2026, 11, 4, 8, 5)
                ),
                new RegistroTransporte(
                        "U003",
                        "R2",
                        "Estacion Sur",
                        "entrada",
                        LocalDateTime.of(2026, 11, 4, 8, 15)
                ),
                new RegistroTransporte(
                        "U004",
                        "R2",
                        "Estacion Sur",
                        "entrada",
                        LocalDateTime.of(2026, 11, 4, 8, 20)
                ),
                new RegistroTransporte(
                        "U003",
                        "R2",
                        "Estacion Centro",
                        "salida",
                        LocalDateTime.of(2026, 11, 4, 9, 0)
                )
        );
    }

    private static void mostrarRegistros(
            List<RegistroTransporte> registros) {

        System.out.println("=== TECNOMOVIL DATA ===");
        System.out.println(
                "Registros cargados: " + registros.size()
        );

        registros.forEach(System.out::println);
    }

    private static void mostrarAfluenciaPorEstacion(
            List<RegistroTransporte> registros) {

        System.out.println(
                "\n=== AFLUENCIA POR ESTACION ==="
        );

        ProcesadorTransporte
                .calcularAfluenciaPorEstacion(registros)
                .forEach((estacion, cantidad) ->
                        System.out.println(
                                estacion
                                + ": "
                                + cantidad
                                + " entradas"
                        ));
    }

    private static void mostrarFlujoPorHora(
            List<RegistroTransporte> registros) {

        System.out.println("\n=== FLUJO POR HORA ===");

        ProcesadorTransporte
                .calcularFlujoPorHora(registros)
                .forEach((hora, cantidad) ->
                        System.out.println(
                                hora
                                + ":00 -> "
                                + cantidad
                                + " registros"
                        ));
    }

    private static void mostrarHorasPico(
            List<RegistroTransporte> registros) {

        System.out.println("\n=== HORAS PICO ===");

        ProcesadorTransporte
                .identificarHorasPico(registros)
                .forEach((hora, cantidad) ->
                        System.out.println(
                                hora
                                + ":00 con "
                                + cantidad
                                + " registros"
                        ));
    }

    private static void mostrarUsoPorRuta(
            List<RegistroTransporte> registros) {

        System.out.println("\n=== USO POR RUTA ===");

        ProcesadorTransporte
                .calcularUsoPorRuta(registros)
                .forEach((ruta, cantidad) ->
                        System.out.println(
                                ruta
                                + " -> "
                                + cantidad
                                + " registros"
                        ));
    }

    private static void mostrarRutasMasUtilizadas(
            List<RegistroTransporte> registros) {

        System.out.println(
                "\n=== RUTAS MAS UTILIZADAS ==="
        );

        ProcesadorTransporte
                .identificarRutasMasUtilizadas(registros)
                .forEach((ruta, cantidad) ->
                        System.out.println(
                                ruta
                                + " con "
                                + cantidad
                                + " registros"
                        ));
    }

    private static void mostrarPatronesViaje(
            List<RegistroTransporte> registros) {

        System.out.println(
                "\n=== PATRONES DE VIAJE POR USUARIO ==="
        );

        ProcesadorTransporte
                .obtenerPatronesViajePorUsuario(registros)
                .forEach((usuario, estaciones) ->
                        System.out.println(
                                usuario
                                + " -> "
                                + String.join(
                                        " -> ",
                                        estaciones
                                )
                        ));
    }

    private static void mostrarTiempoPromedio(
            List<RegistroTransporte> registros) {

        double tiempoPromedio =
                ProcesadorTransporte
                        .calcularTiempoPromedioEntreEstaciones(
                                registros
                        );

        System.out.println(
                "\n=== TIEMPO PROMEDIO ENTRE ESTACIONES ==="
        );

        System.out.printf(
                "Tiempo promedio: %.2f minutos%n",
                tiempoPromedio
        );
    }

    private static void mostrarEstadoOcupacion(
            List<RegistroTransporte> registros) {

        long umbralOcupacion = 3;

        System.out.println(
                "\n=== ESTADO DE OCUPACION DE RUTAS ==="
        );

        System.out.println(
                "Umbral simulado: "
                + umbralOcupacion
                + " registros"
        );

        ProcesadorTransporte
                .detectarSobrecargaRutas(
                        registros,
                        umbralOcupacion
                )
                .forEach((ruta, estado) ->
                        System.out.println(
                                ruta + " -> " + estado
                        ));
    }
}