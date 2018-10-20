package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 参数校验不正确时抛出此类异常,不支持传入自定义异常编码，此异常编码系统已经定义好
 * @联系邮箱 sunpengwei@jd.com
 */
public class ParamErrorException extends  BaseException{

    public ParamErrorException() {
        super(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
                ExceptionNumberAndDesEnum.PARAM_INCORRECT_EXCEPTION.getExceptionCode(),
                ExceptionNumberAndDesEnum.PARAM_INCORRECT_EXCEPTION.getExceptionMsg() );
    }

    public ParamErrorException( String exceptionMsg) {
        this(null, exceptionMsg);
    }

    public ParamErrorException(Throwable e) {
        this(null,null,e);
    }

    public ParamErrorException( Integer level, String exceptionMsg) {
        this(level,exceptionMsg,null);
    }
    public ParamErrorException( Integer level, Throwable e) {
        this(null,null,e);
    }

    public ParamErrorException(String exceptionMsg, Throwable e) {
       this(null,exceptionMsg,e);
    }

    public ParamErrorException( Integer level,String exceptionMsg, Throwable e) {
        super(level,
                ExceptionNumberAndDesEnum.PARAM_INCORRECT_EXCEPTION.getExceptionCode(),
                exceptionMsg,
                e);
    }

}
