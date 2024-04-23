package tempData;

public class LoginGrnNumberData {
	private long grnNumber;
	
	public static long[] loginGrnNumberData = new long[1];


	public long getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(long grnNumber) {
		loginGrnNumberData[0] = grnNumber;
		this.grnNumber = grnNumber;
	}
}
