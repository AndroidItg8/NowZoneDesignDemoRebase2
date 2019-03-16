package itg8.com.nowzonedesigndemo.exception;

/**
 * Created by itg_Android on 3/1/2017.
 */

public class StringEmptyException extends Exception {
        private static final long serialVersionUID=1300;
    public StringEmptyException(String msg){
        super(msg);
    }
}
