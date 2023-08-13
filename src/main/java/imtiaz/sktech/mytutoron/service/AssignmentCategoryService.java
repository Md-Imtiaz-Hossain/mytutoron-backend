package imtiaz.sktech.mytutoron.service;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.mapper.AssignmentCategoryMapper;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.repository.AssignmentCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AssignmentCategoryService {

    public static final String Assignment_CATEGORY_NOT_FOUND = "Assignment category not found";
    private final AssignmentCategoryMapper assignmentCategoryMapper;
    private final AssignmentCategoryRepository assignmentCategoryRepository;

    public Page<AssignmentCategory> getAll(Pageable pageable){
        return assignmentCategoryRepository.findAll(pageable).map(assignmentCategoryMapper::toDomain);
    }

    public List<AssignmentCategory> getAllAssignmentCategory() {
        return assignmentCategoryRepository.findAll().stream().map(assignmentCategoryMapper::toDomain).toList();
    }
    public AssignmentCategory getOne(UUID id){
        var AssignmentCategoryEntity = assignmentCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Assignment_CATEGORY_NOT_FOUND));
        return assignmentCategoryMapper.toDomain(AssignmentCategoryEntity);
    }

    public UUID createOne(CreateAssignmentCategoryRequest request){
        var AssignmentCategoryEntity = assignmentCategoryMapper.toEntity(request);
        AssignmentCategoryEntity.setId(UUID.randomUUID());
        var savedCategory = assignmentCategoryRepository.save(AssignmentCategoryEntity);
        return savedCategory.getId();
    }

    public AssignmentCategoryEntity create(CreateAssignmentCategoryRequest request){
        var assignmentCategoryEntity = assignmentCategoryMapper.toEntity(request);
        assignmentCategoryEntity.setId(UUID.randomUUID());
        return assignmentCategoryRepository.save(assignmentCategoryEntity);
    }

    public void updateAssignmentCategory(UpdateAssignmentCategoryRequest request, UUID id){
        var AssignmentCategoryEntity = assignmentCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Assignment_CATEGORY_NOT_FOUND));
        AssignmentCategoryEntity.setName(request.getName());
        AssignmentCategoryEntity.setDescription(request.getDescription());
        assignmentCategoryRepository.save(AssignmentCategoryEntity);
    }

    public void deleteAssignment(UUID id){
        assignmentCategoryRepository.deleteById(id);
    }

    public Long countAssignmentCategory() {
        return assignmentCategoryRepository.count();
    }
}
