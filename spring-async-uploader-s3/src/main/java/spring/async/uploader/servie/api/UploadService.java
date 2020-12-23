package spring.async.uploader.servie.api;

import spring.async.uploader.service.domain.FileMeta;

public interface UploadService {

	FileMeta get(String id);

	void  process(final byte[] bs, final FileMeta meta);

	void upload(byte[] bs, FileMeta meta);

}
