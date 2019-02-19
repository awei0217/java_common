package exception;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/3/9
 * @描述 异常等级枚举类
 * @联系邮箱
 */
public enum ExceptionLevelEnum {

	//  数字越大，代表等级越高
	NORMAL_ONE_LEVEL(1,"正常"), // 此类等级的异常属于正常情况

	WARING_TWO_LEVEL(2,"警告"), //警告异常，此类等级异常开发人员需要关注,但不影响系统，业务，正常运行

	GRAVE_THREE_LEVEL(3,"严重"), // 严重异常，此类等级的异常会导致业务和系统运行问题，开发人员根据业务重要程度需要尽快处理

	ERROR_THREE_LEVEL(4,"错误"); // 错误异常，此类等级的异常，开发人员必须马上处理

	/**
	 * 异常等级
     */
	private Integer exceptionLevel;
	/**
	 * 异常等级对应的含义描述
     */
	private String exceptionLevelDesc;

	ExceptionLevelEnum(Integer exceptionLevel,String exceptionLevelDesc){
		this.exceptionLevel = exceptionLevel;
		this.exceptionLevelDesc = exceptionLevelDesc;
	}

	public Integer getExceptionLevel() {
		return exceptionLevel;
	}

	public void setExceptionLevel(Integer exceptionLevel) {
		this.exceptionLevel = exceptionLevel;
	}

	public String getExceptionLevelDesc() {
		return exceptionLevelDesc;
	}

	public void setExceptionLevelDesc(String exceptionLevelDesc) {
		this.exceptionLevelDesc = exceptionLevelDesc;
	}
}
