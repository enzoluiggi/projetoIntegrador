package com.mycompany.interfacegrafica;

import java.util.*;

public class Teatro {

    private List<Ingresso> ingressosVendidos = new ArrayList<>();

    // Mapa para armazenar o número de poltronas ocupadas por área, sessão e peça
    private Map<String, Set<Integer>> poltronasOcupadas = new HashMap<>();

    // Mapa com a quantidade de poltronas por área
    private Map<String, Integer> capacidadePorArea = new HashMap<>();

    public Teatro() {
        // Inicializando a capacidade das áreas
        capacidadePorArea.put("Plateia A", 25);
        capacidadePorArea.put("Plateia B", 100);
        capacidadePorArea.put("Frisa", 5);
        capacidadePorArea.put("Camarote", 10);
        capacidadePorArea.put("Balcão Nobre", 50);
    }

    // Método para vender ingresso
    public void venderIngresso(Ingresso ingresso) {
        // Verificando se o CPF já comprou ingresso para a mesma área, sessão e peça
        for (Ingresso i : ingressosVendidos) {
            if (i.getCpfCliente().equals(ingresso.getCpfCliente())
                    && i.getArea().equals(ingresso.getArea())
                    && i.getSessao().equals(ingresso.getSessao())
                    && i.getPeça().equals(ingresso.getPeça())) {
                throw new IllegalArgumentException("Erro: Este CPF já comprou ingresso para esta área, sessão e peça.");
            }
        }

        //obs: A partir daqui, estamos usand muitos conceitos de hashmap para facilitar toda a estrutura do código, pois em chaves facilita a obtençao desses dados.
        //Essas variáveis são preenchidas no loop que percorre os ingressos vendidos e são usadas para gerar a verificação de poltronas e posteriomente também usaremos para definir o gerador de relatório, 
        //composto por lucro total, vendas por peça, vendas por sessão, lucro por peça e sessão, e etc. Portanto, é mais otimizado e rápido do que usar vetores e varrê-los.
        
        // Verificando a disponibilidade da poltrona
        String chavePoltrona = ingresso.getArea() + "-" + ingresso.getSessao() + "-" + ingresso.getPeça();
        Set<Integer> poltronas = poltronasOcupadas.getOrDefault(chavePoltrona, new HashSet<>());

        // Verifica se a poltrona já foi ocupada
        if (poltronas.size() >= capacidadePorArea.get(ingresso.getArea())) {
            throw new IllegalArgumentException("Erro: Não há mais poltronas disponíveis para essa área.");
        }

        // Adiciona a poltrona ocupada
        int numeroPoltrona = poltronas.size() + 1;
        poltronas.add(numeroPoltrona);

        // Registra as poltronas ocupadas para a chave (área, sessão e peça)
        poltronasOcupadas.put(chavePoltrona, poltronas);

        // Adiciona o ingresso na lista de vendidos e salva no arquivo
        ingressosVendidos.add(ingresso);
        Arquivo.salvarVenda(ingresso);
    }
    // Gera os relatórios para a interface

    public String gerarRelatorioEstatisticas() {
        Map<String, Integer> vendasPorPeça = new HashMap<>();
        Map<String, Integer> vendasPorSessao = new HashMap<>();
        Map<String, Double> lucroPorPeçaSessao = new HashMap<>();

        double lucroTotal = 0;
        Map<String, Double> lucroPorArea = new HashMap<>();

        for (Ingresso ingresso : ingressosVendidos) {
            vendasPorPeça.put(ingresso.getPeça(), vendasPorPeça.getOrDefault(ingresso.getPeça(), 0) + 1);
            vendasPorSessao.put(ingresso.getSessao(), vendasPorSessao.getOrDefault(ingresso.getSessao(), 0) + 1);
            String key = ingresso.getPeça() + " - " + ingresso.getSessao();
            lucroPorPeçaSessao.put(key, lucroPorPeçaSessao.getOrDefault(key, 0.0) + ingresso.getPreco());
            lucroTotal += ingresso.getPreco();

            lucroPorArea.put(ingresso.getArea(), lucroPorArea.getOrDefault(ingresso.getArea(), 0.0) + ingresso.getPreco());
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Lucro Total: ").append(lucroTotal).append("\n");

        // Estatísticas por peça
        for (Map.Entry<String, Integer> entry : vendasPorPeça.entrySet()) {
            relatorio.append("Vendas de ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Estatísticas por sessão
        for (Map.Entry<String, Integer> entry : vendasPorSessao.entrySet()) {
            relatorio.append("Vendas de ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Lucro por área
        for (Map.Entry<String, Double> entry : lucroPorArea.entrySet()) {
            relatorio.append("Lucro da área ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Lucro por peça e sessão
        for (Map.Entry<String, Double> entry : lucroPorPeçaSessao.entrySet()) {
            relatorio.append("Lucro de ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return relatorio.toString();
    }
}
