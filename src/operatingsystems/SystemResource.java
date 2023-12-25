package operatingsystems;

import java.nio.ByteBuffer;

public class SystemResource {
	static int printerCount = 2;
	static int scannerCount = 1;
	static int modemCount = 1;
	static int cdCount = 2;
	
	private ByteBuffer realtimeBuffer;
	private ByteBuffer userBuffer;
	
	public SystemResource() {
		int megabyte64 = 64;
		int megabyte960 = 960;
		long realtimebytes = megabyte64 * 1024 * 1024;
		long userbytes = megabyte960 * 1024 * 1024;
		
		realtimeBuffer = ByteBuffer.allocateDirect((int) realtimebytes);
		userBuffer = ByteBuffer.allocateDirect((int) userbytes);
	}
}
