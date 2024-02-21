package com.epf.rentmanager.exeptions;

public class DaoException extends Exception{
        public DaoException() {
            super("Error with the DAO");
        }
}
