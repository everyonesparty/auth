package everyonesparty.auth;

import everyonesparty.auth.common.exception.error.ErrorMap;
import everyonesparty.auth.common.exception.error.RestError;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.reflections.Reflections;

import java.util.EnumSet;
import java.util.Set;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		setErrorMap();
		SpringApplication.run(AuthApplication.class, args);
	}


	/***
	 * spring boot application 처음 기동 시 errorMap 세팅
	 * constraintViolation 에서 값을 넘겨주기 위해 사용
	 */
	private static void setErrorMap(){
		Reflections reflections = new Reflections("everyonesparty.auth.common.exception.error");
		Set<Class<? extends RestError>> subTypesOf = reflections.getSubTypesOf(RestError.class);
		for(Class clazz: subTypesOf){
			for(Object obj: EnumSet.allOf(clazz)){
				RestError error = (RestError) obj;
				ErrorMap.setError(error.toString(),error);
			}
		}
	}
}
