package in.srb.dreambike.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.dreambike.admin.model.AdminDetails;
import in.srb.dreambike.admin.repo.AdminRepo;

@Service
public class AdminServiceImpl implements AdminServiceI {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public AdminDetails saveData(String adminJson, MultipartFile empImage, MultipartFile empPancard) {
        try {
            AdminDetails adminDetails = mapper.readValue(adminJson, AdminDetails.class);
            adminDetails.setEmpImage(empImage.getBytes());
            adminDetails.setEmpPancard(empPancard.getBytes());
            return adminRepo.save(adminDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AdminDetails getSingleEmp(int empId) {
        Optional<AdminDetails> optional = adminRepo.findById(empId);
        return optional.orElse(null);
    }

    @Override
    public List<AdminDetails> getAllEmpDetails() {
        return adminRepo.findAll();
    }

    @Override
    public AdminDetails updateData(int empId, String adminJson, MultipartFile empImage, MultipartFile empPancard) {
        try {
            Optional<AdminDetails> optional = adminRepo.findById(empId);
            if (optional.isPresent()) {
                AdminDetails existingAdmin = optional.get();

                // Convert the incoming JSON to a new AdminDetails object
                AdminDetails updatedAdmin = mapper.readValue(adminJson, AdminDetails.class);

                // Update fields individually
                existingAdmin.setEmpFirstName(updatedAdmin.getEmpFirstName());
                existingAdmin.setEmpMiddleName(updatedAdmin.getEmpMiddleName());
                existingAdmin.setEmpLastName(updatedAdmin.getEmpLastName());
                existingAdmin.setEmpEmail(updatedAdmin.getEmpEmail());
                existingAdmin.setEmpSalary(updatedAdmin.getEmpSalary());
                existingAdmin.setEmpAge(updatedAdmin.getEmpAge());
                existingAdmin.setUsername(updatedAdmin.getUsername());
                existingAdmin.setPasswoed(updatedAdmin.getPasswoed());
                existingAdmin.setUsertype(updatedAdmin.getUsertype());

                // Only update image if a new one is provided
                if (empImage != null && !empImage.isEmpty()) {
                    existingAdmin.setEmpImage(empImage.getBytes());
                }

                if (empPancard != null && !empPancard.isEmpty()) {
                    existingAdmin.setEmpPancard(empPancard.getBytes());
                }

                return adminRepo.save(existingAdmin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
