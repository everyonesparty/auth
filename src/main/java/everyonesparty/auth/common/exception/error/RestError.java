package everyonesparty.auth.common.exception.error;

import everyonesparty.auth.common.response.ResponseError;
import org.springframework.http.HttpStatus;

public interface RestError {

    ResponseError toResponseError();

    HttpStatus getHttpStatus();

    String getErrorMsg();

}
