package com.github.youssfbr.tabelafipe.principal;

import com.github.youssfbr.tabelafipe.services.ConsumoApi;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Principal {

    private final ConsumoApi consumoApi;
    private final Scanner sc = new Scanner(System.in);
    private static final String URL = "https://parallelum.com.br/fipe/api/v1/";

    public Principal(ConsumoApi consumoApi) {
        this.consumoApi = consumoApi;
    }

    public void exibeMenu() {

        System.out.println("""
                \n**** OPÇÕES ****
                Carro
                Moto
                Caminhão
                """);

        String uri = "";
        System.out.println("Digite uma das opções para consultar valores: ");
        String veiculo = sc.nextLine().toLowerCase();

        if (!veiculo.equals("carro") && !"moto".equals(veiculo) && !veiculo.equals("caminhao")) {
            System.out.println("Veículo inválido!");
        } else {
            uri = URL + veiculo.concat("s") + "/marcas";

            final String json = consumoApi.obterDados(uri);
            System.out.println(json);
        }

    }
}
