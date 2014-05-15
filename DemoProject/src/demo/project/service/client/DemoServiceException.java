package demo.project.service.client;

import android.content.DialogInterface;

public class DemoServiceException extends Throwable {


	private static final long serialVersionUID = 1L;
	private Throwable cause;
	private String title="";
	private String customErrorMessage="";
	private DialogInterface.OnClickListener alertDialogOnClicklistener;
	private DialogInterface.OnKeyListener alertDialogKeyListener;

	public DialogInterface.OnKeyListener getAlertDialogKeyListener() {
		return alertDialogKeyListener;
	}

	public void setAlertDialogKeyListener(
			DialogInterface.OnKeyListener alertDialogKeyListener) {
		this.alertDialogKeyListener = alertDialogKeyListener;
	}
	
	public DemoServiceException(String customErrorMessage) {
		this.customErrorMessage = customErrorMessage;
	}

	public String getCustomErrorMessage() {
		return customErrorMessage;
	}

	public void setCustomErrorMessage(String customErrorMessage) {
		this.customErrorMessage = customErrorMessage;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DialogInterface.OnClickListener getAlertDialogOnClicklistener() {
		return alertDialogOnClicklistener;
	}

	public void setAlertDialogOnClicklistener(DialogInterface.OnClickListener alertDialogOnClicklistener) {
		this.alertDialogOnClicklistener = alertDialogOnClicklistener;
	}
}