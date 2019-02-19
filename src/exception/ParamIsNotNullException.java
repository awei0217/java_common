package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 检验必填参数为空时抛出此类异常,不支持传入自定义异常编码，此异常编码系统已经定义好
 * @联系邮箱
 */
public class ParamIsNotNullException extends BaseException {


	public ParamIsNotNullException() {
		super(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.PARAM_IS_NOT_NULL_EXCEPTION.getExceptionCode(),
				ExceptionNumberAndDesEnum.PARAM_IS_NOT_NULL_EXCEPTION.getExceptionMsg() );
	}

	public ParamIsNotNullException(Throwable e) {
		this(null,e);
	}

	public ParamIsNotNullException( String exceptionMsg) {
		this(null,exceptionMsg);
	}

	public ParamIsNotNullException( Integer level, String exceptionMsg) {
		this(level,exceptionMsg,null);
	}

	public ParamIsNotNullException(String exceptionMsg, Throwable e) {
		this(null,exceptionMsg, e);
	}

	public ParamIsNotNullException( Integer level, String exceptionMsg, Throwable e) {
		super(level,
				ExceptionNumberAndDesEnum.PARAM_IS_NOT_NULL_EXCEPTION.getExceptionCode(),
				exceptionMsg,
				e);
	}

}
