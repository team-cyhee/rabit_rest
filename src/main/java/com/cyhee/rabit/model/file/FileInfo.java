package com.cyhee.rabit.model.file;

import com.cyhee.rabit.model.cmm.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@NoArgsConstructor
public class FileInfo extends TimestampEntity {

    private String path;

    private Long size;

    @Column(length=100)
    private String name;

    @Column(length=100)
    private String orgName;

    @Column(length=5)
    private String extension;

    @Column(nullable = false)
    private FileStatus status;

    public FileInfo(String path, Long size, String name, String orgName, String extension, FileStatus status) {
        this.path = path;
        this.size = size;
        this.name = name;
        this.orgName = orgName;
        this.extension = extension;
        this.status = status;
    }
}
