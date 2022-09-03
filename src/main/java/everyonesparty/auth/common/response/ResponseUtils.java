package everyonesparty.auth.common.response;

import everyonesparty.auth.common.exception.error.RestError;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity ok(){
        return ResponseEntity.ok().build();
    }

    /***
     * data만 들어오면 성공 응답
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity out(T data){
        return out(convertSuccessResponse(data));
    }


    /***
     * RestError 만 들어오면 실패 응답
     * @param restError
     * @return
     */
    public static ResponseEntity out(RestError restError){
        return ResponseEntity.status(restError.getHttpStatus()).body(restError.toResponseError());
    }

    /***
     * Response 가 들어오면 객체를 까서 성공 & 실패를 판단 후 적절한 응답
     * @param response
     * @return
     */
    public static ResponseEntity out(Response response){
        if(response.hasError()){
            RestError restError = response.getError();
            return ResponseEntity.status(restError.getHttpStatus()).body(restError.toResponseError());
        }
        else{
            return ResponseEntity.ok(response.getData());
        }
    }

    /***
     * data -> 성공 형태의 Response 로 변환
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> convertSuccessResponse(T data){
        return new Response<T>(data,null);
    }
}
