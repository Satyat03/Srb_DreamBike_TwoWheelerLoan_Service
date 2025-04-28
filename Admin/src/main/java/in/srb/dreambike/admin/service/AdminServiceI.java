package in.srb.dreambike.admin.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import in.srb.dreambike.admin.model.AdminDetails;

public interface AdminServiceI {

	public AdminDetails saveData(String adminJson, MultipartFile empImage, MultipartFile empPancard);

	
}
