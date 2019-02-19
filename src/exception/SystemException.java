package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 系统问题时抛出此类异常
 * @联系邮箱
 */
public class SystemException extends BaseException {

	public SystemException(){
		super(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(), ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionCode(), ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionMsg());
	}

	public SystemException(Throwable e){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionCode(),
				ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionMsg(),
				e);
	}

	public SystemException(Integer exceptionCode , String exceptionMsg){
		this(null,exceptionCode,exceptionMsg);
	}

	public SystemException(Integer level,Integer exceptionCode , String exceptionMsg){
		this(level,exceptionCode,exceptionMsg,null);
	}

	public SystemException(Integer level ,String exceptionMsg, Throwable e){
		this(level, ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionCode(),exceptionMsg,e);
	}

	public SystemException(String exceptionMsg,Throwable e){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),
				ExceptionNumberAndDesEnum.SYSTEM_EXCEPTION.getExceptionCode(),
				exceptionMsg,
				e);
	}

	public SystemException( Integer level,Integer exceptionCode , String exceptionMsg, Throwable e){
		super(level,exceptionCode,exceptionMsg,e);
	}


}
