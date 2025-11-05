package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceUsuario {

    private Scanner sc;

    public InterfaceUsuario() {
        this.sc = new Scanner(System.in);
    }

    public void fechar() {
        sc.close();
    }

    public void exibirMenu() {
        System.out.println("\n**********************************");
        System.out.println("* ESCOLHA UMA OPÇÃO:              *");
        System.out.println("* 1 - CADASTRAR USUÁRIO           *");
        System.out.println("* 2 - VER USUÁRIOS CADASTRADOS    *");
        System.out.println("* 3 - DELETAR USUÁRIO             *");
        System.out.println("* 0 - SAIR                        *");
        System.out.println("***********************************");
    }

    public int lerOpcao() {
        try {
            int opcao = sc.nextInt();
            sc.nextLine();
            return opcao;
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Por favor, digite um NÚMERO.");
            sc.nextLine(); // Limpa o buffer do scanner
            return -1; // Retorna uma opção inválida
        }
    }

    //metodo para ler texto
    public String lerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    //metodo para ler números longos (telefone)
    public long lerLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                long valor = sc.nextLong();
                sc.nextLine(); // Consome o "Enter"
                return valor;
            } catch (InputMismatchException e) {
                System.err.println("ERRO: Por favor, digite um NÚMERO.");
                sc.nextLine(); // Limpa o buffer
            }
        }
    }

    public boolean perguntarSeContinua(){
        System.out.println("\n************************************");
        System.out.println("* DESEJA VOLTAR AO MENU INICIAL?   *");
        System.out.println("* DIGITE 1 PARA SIM OU 2 PARA NÃO. *");
        System.out.println("************************************");

        int continuar = sc.nextInt();

        if (continuar != 1){
            System.out.println("\nAté a proxima!");
            return false; // Não continuar (ativo = false)
        }
        return true; // Continuar (ativo = verdadeiro)
    }
}