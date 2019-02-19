package exception;


/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 系业务问题时抛出此类异常 ，集成此类的异常请在为类命名时遵守 BizException+业务含义,  方便大家知晓为业务异常
 * @联系邮箱
 */
public class BizException extends  BaseException {

	public BizException(){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionCode(),
				ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionMsg());
	}

	public BizException(Throwable e){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(), ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionCode(), ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionMsg(),e);
	}

	public BizException(Integer exceptionCode , String exceptionMsg){
		this(null,exceptionCode,exceptionMsg);
	}

	public BizException(String exceptionMsg){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionCode(),
				exceptionMsg);
	}

	public BizException(Integer level,Integer exceptionCode , String exceptionMsg){
		this(level,exceptionCode,exceptionMsg,null);
	}
	public BizException(String exceptionMsg,Throwable e){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.BIZ_PROCESS_EXCEPTION.getExceptionCode(),
				exceptionMsg,
				e);
	}
	public BizException(Integer exceptionCode, String exceptionMsg, Throwable e) {
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				exceptionCode,
				exceptionMsg,
				e);
	}
	public BizException(Integer level,Integer exceptionCode , String exceptionMsg,  Throwable e) {
		super(level,
				exceptionCode,
				exceptionMsg,
				e);
	}

}
