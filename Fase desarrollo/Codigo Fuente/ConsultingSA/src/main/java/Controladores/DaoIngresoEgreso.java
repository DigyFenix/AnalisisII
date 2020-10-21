package Controladores;

import Interfaces.DaoEntity;
import Modelos.IngresoEgreso;

/**
 *
 * @author echacon
 */
public class DaoIngresoEgreso extends DaoEntity<IngresoEgreso> {

    public DaoIngresoEgreso() {
        super(new IngresoEgreso());
    } 
}
