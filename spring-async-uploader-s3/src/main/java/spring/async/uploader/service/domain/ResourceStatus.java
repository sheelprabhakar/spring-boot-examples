package spring.async.uploader.service.domain;

import org.springframework.hateoas.RepresentationModel;

public class ResourceStatus extends RepresentationModel<ResourceStatus> {
	private String status;

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
