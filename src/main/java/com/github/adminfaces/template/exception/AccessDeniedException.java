package com.github.adminfaces.template.exception;

import javax.ejb.ApplicationException;
import java.io.Serializable;

/**
 * Marker exception to send user to 403.xhtml, see web-fragment.xml
 */
@ApplicationException(rollback = true)
public class AccessDeniedException extends RuntimeException implements Serializable {

    public AccessDeniedException() {
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message exception message
     */
    public AccessDeniedException(String message) {
        super(message);
    }

}
