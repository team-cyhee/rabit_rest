package com.cyhee.rabit.web.file;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FileController {
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="/rest/files/{id}", method=RequestMethod.GET)
	@ResponseBody
	ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws IOException {
		FileInfo file = fileService.getFile(id);
		Path filePath = Paths.get(file.getPath(), file.getName());
		return ResponseEntity.ok()
				.contentLength(Files.size(filePath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(file.getOrgName())))
				.header("Content-Disposition", "attachment; filename=" + file.getOrgName())
				.body(new InputStreamResource(Files.newInputStream(filePath, StandardOpenOption.READ)));
	}
}
