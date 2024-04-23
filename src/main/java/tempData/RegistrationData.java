package tempData;

public class RegistrationData {
	
	private String mobileNumber;
	private String grnNumber;
	
	public static String[] registrationData = new String[2];
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		registrationData[0] = mobileNumber;
		this.mobileNumber = mobileNumber;
	}
	public String getGrnNumber() {
		return grnNumber;
	}
	public void setGrnNumber(String grnNumber) {
		registrationData[1] = grnNumber;
		this.grnNumber = grnNumber;
	}	
}
