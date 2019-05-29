package cn.meteoroid.common.extend;

import cn.meteoroid.common.support.Exceptions;
import cn.meteoroid.common.support.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 全局异常处理机制
 *
 * @author Kelvin Song
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {

    @Autowired
    private HttpServletRequest request;

    private Result<Object> messageBuilder(List<String> message) {
        String[] messages = message.toArray(new String[message.size()]);
        Result<Object> result = new Result<>(HttpStatus.BAD_REQUEST.value(), messages);

        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), message.toString());
        return result;
    }

    /**
     * 400 - 参数校验 @Valid
     *
     * @param e MethodArgumentNotValidException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> argumentNotValid(MethodArgumentNotValidException e) {
        List<String> message = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.add(error.getDefaultMessage());
        }
        return messageBuilder(message);
    }

    /**
     * 400 - 参数校验 @Validated
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> constraintViolation(ConstraintViolationException e) {
        List<String> message = new ArrayList<>();
        Set<ConstraintViolation<?>> violationSet = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violationSet) {
            message.add(violation.getMessage());
        }
        return messageBuilder(message);
    }

    /**
     * 400 - 参数校验 delete 数据不存在
     *
     * @param e EmptyResultDataAccessException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Result<Object> emptyResultDataAccess(EmptyResultDataAccessException e) {
        Result<Object> result = new Result<>(HttpStatus.BAD_REQUEST.value(), "不存在的请求数据");
        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    /**
     * 400 - Optional 数据不存在
     *
     * @param e NoSuchElementException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public Result<Object> noSuchElement(NoSuchElementException e) {
        Result<Object> result = new Result<>(HttpStatus.BAD_REQUEST.value(), "不存在的请求数据");
        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    /**
     * 405 - 请求方法不支持 GET/POST/PUT/PATCH/DELETE...
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return Result
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> methodNotSupported(HttpRequestMethodNotSupportedException e) {
        Result<Object> result = new Result<>(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持的请求方法");

        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    /**
     * 406 - 自定义异常 - 不接受/无效的
     *
     * @param e NotAcceptableException
     * @return Result
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(Exceptions.NotAcceptableException.class)
    public Result<Object> notAcceptable(Exceptions.NotAcceptableException e) {
        Result<Object> result = new Result<>(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());

        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    /**
     * 415 - 请求的媒体类型不支持
     *
     * @param e HttpMediaTypeNotSupportedException
     * @return Result
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Object> mediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        Result<Object> result = new Result<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "不支持的媒体类型");

        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    /**
     * 422 - 自定义异常 - 数据校验
     *
     * @param e UnProcessableException
     * @return Result
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(Exceptions.UnProcessableException.class)
    public Result<Object> unProcessable(Exceptions.UnProcessableException e) {
        Result<Object> result = new Result<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());

        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
        return result;
    }

    //    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public Result<Object> accessDenied(Exception e) {
//        Result<Object> result = new Result<>();
//        result.setCode(HttpStatus.FORBIDDEN.value());
//        result.setMessage(e.getMessage());
//
//        logger.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
//        return result;
//    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public Result<Object> internalServer(Exception e) {
//        Result<Object> result = new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器发生异常，请联系管理员");
//
//        log.error(" {} - {} - {} - {}", request.getMethod(), request.getRequestURL(), result.getCode(), e.getMessage());
//        return result;
//    }


}
