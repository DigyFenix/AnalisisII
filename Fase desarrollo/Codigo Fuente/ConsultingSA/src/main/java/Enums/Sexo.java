/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author echacon
 */
public enum Sexo {
    MASCULINO("M"), FEMENINO("F");
    private final String id;

    Sexo(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

}
