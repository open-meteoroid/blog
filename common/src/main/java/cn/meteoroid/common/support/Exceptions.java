package cn.meteoroid.common.support;

/**
 * 自定义异常
 *
 * @author Kelvin Song
 */
public class Exceptions {

    /**
     * UNPROCESSABLE_ENTITY(422, "Unprocessable Entity")
     */
    public static class UnProcessableException extends RuntimeException {
        public UnProcessableException(String message) {
            super(message);
        }

        public UnProcessableException() {
            super();
        }
    }

    /**
     * NOT_ACCEPTABLE(406, "Not Acceptable")
     */
    public static class NotAcceptableException extends RuntimeException {
        public NotAcceptableException(String message) {
            super(message);
        }

        public NotAcceptableException() {
            super();
        }
    }

    /**
     * USER_NOT_FOUND(用户不存在)
     */
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }

        public UserNotFoundException() {
            super();
        }
    }

    /**
     * Poi操作异常
     */
    public static class PoiException extends RuntimeException {
        public PoiException(String message) {
            super(message);
        }

        public PoiException() {
            super();
        }
    }
}
