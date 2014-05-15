package demo.project.vo;

import demo.project.util.DemoConstants;


public class UserVO extends BaseVO{

	private boolean isAppForFirstTime = true;
	private String username = DemoConstants.EMPTY_STRING;
	private String password = DemoConstants.EMPTY_STRING;
	private boolean isLogon = false;
	private static final UserVO INSTANCE = new UserVO();

	private String Email = DemoConstants.EMPTY_STRING;;
	private String FullName = DemoConstants.EMPTY_STRING;;
	private String Phone = DemoConstants.EMPTY_STRING;;
	private String PhoneExt = DemoConstants.EMPTY_STRING;;
	private String Site = DemoConstants.EMPTY_STRING;
	private String customerNumber  = DemoConstants.EMPTY_STRING;

	private boolean IsPasUser;
	private boolean isLockedUser =false ;

	private String loginErrorTitle;
	private String loginErrorMsg;

	private String changePasswordErrorMessage = DemoConstants.EMPTY_STRING;

	private String passwordRules = DemoConstants.EMPTY_STRING;
	private String passwordRulesRegex = DemoConstants.EMPTY_STRING;
	private String emailVerificatioErrorMessage = DemoConstants.EMPTY_STRING;


	public String getLoginErrorMsg() {
		return loginErrorMsg;
	}

	public void setLoginErrorMsg(String loginErrorMsg) {
		this.loginErrorMsg = loginErrorMsg;
	}

	public String getLoginErrorTitle() {
		return loginErrorTitle;
	}

	public void setLoginErrorTitle(String loginErrorTitle) {
		this.loginErrorTitle = loginErrorTitle;
	}

	public boolean isLockedUser() {
		return isLockedUser;
	}

	public void setLockedUser(boolean isLockedUser) {
		this.isLockedUser = isLockedUser;
	}


	public UserVO() {

	}

	public static UserVO getSingleInstance() {
		return INSTANCE;

	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getPhoneExt() {
		return PhoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		PhoneExt = phoneExt;
	}

	public String getSite() {
		return Site;
	}

	public void setSite(String site) {
		Site = site;
	}

	public boolean isIsPasUser() {
		return IsPasUser;
	}

	public void setIsPasUser(boolean isPasUser) {
		IsPasUser = isPasUser;
	}

	public boolean isAppForFirstTime() {
		return isAppForFirstTime;
	}

	public void setAppForFirstTime(boolean isAppForFirstTime) {
		this.isAppForFirstTime = isAppForFirstTime;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getUserName() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setLogOnSuccess(boolean isLogon) {
		this.isLogon = isLogon;
	}

	public boolean isLogOnSuccess() {
		return isLogon;
	}

	public String getChangePasswordErrorMessage() {
		return changePasswordErrorMessage;
	}

	public void setChangePasswordErrorMessage(String changePasswordErrorMessage) {
		this.changePasswordErrorMessage = changePasswordErrorMessage;
	}

	public String getPasswordRules() {
		return passwordRules;
	}

	public void setPasswordRules(String passwordRules) {
		this.passwordRules = passwordRules;
	}

	public String getPasswordRulesRegex() {
		return passwordRulesRegex;
	}

	public void setPasswordRulesRegex(String passwordRulesRegex) {
		this.passwordRulesRegex = passwordRulesRegex;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getEmailVerificatioErrorMessage() {
		return emailVerificatioErrorMessage;
	}

	public void setEmailVerificatioErrorMessage(String emailVerificatioErrorMessage) {
		this.emailVerificatioErrorMessage = emailVerificatioErrorMessage;
	}

}