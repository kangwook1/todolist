package com.example.todolist.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

// 모든 컨트롤러가 실행하는 동안 발생할 수 있는 예외를 일괄적으로 처리하는 어노테이션
// ResponseEntityExceptionHandler을 상속해 기존 예외처리를 override해야 일관성 있는 응답 가능(아니면 스프링 자체적인 에러 객체 반환)
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    //설정 해둔 exception들을 처리할 수 있다.

    /*
        MethodArgumentNotValidException는 유효성 검사에서 실패하면 나타나는 예외로 bindingReult에 에러를 담는다.
        bindingResult는 유효성검사에 실패해 예외가 발생하면 담아주는 객체이다.
        bindingResult가 없으면 400오류가 발생해 컨트롤러를 호출하지않고 오류페이지로 이동한다.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult=ex.getBindingResult();
        List<String> errors=new ArrayList<>();
        for(FieldError error: bindingResult.getFieldErrors()){
            errors.add(error.getField() + ":" + error.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(errors);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e){
       ErrorResponse errorResponse=ErrorResponse.builder()
               .status(e.getErrorCode().getStatus().toString())
               .message(e.getErrorCode().getMessage())
               .build();
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(errorResponse);
    }

}
