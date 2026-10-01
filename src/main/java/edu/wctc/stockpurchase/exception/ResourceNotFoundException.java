package edu.wctc.stockpurchase.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, int resourceId) {
        super(resourceName + " with ID " + resourceId + " not found.");
    }
}