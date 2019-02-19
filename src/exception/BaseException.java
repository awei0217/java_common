package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 基类异常，继承自RuntimeExption ,系统的业务异常，系统异常等全部集成这个异常类
 * @联系邮箱
 */
public class BaseException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 异常等级
     */
	public Integer level ;
	/**
	 * 异常编号
     */
	public Integer exceptionCode;

	/**
	 * 异常信息描述
     */
	public String exceptionMsg;
	/**
	 * 异常堆栈信息
     */
	public Throwable e;

	/**
	 *@描述 构造一个基类异常
	 *@参数  [level, exceptionCode, exceptionMsg, e] 异常等级, 异常编码，异常描述，堆栈信息
	 *@返回值
	 *@创建人
	 *@创建时间
	 *@邮箱
	 */
	public BaseException(Integer  level,Integer exceptionCode ,String exceptionMsg , Throwable e){
		super("level:"+level+",exceptionCode:" +exceptionCode + ",exceptionMsg:" +exceptionMsg,e);
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
		this.level = level;
		this.e = e;


	}
	/**
	 *@描述 构造一个基类异常
	 *@参数  [exceptionCode, exceptionMsg] 异常编码，异常描述
	 *@返回值
	 *@创建人
	 *@创建时间
	 *@邮箱
	 */
	public BaseException(Integer exceptionCode ,String exceptionMsg ){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),exceptionCode,exceptionMsg,null);

	}
	/**
	 *@描述 构造一个基类异常
	 *@参数  [level, exceptionCode, exceptionMsg] 异常等级，异常编码，异常描述
	 *@返回值
	 *@创建人
	 *@创建时间
	 *@邮箱
	 */
	public BaseException(Integer level,Integer exceptionCode ,String exceptionMsg ){
		this(level,exceptionCode,exceptionMsg,null);


	}
	/**
	 *@描述 构造一个基类异常
	 *@参数  [exceptionCode, exceptionMsg, e] 异常编码，异常描述，堆栈
	 *@返回值
	 *@创建人
	 *@创建时间
	 *@邮箱
	 */
	public BaseException(Integer exceptionCode ,String exceptionMsg ,Throwable e){
		this(ExceptionLevelEnum.GRAVE_THREE_LEVEL.getExceptionLevel(),exceptionCode,exceptionMsg,e);

	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
