/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dataAccess.DataUserAccess;

/**
 *
 * @author Timothy
 */
public final class DataUserAccessFactory {

    private static DataUserAccess dataUserAccess = null;

    private DataUserAccessFactory() {
        // prevent instance initilisation of this util class
    }

    public static DataUserAccess getDataUserAccessInstance() {
        if (dataUserAccess == null) {
            dataUserAccess = new DataUserAccess();
        }
        return dataUserAccess;
    }
}
