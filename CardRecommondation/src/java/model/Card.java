/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Zhirun Tian
 */
public class Card {

    private String Name;
    private String Network;
    private String Issuer;
    private double bouns;
    private double difficulty;
    private String path;

    public Card(String Name, String Network, String Issuer, double bouns, double difficulty, String path) {
        this.Name = Name;
        this.Network = Network;
        this.Issuer = Issuer;
        this.bouns = bouns;
        this.difficulty = difficulty;
        this.path = path;
    }

    public Card(String Name, String Network, String Issuer, double bouns, double difficulty) {
        this.Name = Name;
        this.Network = Network;
        this.Issuer = Issuer;
        this.bouns = bouns;
        this.difficulty = difficulty;
        this.path = "a fake path";
    }

    public Card() {
        this.Name = "null";
        this.Network = "null";
        this.Issuer = "null";
        this.bouns = 0;
        this.difficulty = 0;
        this.path = "a fake path";
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String Network) {
        this.Network = Network;
    }

    public String getIssuer() {
        return Issuer;
    }

    public void setIssuer(String Issuer) {
        this.Issuer = Issuer;
    }

    public double getBouns() {
        return bouns;
    }

    public void setBouns(double bouns) {
        this.bouns = bouns;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

}
