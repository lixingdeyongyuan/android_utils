
public class DataTrans {

	// 转16进制String
	public static String byteArrat2HexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static byte[] hexString2ByteArray(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i++) {
			data[i / 2] = (byte) (Character.digit(hexString.charAt(i), 16) << 4
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}
}
