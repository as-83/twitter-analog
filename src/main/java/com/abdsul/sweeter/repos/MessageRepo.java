package com.abdsul.sweeter.repos;

import com.abdsul.sweeter.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {


    List<Message> findByTag(String tag);
}
