package com.jmr.stream.demostream.service;

import com.jmr.stream.demostream.model.entity.EventEntity;
import com.jmr.stream.demostream.model.repository.EventEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Event Service
 */
@Slf4j
@Service
@AllArgsConstructor
public class EvenService {

    private EventEntityRepository repository;

    public EventEntity recordEvent(EventEntity event) {
        return repository.save(event);
    }


    public List<EventEntity> getEvents()
    {
        return  repository.findAll();
    }

}
