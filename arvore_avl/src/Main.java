package arvore_avl.src;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		ArvoreAvl arvore = new ArvoreAvl();
		
		Scanner sc = new Scanner(System.in);
		
		boolean opcaoMenu = true;
		
		while(opcaoMenu) {
			System.out.println("DIGITE A OPÇÃO ESCOLHIDA:");
			System.out.println("1 - [Inserir] - 2 [Remover] - 3 [Procurar] - 4 [Sair]");
			
			try {
				int opcao = Integer.parseInt(sc.nextLine());
				
				switch (opcao) {
				
				case 1: 
					System.out.println("\nDigite o número inteiro: ");
					int numeroAAdicionar = Integer.parseInt(sc.nextLine());
					arvore.inserir(numeroAAdicionar);
					arvore.print("\n", arvore.getRaiz(), false);
					System.out.println("");
					break;
				
				case 2:
					System.out.println("Digite o número inteiro: ");
					int numeroARemover = Integer.parseInt(sc.nextLine());
					arvore.remover(numeroARemover);
					arvore.print("\n", arvore.getRaiz(), false);
					System.out.println("");
					break;
					
				case 3:
					System.out.println("Digite o numero a ser procurado: ");
					Node numeroAProcurar = new Node(Integer.parseInt(sc.nextLine()));
					Node existe = arvore.procurarNumero(numeroAProcurar.getChave());
					if (existe != null) {
						System.out.printf("O número %d existe na árvore!\n\n", numeroAProcurar.getChave());
					} else {
						System.out.printf("O número %d não existe na árvore!\n\n", numeroAProcurar.getChave());
					}
					break;
					
				case 4: 
					opcaoMenu = false;
					System.out.println("Programa encerrado.");
					break;
					
				default:
					System.out.println("Digite um número inteiro! \n");
					break;
				}		
				
			} catch (Exception NumberFormatException) {
				System.out.println("Digite um número inteiro! \n");
			}
		}
		
		System.out.println("\nEm ordem:");
		System.out.println(arvore.emOrdem());
		
		System.out.println("\nPre Ordem:");
		arvore.preOrdem(arvore.getRaiz());
		
		System.out.println("\n\nPós Ordem:");
		arvore.posOrdem(arvore.getRaiz());
		
		sc.close();
	}	
}

