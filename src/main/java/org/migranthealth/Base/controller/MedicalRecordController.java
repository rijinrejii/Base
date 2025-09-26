package org.migranthealth.Base.controller;

import java.util.List;
import java.util.Optional;

import org.migranthealth.Base.entity.MedicalRecord;
import org.migranthealth.Base.service.MedicalRecordService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    // CREATE - Add new medical record
    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(medicalRecord);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }
    
    // CREATE - Add medical record for specific patient
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> createMedicalRecordForPatient(
            @PathVariable Long patientId, 
            @RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord savedRecord = medicalRecordService.createMedicalRecordForPatient(patientId, medicalRecord);
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    // READ - Get all medical records
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    // READ - Get medical record by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicalRecordById(@PathVariable Long id) {
        Optional<MedicalRecord> record = medicalRecordService.getMedicalRecordById(id);
        if (record.isPresent()) {
            return new ResponseEntity<>(record.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
        }
    }
    
    // READ - Get medical records by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    // READ - Get medical records by doctor
    @GetMapping("/doctor/{doctorName}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByDoctor(@PathVariable String doctorName) {
        List<MedicalRecord> records = medicalRecordService.getMedicalRecordsByDoctor(doctorName);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    // READ - Search medical records
    @GetMapping("/search")
    public ResponseEntity<List<MedicalRecord>> searchMedicalRecords(@RequestParam String term) {
        List<MedicalRecord> records = medicalRecordService.searchMedicalRecords(term);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    // UPDATE - Update medical record
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord updatedRecord = medicalRecordService.updateMedicalRecord(id, medicalRecord);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    // DELETE - Delete medical record
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable Long id) {
        try {
            medicalRecordService.deleteMedicalRecord(id);
            return new ResponseEntity<>("Medical record deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}