package com.mycompany.casoestudio_pooii_camilomendez;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CasoEstudio_POOII_CamiloMendez {

    public static void main(String[] args) {

        List<RegistroTransporte> registros = List.of(
                
            new RegistroTransporte(
                "U001", "R1", "Estacion Centro", "entrada",
                LocalDateTime.of(2026, 11, 4, 7, 30)
            ),
            
             new RegistroTransporte(
                "U001", "R1", "Estacion Centro", "entrada",
                LocalDateTime.of(2026, 11, 5, 7, 30)
            ),

            new RegistroTransporte(
                "U002", "R1", "Estacion Centro", "entrada",
                LocalDateTime.of(2026, 11, 4, 7, 35)
            ),

            new RegistroTransporte(
                "U001", "R1", "Estacion Norte", "salida",
                LocalDateTime.of(2026, 11, 4, 8, 5)
            ),

            new RegistroTransporte(
                "U003", "R2", "Estacion Sur", "entrada",
                LocalDateTime.of(2026, 11, 4, 8, 15)
            ),

            new RegistroTransporte(
                "U004", "R2", "Estacion Sur", "entrada",
                LocalDateTime.of(2026, 11, 4, 8, 20)
            ),

            new RegistroTransporte(
                "U003", "R2", "Estacion Centro", "salida",
                LocalDateTime.of(2026, 11, 4, 9, 0)
            )
        );

        System.out.println("=== TECNOMOVIL DATA ===");
        System.out.println("Registros cargados: " + registros.size());

        registros.forEach(System.out::println);
        System.out.println("\n=== AFLUENCIA POR ESTACION ===");

ProcesadorTransporte.calcularAfluenciaPorEstacion(registros)
        .forEach((estacion, cantidad) ->
                System.out.println(estacion + ": " + cantidad + " entradas"));

System.out.println("\n=== FLUJO POR HORA ===");

ProcesadorTransporte.calcularFlujoPorHora(registros)
        .forEach((hora, cantidad) ->
                System.out.println(hora + ":00 -> " + cantidad + " registros"));

System.out.println("\n=== USO POR RUTA ===");

ProcesadorTransporte.calcularUsoPorRuta(registros)
        .forEach((ruta, cantidad) ->
                System.out.println(ruta + " -> " + cantidad + " registros"));

System.out.println("\n=== RUTAS MAS UTILIZADAS ===");

ProcesadorTransporte.identificarRutasMasUtilizadas(registros)
        .forEach((ruta, cantidad) ->
                System.out.println(
                        ruta + " con " + cantidad + " registros"
                ));

System.out.println("\n=== PATRONES DE VIAJE POR USUARIO ===");

ProcesadorTransporte.obtenerPatronesViajePorUsuario(registros)
        .forEach((usuario, estaciones) ->
                System.out.println(
                        usuario + " -> " + String.join(" -> ", estaciones)
                ));
        }
}
