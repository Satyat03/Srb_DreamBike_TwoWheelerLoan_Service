package in.srb.dreambike.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.srb.dreambike.admin.model.AdminDetails;
import in.srb.dreambike.admin.service.AdminServiceI;

@RequestMapping("/admin")
@RestController
@CrossOrigin("*")
public class Admin_Controller {

    @Autowired
    AdminServiceI serviceI;

    private static final Logger LOGGER = LoggerFactory.getLogger(Admin_Controller.class);

    @GetMapping("/get")
    public String getdata() {
        LOGGER.info("Received request at /get");
        return "Success";
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<AdminDetails> addData(@RequestParam("adminJson") String adminJson,
                                                @RequestPart("empImage") MultipartFile empImage,
                                                @RequestPart("empPancard") MultipartFile empPancard) {
        LOGGER.info("Adding new employee with data: {}", adminJson);
        AdminDetails details = serviceI.saveData(adminJson, empImage, empPancard);
        LOGGER.info("Employee added successfully: {}", details);
        return new ResponseEntity<>(details, HttpStatus.CREATED);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<AdminDetails> getSingleData(@PathVariable("empId") int empId) {
        LOGGER.info("Fetching employee with ID: {}", empId);
        AdminDetails ad = serviceI.getSingleEmp(empId);
        LOGGER.info("Employee fetched: {}", ad);
        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AdminDetails>> getAllEmp() {
        LOGGER.info("Fetching all employee records");
        List<AdminDetails> allEmp = serviceI.getAllEmpDetails();
        LOGGER.info("Total employees fetched: {}", allEmp.size());
        return new ResponseEntity<>(allEmp, HttpStatus.OK);
    }

    @PutMapping(value = "/{empId}", consumes = "multipart/form-data")
    public ResponseEntity<AdminDetails> updateEmp(@PathVariable("empId") int empId,
                                                  @RequestParam("adminJson") String adminJson,
                                                  @RequestPart("empImage") MultipartFile empImage,
                                                  @RequestPart("empPancard") MultipartFile empPancard) {
        LOGGER.info("Updating employee with ID: {}", empId);
        AdminDetails updatedAdmin = serviceI.updateData(empId, adminJson, empImage, empPancard);
        LOGGER.info("Employee updated: {}", updatedAdmin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @GetMapping("/adminLogin/{username}/{password}")
    public ResponseEntity<AdminDetails> userLogin(@PathVariable String username,
                                                  @PathVariable String password) {
        LOGGER.info("Login attempt for username: {}", username);
        AdminDetails a = serviceI.loginCheck(username, password);
        if (a != null) {
            LOGGER.info("Login successful for username: {}", username);
        } else {
            LOGGER.warn("Login failed for username: {}", username);
        }
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteEmp(@PathVariable int empId) {
        LOGGER.info("Deleting employee with ID: {}", empId);
        serviceI.deleteData(empId);
        LOGGER.info("Employee deleted with ID: {}", empId);
        return new ResponseEntity<>("Employee Deleted..!!", HttpStatus.OK);
    }
}
