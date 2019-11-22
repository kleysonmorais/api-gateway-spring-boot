package com.validator.bpm.demo.dto;

public class DeficientDto {

    private Integer coddef;
    private String descricao;

    public DeficientDto() {}

    public DeficientDto(Integer coddef, String descricao) {
        this.coddef = coddef;
        this.descricao = descricao;
    }

    public Integer getCoddef() {
        return coddef;
    }

    public void setCoddef(Integer coddef) {
        this.coddef = coddef;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeficientDto{");
        sb.append("coddef=").append(coddef);
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
