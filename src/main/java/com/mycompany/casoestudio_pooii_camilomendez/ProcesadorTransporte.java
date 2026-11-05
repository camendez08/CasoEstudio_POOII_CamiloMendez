package com.mycompany.casoestudio_pooii_camilomendez;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesadorTransporte {

    private ProcesadorTransporte() {
        // Evita crear instancias de esta clase de utilidades.
    }

    public static Map<String, Long> calcularAfluenciaPorEstacion(
            List<RegistroTransporte> registros) {

        Map<String, Long> resultado = registros.stream()
                .filter(registro ->
                        registro.accion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::estacion,
                        Collectors.counting()
                ));

        return Map.copyOf(resultado);
    }

    public static Map<Integer, Long> calcularFlujoPorHora(
            List<RegistroTransporte> registros) {

        Map<Integer, Long> resultado = registros.stream()
                .collect(Collectors.groupingBy(
                        registro -> registro.timestamp().getHour(),
                        Collectors.counting()
                ));

        return Map.copyOf(resultado);
    }

    public static Map<Integer, Long> identificarHorasPico(
            List<RegistroTransporte> registros) {

        Map<Integer, Long> flujoPorHora =
                calcularFlujoPorHora(registros);

        long flujoMaximo = flujoPorHora.values()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return flujoPorHora.entrySet()
                .stream()
                .filter(entrada ->
                        entrada.getValue() == flujoMaximo)
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public static Map<String, Long> calcularUsoPorRuta(
            List<RegistroTransporte> registros) {

        Map<String, Long> resultado = registros.stream()
                .collect(Collectors.groupingBy(
                        RegistroTransporte::ruta,
                        Collectors.counting()
                ));

        return Map.copyOf(resultado);
    }

    public static Map<String, Long> identificarRutasMasUtilizadas(
            List<RegistroTransporte> registros) {

        Map<String, Long> usoPorRuta =
                calcularUsoPorRuta(registros);

        long maximoUso = usoPorRuta.values()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        return usoPorRuta.entrySet()
                .stream()
                .filter(entrada ->
                        entrada.getValue() == maximoUso)
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public static Map<String, List<String>>
            obtenerPatronesViajePorUsuario(
                    List<RegistroTransporte> registros) {

        Map<String, List<String>> patrones =
                registros.stream()
                        .sorted(Comparator.comparing(
                                RegistroTransporte::timestamp))
                        .collect(Collectors.groupingBy(
                                RegistroTransporte::idUsuario,
                                Collectors.mapping(
                                        RegistroTransporte::estacion,
                                        Collectors.toUnmodifiableList()
                                )
                        ));

        return Map.copyOf(patrones);
    }

    public static double calcularTiempoPromedioEntreEstaciones(
            List<RegistroTransporte> registros) {

        Map<String, List<RegistroTransporte>> registrosPorUsuario =
                registros.stream()
                        .collect(Collectors.groupingBy(
                                RegistroTransporte::idUsuario
                        ));

        return registrosPorUsuario.values()
                .stream()
                .flatMapToLong(lista -> {

                    List<RegistroTransporte> ordenados =
                            lista.stream()
                                    .sorted(Comparator.comparing(
                                            RegistroTransporte::timestamp))
                                    .toList();

                    return IntStream
                            .range(0, Math.max(0, ordenados.size() - 1))
                            .filter(indice -> estacionesSonDiferentes(
                                    ordenados.get(indice),
                                    ordenados.get(indice + 1)
                            ))
                            .mapToLong(indice -> calcularMinutosEntre(
                                    ordenados.get(indice),
                                    ordenados.get(indice + 1)
                            ));
                })
                .average()
                .orElse(0.0);
    }

    private static boolean estacionesSonDiferentes(
            RegistroTransporte registroInicial,
            RegistroTransporte registroFinal) {

        return !registroInicial.estacion()
                .equalsIgnoreCase(registroFinal.estacion());
    }

    private static long calcularMinutosEntre(
            RegistroTransporte registroInicial,
            RegistroTransporte registroFinal) {

        return Duration.between(
                registroInicial.timestamp(),
                registroFinal.timestamp()
        ).toMinutes();
    }

    public static Map<String, String> detectarSobrecargaRutas(
            List<RegistroTransporte> registros,
            long umbral) {

        if (umbral < 0) {
            throw new IllegalArgumentException(
                    "El umbral no puede ser negativo"
            );
        }

        return calcularUsoPorRuta(registros)
                .entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entrada -> entrada.getValue() > umbral
                                ? "CRITICA"
                                : "NORMAL"
                ));
    }
}