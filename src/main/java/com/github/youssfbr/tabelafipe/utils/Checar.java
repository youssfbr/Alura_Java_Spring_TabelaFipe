package com.github.youssfbr.tabelafipe.utils;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class Checar {

    private int numeroInteiro;
    private final Scanner sc = new Scanner(System.in);

    public int checarInteiro(String mensagem) {

        boolean opcao = true;
        while (opcao) {
            System.out.println("\n" + mensagem);

            try {
                numeroInteiro = sc.nextInt();
                opcao = false;
            } catch (InputMismatchException e) {
                System.out.println("""
                                \"""ERRO! O número digitado deveria ser um número inteiro."
                                Deseja tentar novamente? (s/n)
                                """);
                sc.nextLine();
                final char s = sc.nextLine().toLowerCase().charAt(0);
                if (s == 'n') opcao = false;
            }
        }

        return numeroInteiro;
    }
}
