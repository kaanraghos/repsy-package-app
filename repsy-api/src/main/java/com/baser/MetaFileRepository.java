package com.baser;

import com.baser.entity.MetaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaFileRepository extends JpaRepository<MetaFile, Long> {
}
