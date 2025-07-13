package com.product.category.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.category.domain.CategoryProductMappingRequest;
import com.product.enums.RequestStatus;

@Service
public interface CategoryProductMappingRequestDAO {

	void createMappingRequest(CategoryProductMappingRequest request);

    void updateRequestStatus(List<Long> requestIds, Long approvedBy, RequestStatus status);

    List<CategoryProductMappingRequest> findAllRequest(List<Long> requestIds,RequestStatus requestStatus,Long loggedInUser) throws Exception;
}
