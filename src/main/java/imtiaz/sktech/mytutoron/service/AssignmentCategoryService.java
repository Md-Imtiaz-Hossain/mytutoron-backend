package imtiaz.sktech.mytutoron.service;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.mapper.AssignmentCategoryMapper;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.repository.AssignmentCategoryRepository;
import org.springframework.data.domain.*;
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

//    public Page<AssignmentCategory> getAllPaginatedAssignmentCategory(PaginationArgs paginationArgs) {
//        Pageable pageable = AppUtils.getPageable(paginationArgs);
//
//        Page<AssignmentCategoryEntity> assignmentCategoryEntities;
//        Map<String, Object> specificParameters = AppUtils.getSpecificParameters(paginationArgs.getParameters());
//        if (!specificParameters.isEmpty()) {
//            Specification<AssignmentCategoryEntity> assignmentCategoryEntitySpecification = UserSpecification.getSpecification(specificParameters);
//            assignmentCategoryEntities = assignmentCategoryRepository.findAll(assignmentCategoryEntitySpecification, pageable);
//        }
//        else {
//            assignmentCategoryEntities = assignmentCategoryRepository.findAll(pageable);
//        }
//
//        List<AssignmentCategory> assignmentCategories = assignmentCategoryEntities.stream().map(assignmentCategoryMapper::toDomain).toList();
//        return new PageImpl<>(assignmentCategories, pageable, assignmentCategoryEntities.getTotalElements());
//    }

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

    public AssignmentCategoryEntity updateAssignmentCategory(UpdateAssignmentCategoryRequest request, UUID id){
        var AssignmentCategoryEntity = assignmentCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Assignment_CATEGORY_NOT_FOUND));
        AssignmentCategoryEntity.setName(request.getName());
        AssignmentCategoryEntity.setDescription(request.getDescription());
        return assignmentCategoryRepository.save(AssignmentCategoryEntity);
    }

    public void deleteAssignmentCategory(UUID id){
        assignmentCategoryRepository.deleteById(id);
    }

    public Long countAssignmentCategory() {
        return assignmentCategoryRepository.count();
    }

    public Page<AssignmentCategory> findAllWithPageable(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return assignmentCategoryRepository.findAll(pageable).map(assignmentCategoryMapper::toDomain);
    }

//    public Page<AssignmentCategoryEntity> getAllWithPagination(int pageNo, int pageSize, String sortBy, AscOrDesc ascOrDesc) {
//
//        Sort.Direction sortDirection = Sort.Direction.ASC;
//        Pageable pageable = PageRequest.of(pageNo, pageSize, ascOrDesc, sortBy);
//        Page<AssignmentCategoryEntity> pageResult = assignmentCategoryRepository.findAll(pageable);
//
//        return pageResult;
//    }
}
