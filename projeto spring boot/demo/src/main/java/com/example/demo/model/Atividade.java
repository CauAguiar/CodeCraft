package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Nome da atividade (ex.: "Quiz sobre Java")

    @Column(nullable = false)
    private String tipo; // Tipo da atividade (pode ser "quiz", "video", "exercicio_aberto", etc.)

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    @JsonBackReference
    private Modulo modulo; // O módulo ao qual a atividade pertence

    @Column(nullable = false)
    private Boolean habilitada; // Indica se a atividade está disponível

    @Column(nullable = false)
    private Boolean concluida; // Indica se a atividade foi concluída pelo aluno

    // Atributos específicos dependendo do tipo de atividade
    @Column(nullable = true)
    private Long atividadeEspecificaId; // Armazenará o ID da atividade específica (como Quiz, Video, etc.)

    // Atributos de controle de progresso do aluno
    @Column(nullable = true)
    private Integer acertos; // Para atividades de quiz ou exercícios, número de acertos
    @Column(nullable = true)
    private Integer erros; // Para atividades de quiz ou exercícios, número de erros
    
    @ManyToOne
    @JoinColumn(name = "nivelamento_id")
    private Nivelamento nivelamento;

    public Nivelamento getNivelamento() {
        return nivelamento;
    }

    public void setNivelamento(Nivelamento nivelamento) {
        this.nivelamento = nivelamento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Long getAtividadeEspecificaId() {
        return atividadeEspecificaId;
    }

    public void setAtividadeEspecificaId(Long atividadeEspecificaId) {
        this.atividadeEspecificaId = atividadeEspecificaId;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public Integer getErros() {
        return erros;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }
}
