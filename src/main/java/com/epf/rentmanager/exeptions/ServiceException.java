package com.epf.rentmanager.exeptions;

public class ServiceException extends Exception{
        public ServiceException(String errorMessage) {
            super(errorMessage);
        }
}
