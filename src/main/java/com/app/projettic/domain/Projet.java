package com.app.projettic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "projet")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Lob
    @Column(name = "description_pdf")
    private byte[] descriptionPDF;

    @Column(name = "description_pdf_content_type")
    private String descriptionPDFContentType;

    @Column(name = "description_texte")
    private String descriptionTexte;

    @Column(name = "nb_etudiant")
    private Integer nbEtudiant;

    @Column(name = "automatique")
    private Boolean automatique;

    @Column(name = "archive")
    private Boolean archive;

    @OneToOne
    @JoinColumn(unique = true)
    private Groupe groupe;

    @OneToOne
    @JoinColumn(unique = true)
    private User userExtra;

    @OneToMany(mappedBy = "projet")
    private Set<Document> documents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Projet nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getDescriptionPDF() {
        return descriptionPDF;
    }

    public Projet descriptionPDF(byte[] descriptionPDF) {
        this.descriptionPDF = descriptionPDF;
        return this;
    }

    public void setDescriptionPDF(byte[] descriptionPDF) {
        this.descriptionPDF = descriptionPDF;
    }

    public String getDescriptionPDFContentType() {
        return descriptionPDFContentType;
    }

    public Projet descriptionPDFContentType(String descriptionPDFContentType) {
        this.descriptionPDFContentType = descriptionPDFContentType;
        return this;
    }

    public void setDescriptionPDFContentType(String descriptionPDFContentType) {
        this.descriptionPDFContentType = descriptionPDFContentType;
    }

    public String getDescriptionTexte() {
        return descriptionTexte;
    }

    public Projet descriptionTexte(String descriptionTexte) {
        this.descriptionTexte = descriptionTexte;
        return this;
    }

    public void setDescriptionTexte(String descriptionTexte) {
        this.descriptionTexte = descriptionTexte;
    }

    public Integer getNbEtudiant() {
        return nbEtudiant;
    }

    public Projet nbEtudiant(Integer nbEtudiant) {
        this.nbEtudiant = nbEtudiant;
        return this;
    }

    public void setNbEtudiant(Integer nbEtudiant) {
        this.nbEtudiant = nbEtudiant;
    }

    public Boolean isAutomatique() {
        return automatique;
    }

    public Projet automatique(Boolean automatique) {
        this.automatique = automatique;
        return this;
    }

    public void setAutomatique(Boolean automatique) {
        this.automatique = automatique;
    }

    public Boolean isArchive() {
        return archive;
    }

    public Projet archive(Boolean archive) {
        this.archive = archive;
        return this;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Projet groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public User getUserExtra() {
        return userExtra;
    }

    public Projet userExtra(User user) {
        this.userExtra = user;
        return this;
    }

    public void setUserExtra(User user) {
        this.userExtra = user;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Projet documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Projet addDocument(Document document) {
        this.documents.add(document);
        document.setProjet(this);
        return this;
    }

    public Projet removeDocument(Document document) {
        this.documents.remove(document);
        document.setProjet(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", descriptionPDF='" + getDescriptionPDF() + "'" +
            ", descriptionPDFContentType='" + getDescriptionPDFContentType() + "'" +
            ", descriptionTexte='" + getDescriptionTexte() + "'" +
            ", nbEtudiant=" + getNbEtudiant() +
            ", automatique='" + isAutomatique() + "'" +
            ", archive='" + isArchive() + "'" +
            "}";
    }
}
