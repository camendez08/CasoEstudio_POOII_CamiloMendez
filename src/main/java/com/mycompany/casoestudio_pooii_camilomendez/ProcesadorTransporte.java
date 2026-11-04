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
}