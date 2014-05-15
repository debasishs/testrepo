package demo.project.util;


public interface DemoConstants {

	String DEMO_PREFERENCES = "DEMO_preferences";
	String SESSIONTTL = "sessionttl";
	String SESSIONSTARTTIME = "sessionstarttime";
	String SESSIONENDTIME = "sessionendtime";
	String EMPTY_STRING = "";


	String URL_ENCODING_TYPE = "application/x-www-form-urlencoded";
	String JSON_CONTENT_TYPE = "application/json";

	String DEMO_ENCRYPTION_KEY = "DEMO_encryption_key";
	String ERROR = "error";
	String ACCESS_TOKEN = "access_token";

	String IMAGE_RESPONSE_CONTENT_TYPE = "image/gif";

	int DISMISS_DIALOG_ID = 0;
	int NETWORK_ERROR = 4;

	String LAST_SUCCESSFUL_LOGGED_USER = "last_successful_logged_user";

	String POST = "post";
	String GET = "get";

	String DIALOG_OK = "OK";
	String DIALOG_ERROR = "Error";


	// Network timeout related constants in milliseconds.

	int TIMEOUT_CONNECTION = 10000;
	int SOCKET_TIMEOUT = 3600000;

	String NETWORKERROR = "Please check your data connection and try again.";
	String XML_RESPONSE_DUE_TO_ERROR = "ERROR_DUE_TO_WRONG_RESPONSE/REQUEST";

	// Locked user error message and Title
	String LOCKED_USER_TITLE = "Account Locked";
	String LOCKED_USER_MESSAGE = "Your account has been temporarily locked. Please contact site support";

	// password compliance

	String INVALID_PASSWORD_COMPLIANCE = "invalid_password_compliance";
	String USER_NAME_TAG = "username";
	String DEMO_ERROR = "DEMO_error";
	String SUCCESS = "SUCCESS";
	String CHANGE_PASSWORD_ERROR = "Error";
	String CHANGE_PASSWORD_COMMON_ERROR_MESSAGE = "Unable to Change Password";
	String USER_DISABLED = "user_disabled";
	String ERROR_MESSAGE_KEY = "error_msg";
	String INVALID_SITE_KEY = "invalid_site";
	String PASSWORD_VALID_MESSAGE = "pdw_valid_msg";
	String PASSWORD_REGEX_KEY = "pdw_regex";
	String USER_LOCKED = "user_locked";
	String USER_NOT_VERIFIED = "user_not_verified";

}
