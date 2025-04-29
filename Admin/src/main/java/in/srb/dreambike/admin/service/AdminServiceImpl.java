package in.srb.dreambike.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.srb.dreambike.admin.Exception.FileInvalidException;
import in.srb.dreambike.admin.Exception.InvalidFormatException;
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
            
            // Username= 8 characters
            if (adminDetails.getUsername() == null || adminDetails.getUsername().length() < 8) {
                throw new InvalidFormatException("Username must be at least 8 characters long.");
            }

            // Password = one special character
            String password = adminDetails.getPasswoed();
            if (password == null || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                throw new InvalidFormatException("Password must contain at least one special character.");
            }

            // Ensure names are in uppercase
            if (adminDetails.getEmpFirstName() == null || !adminDetails.getEmpFirstName().equals(adminDetails.getEmpFirstName().toUpperCase())) {
                throw new InvalidFormatException("First name must be in uppercase.");
            }
            if (adminDetails.getEmpMiddleName() != null && !adminDetails.getEmpMiddleName().equals(adminDetails.getEmpMiddleName().toUpperCase())) {
                throw new InvalidFormatException("Middle name must be in uppercase.");
            }
            if (adminDetails.getEmpLastName() == null || !adminDetails.getEmpLastName().equals(adminDetails.getEmpLastName().toUpperCase())) {
                throw new InvalidFormatException("Last name must be in uppercase.");
            }

            // File validations
            if (empImage == null || empImage.isEmpty()) {
                throw new FileInvalidException("Employee image is required.");
            }
            if (empPancard == null || empPancard.isEmpty()) {
                throw new FileInvalidException("Employee PAN card is required.");
            }

            // Optional: File size check (e.g., max 2MB)
            long maxFileSize = 2 * 1024 * 1024; // 2 MB
            if (empImage.getSize() > maxFileSize || empPancard.getSize() > maxFileSize) {
                throw new FileInvalidException("File size must be less than 2MB.");
            }
            
            
            
            adminDetails.setEmpImage(empImage.getBytes());
            adminDetails.setEmpPancard(empPancard.getBytes());
           
            return adminRepo.save(adminDetails);
        } 
        catch (IOException e) 
        {
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
