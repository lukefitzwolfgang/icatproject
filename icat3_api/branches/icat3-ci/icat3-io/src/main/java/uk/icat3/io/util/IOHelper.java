package uk.icat3.io.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class IOHelper {

	/* Block size that we want to read in one time. */
	private static final int READ_BLOCK = 8192;

	/*
	 * Read all from stream, using nio.
	 * 
	 * @param is source stream.
	 * 
	 * @return result byte array that read from source
	 * 
	 * @throws IOException by {@code Channel.read()}
	 */
	public static byte[] readToEnd(InputStream is) throws IOException {
		// create channel for input stream
		ReadableByteChannel bc = Channels.newChannel(is);
		ByteBuffer bb = ByteBuffer.allocate(READ_BLOCK);

		while (bc.read(bb) != -1) {
			bb = resizeBuffer(bb); // get new buffer for read
		}
		byte[] result = new byte[bb.position()];
		bb.position(0);
		bb.get(result);

		return result;
	}

	private static ByteBuffer resizeBuffer(ByteBuffer in) {
		ByteBuffer result = in;
		if (in.remaining() < READ_BLOCK) {
			// create new buffer
			result = ByteBuffer.allocate(in.capacity() * 2);
			// set limit to current position in buffer and set position to zero.
			in.flip();
			// put original buffer to new buffer
			result.put(in);
		}

		return result;
	}
}