package tempData;

public class AdmissionData {
	private String studentFirstName;
	private String fatherFirstName;
	private String motherFirstName;
	private String lastName;
	private String parentMobileNumber;
	private String address;
	
	public static String[] admissionData = new String[6];
	
	
	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		admissionData[0] = studentFirstName;
		this.studentFirstName = studentFirstName;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		admissionData[1] = fatherFirstName;
		this.fatherFirstName = fatherFirstName;
	}

	public String getMotherFirstName() {
		return motherFirstName;
	}

	public void setMotherFirstName(String motherFirstName) {
		admissionData[2] = motherFirstName;
		this.motherFirstName = motherFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		admissionData[3] = lastName;
		this.lastName = lastName;
	}

	public String getParentMobileNumber() {
		return parentMobileNumber;
	}

	public void setParentMobileNumber(String parentMobileNumber) {
		admissionData[4] = parentMobileNumber;
		this.parentMobileNumber = parentMobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		admissionData[5] = address;
		this.address = address;
	}
}
