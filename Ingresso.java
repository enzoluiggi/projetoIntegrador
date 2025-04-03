package com.mycompany.interfacegrafica;

public class Ingresso {

    private String cpfCliente;
    private String area;
    private double preco;
    private String sessao;
    private String peça;

    public Ingresso(String cpfCliente, String area, String sessao, String peça) {
        this.cpfCliente = cpfCliente;
        this.area = area;
        this.sessao = sessao;
        this.peça = peça;
        this.preco = calcularPreco(area);
    }

    // Método para validar o CPF
    public static boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (caso comum de CPFs inválidos)
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
                || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            return false;
        }

        // Valida o primeiro dígito 
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * peso--;
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) {
            digito1 = 0;
        }

        // Valida o segundo dígito 
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * peso--;
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) {
            digito2 = 0;
        }

        // Compara os dois dígitos 
        return digito1 == Integer.parseInt(String.valueOf(cpf.charAt(9))) && digito2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
    }

    //Metodo de calculo de preço
    private double calcularPreco(String area) {
        switch (area) {
            case "Plateia A":
                return 40.00;
            case "Plateia B":
                return 60.00;
            case "Camarote":
                return 80.00;
            case "Frisa":
                return 120.00;
            case "Balcão Nobre":
                return 250.00;
            default:
                return 0;
        }
    }

    // Getters
    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getArea() {
        return area;
    }

    public double getPreco() {
        return preco;
    }

    public String getSessao() {
        return sessao;
    }

    public String getPeça() {
        return peça;
    }

    @Override
    public String toString() {
        return "Ingresso [CPF: " + cpfCliente + ", Peça: " + peça + ", Sessão: " + sessao + ", Área: " + area + ", Preço: " + preco + "]";
    }
}
