package com.mycompany.interfacegrafica;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Arquivo {

    private static final String ARQUIVO_VENDAS = "vendas.txt";

    // salva a venda dentro do arquivo de vendas
    public static void salvarVenda(Ingresso ingresso) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_VENDAS, true))) {
            writer.write(ingresso.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ingresso> recuperarVendas() {
        List<Ingresso> vendas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_VENDAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Ingresso")) {
                    String cpf = extractValue(linha, "CPF");
                    String area = extractValue(linha, "Área");
                    String sessao = extractValue(linha, "Sessão");
                    String peça = extractValue(linha, "Peça");
                    String precoStr = extractValue(linha, "Preço");
                    precoStr = precoStr.replace("]", "").trim();
                    double preco = Double.parseDouble(precoStr);

                    vendas.add(new Ingresso(cpf, area, sessao, peça));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendas;
    }

    private static String extractValue(String linha, String chave) {
        int startIndex = linha.indexOf(chave + ":") + chave.length() + 1;
        int endIndex = linha.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = linha.indexOf("]", startIndex);
        }
        return linha.substring(startIndex, endIndex).trim();
    }
}
