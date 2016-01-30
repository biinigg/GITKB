package com.dsc.dci.jweb.pub;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dci.jweb.PublicLib.DCIString;

@Controller
@RequestMapping("/ImageLoader.dsc")
public class ImageLoader {
	@RequestMapping(method = RequestMethod.GET)
	public void getMethod(HttpServletRequest request, HttpServletResponse response) {
		String iconid = request.getParameter("iconid");
		String imgpath = request.getParameter("imgpath");

		ServletOutputStream output = null;
		BufferedInputStream bufferedInput = null;
		FileInputStream fileInput = null;

		if (DCIString.isNullOrEmpty(iconid)) {
			imgpath = DCIString.Base64Decode(imgpath);
		} else {
			imgpath = new APPubMethods().getIconPath(DCIString.Base64Decode(iconid));
		}

		if (!DCIString.isNullOrEmpty(imgpath)) {
			File file = new File(imgpath);

			if (file.exists()) {
				int startIndex = file.getName().lastIndexOf(46) + 1;
				int endIndex = file.getName().length();
				String extension = file.getName().substring(startIndex, endIndex);
				String contentType = null;

				try {
					byte[] buffer = new byte[1024 * 1024 * 10];
					fileInput = new FileInputStream(imgpath);
					bufferedInput = new BufferedInputStream(fileInput);

					if (extension.toLowerCase().indexOf("svg") == -1) {
						contentType = "image/" + extension;
					} else {
						contentType = "image/svg+xml; charset=UTF-8;";
					}

					response.setContentType(contentType);
					response.setHeader("Content-Disposition", " inline; filename=" + iconid + "." + extension);

					output = response.getOutputStream();

					int data = 0;

					while ((data = bufferedInput.read(buffer)) != -1) {
						output.write(buffer, 0, data);
					}

					output.flush();
					bufferedInput.close();
					fileInput.close();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedInput != null) {
							bufferedInput.close();
						}
						bufferedInput = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if (fileInput != null) {
							fileInput.close();
						}
						fileInput = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
