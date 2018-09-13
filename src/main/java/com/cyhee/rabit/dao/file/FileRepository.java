
package com.cyhee.rabit.dao.file;

import com.cyhee.rabit.model.file.FileInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called commentRepository
// CRUD refers Create, Read, Update, Delete

public interface FileRepository extends PagingAndSortingRepository<FileInfo, Long> {

    @Query("select count(f) as cnt from FileInfo f where f.name = :name and cnt > 0")
    boolean existsName(@Param("name") String name);
}

