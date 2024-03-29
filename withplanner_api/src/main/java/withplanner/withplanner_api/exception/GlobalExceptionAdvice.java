package withplanner.withplanner_api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionAdvice {

    private final MessageSource ms;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse MemberNotFoundException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        String[] codes = bindingResult.getAllErrors().get(0).getCodes();
        for (String code : codes) {
            System.out.println("code = " + code);
        }

        String code = codes[1];
        return new ErrorResponse(ms.getMessage(code,null,null));
    }

    @Getter
    public class ErrorResponse {
        String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        log.error("Handle CommonException: {}", e.getMessage());
        return new BaseResponse<>(e.getStatus());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> allHandleException(Exception e) {
        log.error("Handle All Exception: {}", e.getMessage());
        return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
    }
}