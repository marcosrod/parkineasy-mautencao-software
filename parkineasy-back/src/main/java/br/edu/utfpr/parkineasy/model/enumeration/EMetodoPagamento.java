package br.edu.utfpr.parkineasy.model.enumeration;

public enum EMetodoPagamento {
    
    CARTAO("Cartão"),
    DINHEIRO("Dinheiro"),
    PIX("Pix");
    
    private String descricao;

    EMetodoPagamento(String descricao) {
        this.descricao = descricao;
    }
}
