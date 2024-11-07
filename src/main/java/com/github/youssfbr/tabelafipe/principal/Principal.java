package com.github.youssfbr.tabelafipe.principal;

import com.github.youssfbr.tabelafipe.models.DadosDTO;
import com.github.youssfbr.tabelafipe.models.ModelosDTO;
import com.github.youssfbr.tabelafipe.models.VeiculoDTO;
import com.github.youssfbr.tabelafipe.services.ConsumoApi;
import com.github.youssfbr.tabelafipe.services.ConverteDados;
import com.github.youssfbr.tabelafipe.utils.Checar;
import com.github.youssfbr.tabelafipe.utils.Mensagens;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Principal {

    private final Checar checar;
    private final ConsumoApi consumoApi;
    private final ConverteDados conversor;
    private final Scanner sc = new Scanner(System.in);
    private static final String URL = "https://parallelum.com.br/fipe/api/v1/";

    public Principal(Checar checar , ConsumoApi consumoApi , ConverteDados conversor) {
        this.checar = checar;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void exibeMenu() {

        System.out.println(Mensagens.MENSAGENS_OPCOES);
        String veiculo = sc.nextLine().toLowerCase();

        if (!veiculo.equals("carro") && !"moto".equals(veiculo) && !veiculo.equals("caminhao")) {
            System.out.println("Veículo inválido!");
        } else {

            if (veiculo.equals("caminhao")) veiculo="caminhoe";

            final String URI_DADOS_VEICULOS = URL + veiculo.concat("s") + "/marcas";

            String json = consumoApi.obterDados(URI_DADOS_VEICULOS);
            System.out.println(json);

            // Usando Array
            final DadosDTO[] marcasArray = conversor.obterDados(json , DadosDTO[].class);

            // Usando Lista
//            final List<DadosDTO> marcasList = conversor.obterLista(json , DadosDTO.class);

//            for (DadosDTO dadosDTO : dados) {
//                System.out.println(dadosDTO);
//            }

            // Busca por Array
            System.out.println();
            arraysDe(marcasArray).forEach(System.out::println);
//                    Arrays.stream(marcasArray)
//                    .sorted(Comparator.comparing(DadosDTO::codigo))
//                    .toList();

            // Busca por List
//            System.out.println();
//            marcasList.stream()
//                    .sorted(Comparator.comparing(DadosDTO::codigo))
//                    .forEach(System.out::println);

// ---------------------------------------------------------------------------------

            int marca = checar.checarInteiro("Informe o código da marca para consulta: ");

            final String URI_MODELOS = URI_DADOS_VEICULOS + "/" + marca + "/modelos";
            json = consumoApi.obterDados(URI_MODELOS);

            final ModelosDTO dadosModelo = conversor.obterDados(json , ModelosDTO.class);

            System.out.println("\nModelos dessa marca:");
            getList(dadosModelo).forEach(System.out::println);

// ---------------------------------------------------------------------------------

            System.out.println("\nDigite um trecho do nome do veiculo para consulta: ");
            final String TRECHO_MODELO_VEICULO = sc.nextLine();

            final List<DadosDTO> modelosFiltrados = dadosModelo.modelos()
                    .stream()
                    .filter(m -> m.nome().toLowerCase().contains(TRECHO_MODELO_VEICULO.toLowerCase()))
                    .toList();

            System.out.println("\nModelos filtrados");
            modelosFiltrados.forEach(System.out::println);

// ---------------------------------------------------------------------------------

            int codmodelo = checar.checarInteiro("Informe o código do modelo da marca do veiculo para consulta dos valores de avaliação: ");
            final String URI_CODIGO_MODELOS = URI_DADOS_VEICULOS + "/" + marca + "/modelos/" + codmodelo + "/anos";
            json = consumoApi.obterDados(URI_CODIGO_MODELOS);
            final DadosDTO[] dadosDTOS = conversor.obterDados(json , DadosDTO[].class);
            final ArrayList<VeiculoDTO> veiculos = new ArrayList<>();

            for (int i=0 ; i < dadosDTOS.length ; i++) {
                var enderecoAnos = URI_CODIGO_MODELOS + "/" + dadosDTOS[i].codigo();
                json = consumoApi.obterDados(enderecoAnos);
                veiculos.add(conversor.obterDados(json , VeiculoDTO.class));
            }

            System.out.println("\nVeículos encontrados por ano:");
            System.out.println("-------------------------------------------------");
            veiculos.forEach(System.out::println);
        }
    }

    private static List<DadosDTO> arraysDe(DadosDTO[] dadosDTOS) {
        return Arrays.stream(dadosDTOS)
                .sorted(Comparator.comparing(DadosDTO::codigo))
                .toList();
    }

    private static List<DadosDTO> getList(ModelosDTO dadosModelo) {
        return dadosModelo.modelos()
                .stream()
                .sorted(Comparator.comparing(DadosDTO::codigo))
                .toList();
    }
}
