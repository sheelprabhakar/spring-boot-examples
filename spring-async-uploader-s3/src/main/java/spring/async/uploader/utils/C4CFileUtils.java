package spring.async.uploader.utils;

import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.MediaType;

public class C4CFileUtils {
	private static final Tika tika = new Tika();
	public static String getFileType(final MultipartFile file) throws IOException {
		return  tika.detect(file.getInputStream());

	}

	public static MediaType getMediaType(final MultipartFile file) throws IOException {
		return  MediaType.parse(getFileType(file));

	}
}
