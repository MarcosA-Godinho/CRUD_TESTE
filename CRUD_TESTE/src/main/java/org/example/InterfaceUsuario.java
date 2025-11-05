package org.example;

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
        System.out.println("**************************");
        System.out.println("* ESCOLHA UMA OPÇÃO:     *");
        System.out.println("* 1 - ADIÇÃO             *");
        System.out.println("* 2 - SUBTRAÇÃO          *");
        System.out.println("* 3 - MULTIPLICAÇÃO      *");
        System.out.println("* 4 - DIVISÃO            *");
        System.out.println("* 5 - SAIR DO PROGRAMA   *");
        System.out.println("**************************");
    }


}
