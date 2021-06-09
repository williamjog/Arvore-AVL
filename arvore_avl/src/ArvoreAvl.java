package arvore_avl.src;
import java.util.ArrayList;

public class ArvoreAvl {

  protected Node raiz;
  
	public Node getRaiz() {
		return raiz;
	}
	
	private int altura(Node atual) {
		if (atual == null) {
			return -1;
		}
		if (atual.getEsquerda() == null && atual.getDireita() == null) {
			return 0;
		} 
    else if (atual.getEsquerda() == null) {
			return 1 + altura(atual.getDireita());
		} 
    else if (atual.getDireita() == null) {
			return 1 + altura(atual.getEsquerda());
		} 
    else {
			return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
		}
	}
	
	private void checarBalanceamento(Node atual) {
		setBalanceamento(atual);
		int balanceamento = atual.getBalanceamento();
		if (balanceamento == -2) {
			if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
				atual = rotacaoDireita(atual);
			} else {
				atual = duplaRotacaoEsquerdaDireita(atual);
			}
		} else if (balanceamento == 2) {
			if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
				atual = rotacaoEsquerda(atual);
			} else {
				atual = duplaRotacaoDireitaEsquerda(atual);
			}
		}
		if (atual.getPai() != null) {
			checarBalanceamento(atual.getPai());
		} else {
			this.raiz = atual;
		}
	}

	public void inserir(int numero) {
		Node no = new Node(numero);
		inserirPrivado(this.raiz, no);
	}
	

	private void inserirPrivado(Node raiz, Node noInserido) {
		if (raiz == null) {
			this.raiz = noInserido;
		}
		else {
			if (noInserido.getChave() < raiz.getChave()) {
				if (raiz.getEsquerda() == null) {
					raiz.setEsquerda(noInserido);
					noInserido.setPai(raiz);
					checarBalanceamento(raiz);
				} else {
					inserirPrivado(raiz.getEsquerda(), noInserido);
				}
			} else if (noInserido.getChave() > raiz.getChave()) {
				if (raiz.getDireita() == null) {
					raiz.setDireita(noInserido);
					noInserido.setPai(raiz);
					checarBalanceamento(raiz);
				} else {
					inserirPrivado(raiz.getDireita(), noInserido);
				}
			} else {
				return;
			}
		}
	}

	public void remover(int numero) {
		removerPrivado(this.raiz, numero);
	}

	private void removerPrivado(Node atual, int numero) {
		if (atual == null) {
			return;
		} else {
			if (atual.getChave() > numero) {
				removerPrivado(atual.getEsquerda(), numero);
			} else if (atual.getChave() < numero) {
				removerPrivado(atual.getDireita(), numero);
			} else if (atual.getChave() == numero) {
				removerNoEncontrado(atual);
			}
		}
	}

	private void removerNoEncontrado(Node removido) {
		Node temporario;
		if (removido.getEsquerda() == null || removido.getDireita() == null) {
			if (removido.getPai() == null) {
				this.raiz = null;
				removido = null;
				return;
			}
			temporario = removido;
		} 
    else {
			temporario = sucessor(removido);
			removido.setChave(temporario.getChave());
		}

		Node pai;
		if (temporario.getEsquerda() != null) {
			pai = temporario.getEsquerda();
		} else {
			pai = temporario.getDireita();
		}
		if (pai != null) {
			pai.setPai(temporario.getPai());
		}

		if (temporario.getPai() == null) {
			this.raiz = pai;
		} else {
			if (temporario == temporario.getPai().getEsquerda()) {
				temporario.getPai().setEsquerda(pai);
			} else {
				temporario.getPai().setDireita(pai);
			}
			checarBalanceamento(temporario.getPai());
		}
		temporario = null;
	}

	private Node rotacaoEsquerda(Node inicial) {
		Node direita = inicial.getDireita();
		direita.setPai(inicial.getPai());
		inicial.setDireita(direita.getEsquerda());
		
		if (inicial.getDireita() != null) {
      inicial.getDireita().setPai(inicial);
    }
	
		direita.setEsquerda(inicial);
		inicial.setPai(direita);

		if (direita.getPai() != null) {
			if (direita.getPai().getDireita() == inicial) {
				direita.getPai().setDireita(direita);
			} else if (direita.getPai().getEsquerda() == inicial) {
				direita.getPai().setEsquerda(direita);
			}
		}
		setBalanceamento(inicial);
		setBalanceamento(direita);

		return direita;
	}

	private Node rotacaoDireita(Node inicial) {
		Node esquerda = inicial.getEsquerda();
		esquerda.setPai(inicial.getPai());

		inicial.setEsquerda(esquerda.getDireita());

		if (inicial.getEsquerda() != null) {
			inicial.getEsquerda().setPai(inicial);
		}

		esquerda.setDireita(inicial);
		inicial.setPai(esquerda);

		if (esquerda.getPai() != null) {
			if (esquerda.getPai().getDireita() == inicial) {
				esquerda.getPai().setDireita(esquerda);
			} else if (esquerda.getPai().getEsquerda() == inicial) {
				esquerda.getPai().setEsquerda(esquerda);
			}
		}
		setBalanceamento(inicial);
		setBalanceamento(esquerda);
		return esquerda;
	}

	private Node duplaRotacaoEsquerdaDireita(Node inicial) {
		inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
		return rotacaoDireita(inicial);
	}

	private Node duplaRotacaoDireitaEsquerda(Node inicial) {
		inicial.setDireita(rotacaoDireita(inicial.getDireita()));
		return rotacaoEsquerda(inicial);
	}

	private Node sucessor(Node antigo) {
		if (antigo.getDireita() != null) {
			Node ladoEsquerdo = antigo.getDireita();
			while (ladoEsquerdo.getEsquerda() != null) {
				ladoEsquerdo = ladoEsquerdo.getEsquerda();
			}
			return ladoEsquerdo;
		} 
    else {
			Node pai = antigo.getPai();
			while (pai != null && antigo == pai.getDireita()) {
				antigo = pai;
				pai = antigo.getPai();
			}
			return pai;
		}
	}
	
	private void setBalanceamento(Node no) {
		no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
	}

	public ArrayList<Node> emOrdem() {
		ArrayList<Node> arvore = new ArrayList<Node>();
		imprimirNaOrdem(raiz, arvore);
		return arvore;
	}

	private void imprimirNaOrdem(Node no, ArrayList<Node> arvoreList) { //  ERD = esquerda, raiz, direita
		if (no == null)	return;
		imprimirNaOrdem(no.getEsquerda(), arvoreList);
		arvoreList.add(no);
		imprimirNaOrdem(no.getDireita(), arvoreList);
	}
	
	
	public void preOrdem(Node raiz) { // RED = raiz, esquerda, direita
		if (raiz != null) {
      System.out.print(raiz.getChave() + " ");
      preOrdem(raiz.getEsquerda());
      preOrdem(raiz.getDireita());
		}
	 }
		  
    public void posOrdem(Node raiz) { // EDR = esquerda, direita, raiz
    	if (raiz != null) {
    		posOrdem(raiz.getEsquerda());
    		posOrdem(raiz.getDireita());
	      	System.out.print(raiz.getChave() + " ");
        }
    } 	
    
    public void print(String prefixo, Node node, boolean naEsquerda) { // Baseado em https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
      if (node != null) {
        print(prefixo + "     ", node.getDireita(), false);
        System.out.println (prefixo + ("|-- ") + node.getChave());
        print(prefixo + "     ", node.getEsquerda(), true);
      }
    }
    
    public Node procurarNumero(int procurado) {  
      Node atual = this.getRaiz();              
      while (atual != null) {
        if (atual.getChave() == procurado) {
          break;
        }
        atual = atual.getChave() < procurado ? atual.getDireita() : atual.getEsquerda();
      }
      return atual;
    } 
}

   
