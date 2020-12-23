package spring.async.uploader.service.domain;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.google.common.net.MediaType;

public class FileMeta extends RepresentationModel<FileMeta>{
	private String fileName;
	private long length;
	private MediaType type;
	private Calendar uploadedAt;
	private  boolean uploaded = false;
	private final String id = UUID.randomUUID().toString();
	private String s3Path;
	private String cdnUrl;
	public String getCdnUrl() {
		return this.cdnUrl;
	}
	public String getFileName() {
		return this.fileName;
	}

	public String getId() {
		return this.id;
	}

	public long getLength() {
		return this.length;
	}
	public String getS3Path() {
		return this.s3Path;
	}
	public MediaType getType() {
		return this.type;
	}
	public Calendar getUploadedAt() {
		return this.uploadedAt;
	}
	public boolean isUploaded() {
		return this.uploaded;
	}
	public void setCdnUrl(final String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}
	public void setLength(final long length) {
		this.length = length;
	}
	public void setS3Path(final String s3Path) {
		this.s3Path = s3Path;
	}
	public void setType(final MediaType type) {
		this.type = type;
	}
	public void setUploaded(final boolean uploaded) {
		this.uploaded = uploaded;
	}
	public void setUploadedAt(final Calendar uploadedAt) {
		this.uploadedAt = uploadedAt;
	}


}
