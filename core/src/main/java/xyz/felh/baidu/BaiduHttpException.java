package xyz.felh.baidu;

/**
 * Runtime Exception for HTTP
 */
public class BaiduHttpException extends RuntimeException {

    public BaiduHttpException(BaiduError error, Exception parent) {
        super(error.getErrorMsg(), parent);
    }

}
