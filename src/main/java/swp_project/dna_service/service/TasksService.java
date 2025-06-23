package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.TasksRequest;
import swp_project.dna_service.dto.response.TasksResponse;
import swp_project.dna_service.entity.Dna_Service;
import swp_project.dna_service.entity.MedicalRecord;
import swp_project.dna_service.entity.OrderDetail;
import swp_project.dna_service.entity.Tasks;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.TasksMapper;
import swp_project.dna_service.repository.MedicalRecordRepository;
import swp_project.dna_service.repository.OrderDetailRepository;
import swp_project.dna_service.repository.ServiceRepository;
import swp_project.dna_service.repository.TasksRepository;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TasksService {

    TasksRepository tasksRepository;
    TasksMapper tasksMapper;
    ServiceRepository serviceRepository;
    MedicalRecordRepository medicalRecordRepository;
    OrderDetailRepository orderDetailRepository;


    public TasksResponse createTask(TasksRequest request) {

        Tasks taskEntity = tasksMapper.toEntity(request);

        if (request.getDnaServiceId() != null) {
            Dna_Service service = serviceRepository.findById(request.getDnaServiceId())
                    .orElseThrow(() -> new RuntimeException("Dna_Service not found"));
            taskEntity.setDnaService(service);
        }

        OrderDetail orderDetail = orderDetailRepository.findById(request.getOrderDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        taskEntity.setOrderDetail(orderDetail);

        MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new AppException(ErrorCode.MEDICAL_RECORD_NOT_FOUND));
        taskEntity.setMedicalRecord(medicalRecord);

        log.info("OrderDetailId: {}", request.getOrderDetailId());
        log.info("MedicalRecordId: {}", request.getMedicalRecordId());
        log.info("DnaServiceId: {}", request.getDnaServiceId());

        Date now = new Date();
        taskEntity.setCreatedAt(now);
        taskEntity.setUpdatedAt(now);

        Tasks savedTask = tasksRepository.save(taskEntity);

        var response = tasksMapper.toResponse(savedTask);
        response.setDnaServiceId(savedTask.getDnaService().getId());
        response.setOrderDetailId(savedTask.getOrderDetail().getId());
        response.setMedicalRecordId(savedTask.getMedicalRecord().getId());
        return response;
    }


    public TasksResponse updateTask(String id, TasksRequest request) {
        Tasks existing = tasksRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        tasksMapper.updateTasks(existing, request); // Cần có mapper update

        if (request.getDnaServiceId() != null) {
            Dna_Service service = serviceRepository.findById(request.getDnaServiceId())
                    .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
            existing.setDnaService(service);
        }

        OrderDetail orderDetail = orderDetailRepository.findById(request.getOrderDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        existing.setOrderDetail(orderDetail);

        MedicalRecord medicalRecord = medicalRecordRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new AppException(ErrorCode.MEDICAL_RECORD_NOT_FOUND));
        existing.setMedicalRecord(medicalRecord);

        existing.setUpdatedAt(new Date());

        return tasksMapper.toResponse(tasksRepository.save(existing));
    }


    public void deleteTask(String id) {
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
        tasksRepository.delete(task);
    }


    public TasksResponse getTaskById(String id) {
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
        TasksResponse response = tasksMapper.toResponse(task);

        response.setDnaServiceId(task.getDnaService() != null ? task.getDnaService().getId() : null);
        response.setOrderDetailId(task.getOrderDetail() != null ? task.getOrderDetail().getId() : null);
        response.setMedicalRecordId(task.getMedicalRecord() != null ? task.getMedicalRecord().getId() : null);

        return response;
    }



    public List<TasksResponse> getTasksByOrderDetailId(String orderDetailId) {
        return tasksRepository.findByOrderDetail_Id(orderDetailId).stream()
                .map(task -> {
                    TasksResponse response = tasksMapper.toResponse(task);
                    response.setDnaServiceId(task.getDnaService() != null ? task.getDnaService().getId() : null);
                    response.setOrderDetailId(task.getOrderDetail() != null ? task.getOrderDetail().getId() : null);
                    response.setMedicalRecordId(task.getMedicalRecord() != null ? task.getMedicalRecord().getId() : null);
                    return response;
                }).toList();
    }

    public List<TasksResponse> getTasksByDnaServiceId(String dnaServiceId) {
        return tasksRepository.findByDnaService_Id(dnaServiceId).stream()
                .map(task -> {
                    TasksResponse response = tasksMapper.toResponse(task);
                    response.setDnaServiceId(task.getDnaService() != null ? task.getDnaService().getId() : null);
                    response.setOrderDetailId(task.getOrderDetail() != null ? task.getOrderDetail().getId() : null);
                    response.setMedicalRecordId(task.getMedicalRecord() != null ? task.getMedicalRecord().getId() : null);
                    return response;
                }).toList();
    }


    public List<TasksResponse> getAllTasks() {
        return tasksRepository.findAll().stream()
                .map(task -> {
                    TasksResponse response = tasksMapper.toResponse(task);
                    response.setDnaServiceId(task.getDnaService() != null ? task.getDnaService().getId() : null);
                    response.setOrderDetailId(task.getOrderDetail() != null ? task.getOrderDetail().getId() : null);
                    response.setMedicalRecordId(task.getMedicalRecord() != null ? task.getMedicalRecord().getId() : null);
                    return response;
                }).toList();
    }

}
