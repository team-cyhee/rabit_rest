
package com.cyhee.rabit.dao.file;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.file.FileStatus;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FileRepository extends PagingAndSortingRepository<FileInfo, Long> {

    Page<FileInfo> findByStatusIn(List<FileStatus> statusList, Pageable pageable);

    @Query("select count(f) > 0 from FileInfo f where f.name = :name")
    boolean existsName(@Param("name") String name);
}

