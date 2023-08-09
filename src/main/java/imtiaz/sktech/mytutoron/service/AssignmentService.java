package imtiaz.sktech.mytutoron.service;

import imtiaz.sktech.mytutoron.model.domain.Assignment;
import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.mapper.AssignmentMapper;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentRequest;
import imtiaz.sktech.mytutoron.persistence.repository.AssignmentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AssignmentService {
    public static final String Assignment_NOT_FOUND = "Assignment not found";
    private final AssignmentMapper assignmentMapper;
    private final AssignmentRepository assignmentRepository;

    public Page<Assignment> getAll(Pageable pageable) {
        var entities = assignmentRepository.findAll(pageable);
        return entities.map(assignmentMapper::entityToDomain);
    }

    public Assignment getAssignment(UUID id) {
        var assignmentEntity = assignmentRepository.findById(id).orElseThrow(() -> new NotFoundException(Assignment_NOT_FOUND));
        return assignmentMapper.entityToDomain(assignmentEntity);
    }

    public UUID createOne(CreateAssignmentRequest request) {
        var assignmentEntity = assignmentMapper.domainToResponse(request);
        assignmentEntity.setId(UUID.randomUUID());
        var savedAssignment = assignmentRepository.save(assignmentEntity);
        return savedAssignment.getId();
    }

    public void deleteAssignment(UUID id) {
        assignmentRepository.deleteById(id);
    }
    
    public long countTotalAssignment() {
        return assignmentRepository.count();
    }

    public void updateOne(UpdateAssignmentRequest request, UUID id) {
        var AssignmentEntity = assignmentRepository.findById(id).orElseThrow(() -> new NotFoundException(Assignment_NOT_FOUND));
        AssignmentEntity.setAssignmentTitle(request.getAssignmentTitle());
        AssignmentEntity.setAdditionalNote(request.getAdditionalNote());
        assignmentRepository.save(AssignmentEntity);
    }
}
