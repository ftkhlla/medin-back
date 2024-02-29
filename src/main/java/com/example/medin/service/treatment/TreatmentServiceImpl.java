package com.example.medin.service.treatment;

import com.example.medin.constant.TreatmentConstant;
import com.example.medin.model.dto.*;
import com.example.medin.model.entity.*;
import com.example.medin.repository.MedicineRepository;
import com.example.medin.repository.MedicineTimeRepository;
import com.example.medin.repository.TreatmentProgressRepository;
import com.example.medin.repository.TreatmentRepository;
import com.example.medin.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository repository;
    private final TreatmentProgressRepository treatmentProgressRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineTimeRepository medicineTimeRepository;
    private final UserService userService;

    public TreatmentServiceImpl(TreatmentRepository repository,
                                TreatmentProgressRepository treatmentProgressRepository,
                                MedicineRepository medicineRepository,
                                MedicineTimeRepository medicineTimeRepository,
                                UserService userService) {
        this.repository = repository;
        this.treatmentProgressRepository = treatmentProgressRepository;
        this.medicineRepository = medicineRepository;
        this.medicineTimeRepository = medicineTimeRepository;
        this.userService = userService;
    }

    private List<Treatment> findAllByDoctorIinAndPacientIin(String doctorIin, String pacientIin) {
        User pacient = userService.findPacientByIin(pacientIin);
        User doctor = userService.findDoctorByIin(doctorIin);
        return repository.findAllByDoctorIdAndPacientId(doctor.getId(), pacient.getId());
    }

    private Treatment findByDoctorIdAndPacientId(String doctorIin, String pacientIin) {
        User pacient = userService.findPacientByIin(pacientIin);
        User doctor = userService.findDoctorByIin(doctorIin);
        return repository.findByDoctorIdAndPacientId(doctor.getId(), pacient.getId());
    }

    private boolean existsByDoctorIdAndPacientId(String doctorIin, String pacientIin) {
        User pacient = userService.findPacientByIin(pacientIin);
        User doctor = userService.findDoctorByIin(doctorIin);
        return repository.existsByDoctorIdAndPacientId(doctor.getId(), pacient.getId());
    }

    @Override
    public ResponseEntity<?> create(TreatmentDto request) {
        User pacient = userService.findPacientByIin(request.getPacientIin());
        User doctor = userService.findDoctorByIin(request.getDoctorIin());

        if (repository.existsByDoctorIdAndPacientId(doctor.getId(), pacient.getId())) {
            Map<String, String> result = new HashMap<>();
            result.put("result", "Treatment exist");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Treatment treatment = new Treatment(request, pacient, doctor);
        repository.save(treatment);

        for (MedicineDto medicineDto : request.getMedicine()) {
            Medicine medicine = new Medicine(medicineDto.getName(), treatment);
            medicineRepository.save(medicine);
        }

        for (MedicineTimeDto medicineTimeDto : request.getTimeToMedicine()) {
            MedicineTime medicineTime = new MedicineTime(
                    medicineTimeDto.getTimeToMedicine(),
                    treatment);
            medicineTimeRepository.save(medicineTime);
            System.err.println(LocalTime.parse(medicineTime.getTimeToMedicine()));
        }

        Map<String, String> result = new HashMap<>();
        result.put("result", "Treatment created");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> get(String doctorIin, String pacientIin) {
        List<Treatment> treatmentList = findAllByDoctorIinAndPacientIin(doctorIin, pacientIin);
        List<TreatmentDto> treatmentDtoList = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            List<Medicine> medicineList = medicineRepository.findAllByTreatmentId(treatment.getId());
            List<MedicineDto> medicineDtoList = new ArrayList<>();
            for (Medicine medicine : medicineList) {
                MedicineDto medicineDto = new MedicineDto(medicine.getName());
                medicineDtoList.add(medicineDto);
            }
            List<MedicineTime> medicineTimeList = medicineTimeRepository.findAllByTreatmentId(treatment.getId());
            List<MedicineTimeDto> medicineTimeDtoList = new ArrayList<>();
            for (MedicineTime medicineTime : medicineTimeList) {
                MedicineTimeDto medicineTimeDto = new MedicineTimeDto(medicineTime.getTimeToMedicine());
                medicineTimeDtoList.add(medicineTimeDto);
            }
            TreatmentDto treatmentDto = new TreatmentDto(
                    treatment,
                    medicineDtoList,
                    medicineTimeDtoList);
            treatmentDtoList.add(treatmentDto);
        }
        return new ResponseEntity<>(treatmentDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDetails(String doctorIin, String pacientIin) {
        Treatment treatment = findByDoctorIdAndPacientId(doctorIin, pacientIin);
        List<LocalDate> localDateList = new ArrayList<>();
        long diff = ChronoUnit.DAYS.between(treatment.getStart(), treatment.getEnd());
        if (treatment.getFrequencyInPeriod().equals(TreatmentConstant.ONE_IN_ONE)) {
            for (long i = 0; i <= diff; i++) {
                localDateList.add(treatment.getStart().plusDays(i));
            }
        }
        if (treatment.getFrequencyInPeriod().equals(TreatmentConstant.ONE_IN_TWO)) {
            for (long i = 0; i <= diff; i = i + 2) {
                localDateList.add(treatment.getStart().plusDays(i));
            }
        }
        if (treatment.getFrequencyInPeriod().equals(TreatmentConstant.THREE_IN_SEVEN)) {
            for (long i = 0; i <= diff; i++) {
                LocalDate check = treatment.getStart().plusDays(i);

                if (check.getDayOfWeek().equals(DayOfWeek.MONDAY) ||
                        check.getDayOfWeek().equals(DayOfWeek.WEDNESDAY) ||
                        check.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                    localDateList.add(check);
                }
            }
        }

        List<TreatmentDetailDto> treatmentDetailDtoList = new ArrayList<>();
        List<MedicineDto> medicineDtoList = new ArrayList<>();
        List<Medicine> medicineList = medicineRepository.findAllByTreatmentId(treatment.getId());
        for (Medicine medicine : medicineList) {
            MedicineDto medicineDto = new MedicineDto(medicine.getName());
            medicineDtoList.add(medicineDto);
        }
        float percent = 0;
        for (LocalDate localDate : localDateList) {
            List<ProgressDto> progressDtoList = new ArrayList<>();
            List<MedicineTime> medicineTimeList = medicineTimeRepository.findAllByTreatmentId(treatment.getId());
            float count = 0;
            for (MedicineTime medicineTime : medicineTimeList) {
                LocalDateTime progressDateTime = LocalDateTime.of(localDate, LocalTime.parse(medicineTime.getTimeToMedicine()));
                boolean done = treatmentProgressRepository.existsByTimeAndTreatmentId(progressDateTime, treatment.getId());
                TreatmentProgress treatmentProgress = treatmentProgressRepository.findByTimeAndTreatmentId(progressDateTime, treatment.getId());
                if (done) {
                    count++;
                }
                String doctorComment, pacientComment;
                try {
                    doctorComment = treatmentProgress.getDoctorComment();
                } catch (NullPointerException exception){
                    doctorComment = "";
                }
                try{
                    pacientComment = treatmentProgress.getPacientComment();
                } catch (NullPointerException exception){
                    pacientComment = "";
                }
                ProgressDto progressDto = new ProgressDto(medicineTime.getTimeToMedicine(), done, doctorComment, pacientComment);
                progressDtoList.add(progressDto);

                percent = (count / medicineTimeList.size()) * 100;
            }
            TreatmentDetailDto treatmentDetailDto = new TreatmentDetailDto(localDate, percent, progressDtoList, medicineDtoList);
            treatmentDetailDtoList.add(treatmentDetailDto);
        }
        return new ResponseEntity<>(treatmentDetailDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createProgress(CreateProgressDto request) {
        boolean exists = existsByDoctorIdAndPacientId(request.getDoctorIin(), request.getPacientIin());
        Map<String, String> result = new HashMap<>();
        if (!exists) {
            result.put("message", "Treatment not found");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Treatment treatment = findByDoctorIdAndPacientId(request.getDoctorIin(), request.getPacientIin());
        for (ProgressDto progressDto : request.getProgress()) {
            if (!treatmentProgressRepository.existsByTimeAndTreatmentId(LocalDateTime.parse(progressDto.getTime()), treatment.getId())) {
                TreatmentProgress progress = new TreatmentProgress(LocalDateTime.parse(progressDto.getTime()), treatment);
                treatmentProgressRepository.save(progress);
            } else {
                result.put("message", "Time is not correct and exists");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        }
        result.put("message", "Treatment progress saved");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createComment(CommentDto request) {
        Map<String, String> response = new HashMap<>();

        User pacient = userService.findPacientByIin(request.getPacientIin());
        User doctor = userService.findDoctorByIin(request.getDoctorIin());

        Treatment treatment = repository.findByDoctorIdAndPacientId(doctor.getId(), pacient.getId());

        TreatmentProgress treatmentProgress = treatmentProgressRepository.findByTimeAndTreatmentId(request.getDate(), treatment.getId());
        if (treatmentProgress == null) {
            response.put("message", "Treatment by date " + request.getDate() + " not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        treatmentProgress.setDoctorComment(request.getDoctorComment());
        treatmentProgress.setPacientComment(request.getPacientComment());
        treatmentProgressRepository.save(treatmentProgress);
        response.put("message", "Comment saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
