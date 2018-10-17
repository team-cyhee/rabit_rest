package com.cyhee.rabit.web.file;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.service.file.FileService;


@Controller
public class FileController {
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="/rest/files/{id}", method=RequestMethod.GET)
	@ResponseBody
	ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id, HttpServletRequest request) throws IOException {
		FileInfo file = fileService.getFile(id);
		return fileResponse(file, request);
	}

	/**
	 * 브라우저 구분 얻기.
	 *
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}


	/**
	 * Disposition 지정하기.
	 *
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	private ResponseEntity<InputStreamResource> fileResponse(FileInfo file, HttpServletRequest request) throws IOException {
		String browser = getBrowser(request);
		String filename = file.getOrgName();

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			//throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		Path filePath = Paths.get(file.getPath(), file.getName());

		return ResponseEntity.ok()
				.contentLength(Files.size(filePath))
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", dispositionPrefix + encodedFilename)
				.body(new InputStreamResource(Files.newInputStream(filePath, StandardOpenOption.READ)));
	}
}
