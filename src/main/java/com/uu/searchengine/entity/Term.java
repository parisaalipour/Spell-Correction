package com.uu.searchengine.entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Term")
@Table(name = "term")
public class Term {


    public Term() {
    }

    public Term(String termString) {
        this.termString = termString;
    }

    public Term(String termString, Set<Bigram> bigrams) {
        this.termString = termString;
        this.bigrams = bigrams;
    }

    @Id
    @Column
    String termString;

    int frequency;

    public Set<Bigram> getBigrams() {
        return bigrams;
    }

    public Term(String termString, int frequency) {
        this.termString = termString;
        this.frequency = frequency;
    }

    public void setBigrams(Set<Bigram> bigrams) {
        this.bigrams = bigrams;
    }

    @ManyToMany(fetch = FetchType.LAZY , mappedBy = "terms", cascade = {CascadeType.ALL})
    Set<Bigram> bigrams = new HashSet<>();


    public String getTermString() {
        return termString;
    }

    public void setTermString(String termString) {
        this.termString = termString;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Term{" +
                ", termString='" + termString + '\'' +
                '}';
    }

}