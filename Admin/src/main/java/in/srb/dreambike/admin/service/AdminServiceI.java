package in.srb.dreambike.admin.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.srb.dreambike.admin.model.AdminDetails;

public interface AdminServiceI {

	public AdminDetails saveData(String adminJson, MultipartFile empImage, MultipartFile empPancard);

	public AdminDetails getSingleEmp(int empId);

	public List<AdminDetails> getAllEmpDetails();

	 // updated method
    public AdminDetails updateData(int empId, String adminJson, MultipartFile empImage, MultipartFile empPancard);

	public AdminDetails loginCheck(String username, String password);

	public void deleteData(int empId);

}
