//Alunos:
//João Pedro Marques Alves 
//Gabryel Santana
//Ezequiel Paiva
//Enzo Luiggi Lazaro Garcia
//Contexto do projeto: O teatro ABC está contratando sua equipe para construir um sistema que faça o controle das vendas de ingressos dele.
//O software solicitado deverá conter no mínimo as seguintes funcionalidades e deverá ser feito em Java Desktop:
//
//1 - Comprar Ingresso: essa funcionalidade deverá permitir com que o cliente informe seu CPF e compre o ingresso
//de determinada peça (três peças) em determinada sessão (manhã, tarde e noite). Além dessas informações será
//necessário especificar qual área deseja, pois o valor do ingresso depende da área escolhida e a poltrona que irá
//ocupar.
//
//2 - Imprimir Ingresso: essa funcionalidade deverá permitir com que o cliente informe seu CPF e imprima em tela
//o comprovante do ingresso comprado por ele. 
//
//3 - Estatística de Vendas: Essa funcionalidade permite visualizar:
//- Qual peça teve mais e menos ingressos vendidos;
//- Qual sessão teve maior e menor ocupação de poltronas;
//- Qual a peça/sessão mais lucrativa e menos lucrativa;
//- Lucro médio do teatro com todas as áreas por peça.
//Package nomeada Interface Grafica para previnir erros de maven 
package com.mycompany.interfacegrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceGrafica {

    private JFrame frame;
    private JTextField cpfField;
    private JComboBox<String> areaComboBox;
    private JComboBox<String> sessaoComboBox;
    private JComboBox<String> peçaComboBox;
    private JTextArea displayArea;

    private Teatro teatro;

    public InterfaceGrafica() {
        teatro = new Teatro();

        // Abertura de Interface
        frame = new JFrame("Teatro do Zezinho Chiq Demais");
        frame.setSize(700, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Nós utilizamos caixas de seleção para facilitar escolha do usuário
        // CPF
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(20, 20, 100, 20);
        frame.add(cpfLabel);

        cpfField = new JTextField();
        cpfField.setBounds(120, 20, 200, 20);
        frame.add(cpfField);

        // Área
        JLabel areaLabel = new JLabel("Área:");
        areaLabel.setBounds(20, 60, 100, 20);
        frame.add(areaLabel);

        String[] areas = {"Plateia A", "Plateia B", "Camarote", "Frisa", "Balcão Nobre"};
        areaComboBox = new JComboBox<>(areas);
        areaComboBox.setBounds(120, 60, 200, 20);
        frame.add(areaComboBox);

        // Sessão
        JLabel sessaoLabel = new JLabel("Sessão:");
        sessaoLabel.setBounds(20, 100, 100, 20);
        frame.add(sessaoLabel);

        String[] sessoes = {"Manhã", "Tarde", "Noite"};
        sessaoComboBox = new JComboBox<>(sessoes);
        sessaoComboBox.setBounds(120, 100, 200, 20);
        frame.add(sessaoComboBox);

        // Peça
        JLabel peçaLabel = new JLabel("Peça:");
        peçaLabel.setBounds(20, 140, 100, 20);
        frame.add(peçaLabel);

        String[] peças = {"Peça 1", "Peça 2", "Peça 3"};
        peçaComboBox = new JComboBox<>(peças);
        peçaComboBox.setBounds(120, 140, 200, 20);
        frame.add(peçaComboBox);

        // Botão para comprar ingresso
        JButton comprarButton = new JButton("Comprar Ingresso");
        comprarButton.setBounds(120, 180, 150, 30);
        frame.add(comprarButton);

        // Botão para ver as estatísticas
        JButton estatisticasButton = new JButton("Ver Estatísticas");
        estatisticasButton.setBounds(120, 220, 150, 30);
        frame.add(estatisticasButton);

        // Botão para ver as vendas
        JButton vendasButton = new JButton("Ver Vendas");
        vendasButton.setBounds(120, 260, 150, 30);
        frame.add(vendasButton);

        // Botão para Programa de Fidelidade
        JButton fidelidadeButton = new JButton("Programa de Fidelidade");
        fidelidadeButton.setBounds(120, 300, 200, 30);
        frame.add(fidelidadeButton);

        // Exibição de resultados na tela da interface gráfica
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(35, 360, 600, 300);
        frame.add(scrollPane);

        // Ação para comprar ingresso
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                String area = (String) areaComboBox.getSelectedItem();
                String sessao = (String) sessaoComboBox.getSelectedItem();
                String peça = (String) peçaComboBox.getSelectedItem();

                // Validação da ação
                if (cpf.isEmpty() || area == null || sessao == null || peça == null) {
                    displayArea.setText("Erro: Todos os campos são obrigatórios.");
                    return;
                }

                // Validação do CPF
                if (!Ingresso.validarCPF(cpf)) {
                    displayArea.setText("Erro: CPF inválido.");
                    return;
                }

                Ingresso ingresso = new Ingresso(cpf, area, sessao, peça);
                try {
                    teatro.venderIngresso(ingresso);
                    displayArea.setText("Ingresso comprado com sucesso!\n" + ingresso.toString());
                } catch (IllegalArgumentException ex) {
                    displayArea.setText(ex.getMessage());
                }
            }
        });

        // Ação para ver estatísticas
        estatisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String relatorio = teatro.gerarRelatorioEstatisticas();
                displayArea.setText(relatorio);
            }
        });

        // Ação para ver vendas
        vendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Ingresso> vendas = Arquivo.recuperarVendas();
                if (vendas.isEmpty()) {
                    displayArea.setText("Nenhuma venda registrada.");
                } else {
                    StringBuilder vendasText = new StringBuilder("Vendas Registradas:\n");
                    for (Ingresso ingresso : vendas) {
                        vendasText.append(ingresso.toString()).append("\n");
                    }
                    displayArea.setText(vendasText.toString());
                }
            }
        });

        // Ação para abrir a tela de fidelidade
        fidelidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaFidelidade();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new InterfaceGrafica();
    }
}
