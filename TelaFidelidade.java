package com.mycompany.interfacegrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaFidelidade extends JFrame {

    private JTextField nomeField, cpfField, telefoneField, enderecoField, dataNascimentoField;
    private JButton salvarButton;

    public TelaFidelidade() {
        setTitle("Cadastro Programa de Fidelidade");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // nome
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(20, 20, 100, 20);
        add(nomeLabel);
        nomeField = new JTextField();
        nomeField.setBounds(120, 20, 200, 20);
        add(nomeField);
        
        // CPF
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(20, 60, 100, 20);
        add(cpfLabel);
        cpfField = new JTextField();
        cpfField.setBounds(120, 60, 200, 20);
        add(cpfField);

        // Telefone
        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setBounds(20, 100, 100, 20);
        add(telefoneLabel);
        telefoneField = new JTextField();
        telefoneField.setBounds(120, 100, 200, 20);
        add(telefoneField);
        
        // Endereço
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setBounds(20, 140, 100, 20);
        add(enderecoLabel);
        enderecoField = new JTextField();
        enderecoField.setBounds(120, 140, 200, 20);
        add(enderecoField);
        
        // DtNasc
        JLabel dataNascimentoLabel = new JLabel("Data Nascimento:");
        dataNascimentoLabel.setBounds(20, 180, 120, 20);
        add(dataNascimentoLabel);
        dataNascimentoField = new JTextField();
        dataNascimentoField.setBounds(150, 180, 170, 20);
        add(dataNascimentoField);

        // Botão para salvar os dados
        salvarButton = new JButton("Salvar");
        salvarButton.setBounds(120, 220, 150, 30);
        add(salvarButton);

        // Ação do botão salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String endereco = enderecoField.getText();
                String dataNascimento = dataNascimentoField.getText();

                // Verificar se todos os campos foram preenchidos
                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || dataNascimento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro: Todos os campos são obrigatórios.");
                    return;
                }

                // Salvar no arquivo fidelidade.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("fidelidade.txt", true))) {
                    writer.write(nome + ";" + cpf + ";" + telefone + ";" + endereco + ";" + dataNascimento + "\n");
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.");
                    dispose();  // Fecha a janela de fidelidade
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar no arquivo.");
                }
            }
        });

        setVisible(true);
    }
}
