package com.blackbook.webconsole.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blackbook.webconsole.services.EnterpriseService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
public class QRController {
	
	private static final String PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
	private static final String PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";
	private static final String SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
	private static final String COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
	private static final String ACCESS_TOKEN = "com.google.android.apps.work.clouddpc.EXTRA_ENROLLMENT_TOKEN";
	private static final Logger LOG = LoggerFactory.getLogger(QRController.class);
	@GetMapping(value = "/getqrcode", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getQrCode() {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		try {
			JSONObject json = new JSONObject();
			json.put(COMPONENT_NAME, "com.google.android.apps.work.clouddpc/.receivers.CloudDeviceAdminReceiver")
			.put(SIGNATURE_CHECKSUM, "I5YvS0O5hXY46mb01BlRjq4oJJGs2kuUcHvVkAPEXlg")
			.put(PACKAGE_DOWNLOAD_LOCATION, "https://play.google.com/managed/downloadManagingApp?identifier=setup")
			.put(PROVISIONING_ADMIN_EXTRAS_BUNDLE, new JSONObject().put(ACCESS_TOKEN, EnterpriseService.ENROLLMENT_TOKEN));
			LOG.info(json.toString());
			bitMatrix = qrCodeWriter.encode(json.toString(), BarcodeFormat.QR_CODE, 300, 300);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "jpeg", os);
			return os.toByteArray();
		} catch (WriterException | IOException e) {
			LOG.error(e.getMessage());
			return new byte[50];
		}  
	}
	
}
