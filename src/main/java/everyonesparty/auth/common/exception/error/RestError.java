package everyonesparty.auth.common.exception.error;

import everyonesparty.auth.common.response.ResponseError;
import org.springframework.http.HttpStatus;

public interface RestError {

    public ResponseError toResponseError();

    public HttpStatus getHttpStatus();

    public String getErrorMsg();

}
