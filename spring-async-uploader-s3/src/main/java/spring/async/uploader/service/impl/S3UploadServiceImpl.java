package spring.async.uploader.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import spring.async.uploader.service.domain.FileMeta;
import spring.async.uploader.servie.api.UploadService;

@Service
public class S3UploadServiceImpl implements UploadService{

	@Autowired
	private AmazonS3 amazonS3;
	private final String bucket= "test.bimbim.in";

	//Change this with db
	private final ConcurrentMap<String, FileMeta> uploadTrackMap = new ConcurrentHashMap<String, FileMeta>();

	@Override
	public FileMeta get(final String id) {
		return this.uploadTrackMap.get(id);
	}

	@Async("taskExecutor")
	@Override
	public void  process(final byte[] bs, final FileMeta meta){
		this.upload(bs, meta);

	}

	@Override
	public void upload(final byte[] bs, final FileMeta meta) {
		this.uploadTrackMap.put(meta.getId(), meta);
		final String fileName = meta.getId()+meta.getFileName();
		final InputStream targetStream = new ByteArrayInputStream(bs);
		final PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucket,fileName, targetStream, new ObjectMetadata());
		this.amazonS3.putObject(putObjectRequest);
		final String url = this.amazonS3.getUrl(this.bucket, fileName).toString();
		meta.setS3Path(url);
		meta.setUploaded(true);
	}

}
