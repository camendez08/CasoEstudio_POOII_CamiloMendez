package com.mycompany.casoestudio_pooii_camilomendez;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcesadorTransporte {

    public static Map<String, Long> calcularAfluenciaPorEstacion(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .filter(registro -> registro.accion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::estacion,
                        Collectors.counting()
                ));
    }

    public static Map<Integer, Long> calcularFlujoPorHora(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .collect(Collectors.groupingBy(
                        registro -> registro.timestamp().getHour(),
                        Collectors.counting()
                ));
    }
    
    public static Map<String, Long> calcularUsoPorRuta(
        List<RegistroTransporte> registros) {

    return registros.stream()
            .collect(Collectors.groupingBy(
                    RegistroTransporte::ruta,
                    Collectors.counting()
            ));
    }
    public static Map<String, Long> identificarRutasMasUtilizadas(
        List<RegistroTransporte> registros) {

    Map<String, Long> usoPorRuta = calcularUsoPorRuta(registros);

    long maximoUso = usoPorRuta.values()
            .stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0);

    return usoPorRuta.entrySet()
            .stream()
            .filter(entrada -> entrada.getValue() == maximoUso)
            .collect(Collectors.toUnmodifiableMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
            ));
    
    }
    public static Map<String, List<String>> obtenerPatronesViajePorUsuario(
        List<RegistroTransporte> registros) {

    return registros.stream()
            .sorted((r1, r2) -> r1.timestamp().compareTo(r2.timestamp()))
            .collect(Collectors.groupingBy(
                    RegistroTransporte::idUsuario,
                    Collectors.mapping(
                            RegistroTransporte::estacion,
                            Collectors.toUnmodifiableList()
                    )
            ));
}
}