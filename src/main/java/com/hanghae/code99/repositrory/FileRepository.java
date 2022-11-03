package com.hanghae.code99.repositrory;

import com.hanghae.code99.domain.message.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {
}
