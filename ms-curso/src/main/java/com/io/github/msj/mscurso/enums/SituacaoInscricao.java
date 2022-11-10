package com.io.github.msj.mscurso.enums;

public enum SituacaoInscricao {

    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private String situacao;

    SituacaoInscricao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return situacao;
    }
}
