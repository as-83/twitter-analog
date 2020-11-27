package com.abdsul.sweeter.repos;

import com.abdsul.sweeter.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {

    Page<Message> findByTag(String tag, Pageable pageable);
    Page<Message> findAll(Pageable pageable);
}
