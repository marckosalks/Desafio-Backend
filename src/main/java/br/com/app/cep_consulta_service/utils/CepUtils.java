package br.com.app.cep_consulta_service.utils;

public class CepUtils {

    public static String limpar(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("\\D", "");
    }
}