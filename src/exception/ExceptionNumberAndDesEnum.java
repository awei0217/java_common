package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 异常编码和描述枚举类
 * @联系邮箱
 */
public enum ExceptionNumberAndDesEnum {


	//业务异常从30000 开始

	BIZ_PROCESS_EXCEPTION(30010,"业务异常"),




	//系统异常从60000 开始
	SYSTEM_EXCEPTION(60010,"系统异常"),




	//其它异常从10000 开始
	PARAM_IS_NOT_NULL_EXCEPTION(10010,"参数不能为空异常"),
	PARAM_INCORRECT_EXCEPTION(10020,"参数不正确异常");

	/**
	 * 异常码
	 */
	private Integer exceptionCode;

	/**
	 * 异常描述
	 */
	private String exceptionMsg;


	ExceptionNumberAndDesEnum(Integer exceptionCode , String exceptionMsg){
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
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
