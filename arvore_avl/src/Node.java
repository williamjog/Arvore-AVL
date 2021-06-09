package arvore_avl.src;

public class Node {
  
	private Node esquerda;
	private Node direita;
	private Node pai;
	private int chave;
	private int balanceamento;

	public Node(int inteiro) {
		setEsquerda(setDireita(setPai(null)));
		setBalanceamento(0);
		setChave(inteiro);
	}

	public String toString() {
		return Integer.toString(getChave());
	}

	public int getChave() {
		return chave;
	}

	public void setChave(int chave) {
		this.chave = chave;
	}

	public int getBalanceamento() {
		return balanceamento;
	}

	public void setBalanceamento(int balanceamento) {
		this.balanceamento = balanceamento;
	}

	public Node getPai() {
		return pai;
	}

	public Node setPai(Node pai) {
		this.pai = pai;
		return pai;
	}

	public Node getDireita() {
		return direita;
	}

	public Node setDireita(Node direita) {
		this.direita = direita;
		return direita;
	}

	public Node getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(Node esquerda) {
		this.esquerda = esquerda;
	}
}