package spring.async.uploader.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Calendar;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.async.uploader.service.domain.FileMeta;
import spring.async.uploader.service.domain.ResourceStatus;
import spring.async.uploader.servie.api.UploadService;
import spring.async.uploader.utils.C4CFileUtils;

@RestController
public class UploadController {

	@Autowired
	private UploadService uploadService;
	@RequestMapping(value = "/api/upload/{id}/", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("id") final String id){
		final FileMeta meta = this.uploadService.get(id);
		if(meta==null) {
			return  ResponseEntity.notFound().build();
		}else if(meta.isUploaded()) {
			return ResponseEntity.ok(meta.add(linkTo(methodOn(UploadController.class).get(id)).withSelfRel()));
		}else {
			final ResourceStatus status = new ResourceStatus();
			status.setStatus("In Progress");
			return ResponseEntity.ok(status.add(linkTo(methodOn(UploadController.class).get(id)).withRel("delete")));
		}
	}



	@RequestMapping(value = "/api/upload/", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestParam final MultipartFile file) throws IOException {
		if(file != null && !file.isEmpty()) {
			final FileMeta meta = new FileMeta();
			meta.setFileName(file.getOriginalFilename());
			meta.setLength(file.getSize());

			try {
				meta.setType( C4CFileUtils.getMediaType(file));
			} catch (final IOException e) {
				//No able to parse
				//Do exception handling
			}

			meta.setUploadedAt(Calendar.getInstance());
			final byte buffer[] = new byte[(int)file.getSize()];
			IOUtils.readFully(file.getInputStream(), buffer);

			this.uploadService.process(buffer, meta);

			return ResponseEntity.accepted().header("location", "/api/upload/"+meta.getId()+"/").build();
		}else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
