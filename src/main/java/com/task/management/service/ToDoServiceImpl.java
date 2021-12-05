package com.task.management.service;

import com.task.management.Utillity.DateTimeUtil;
import com.task.management.Utillity.ToDoSearchField;
import com.task.management.dto.ToDoDto;
import com.task.management.exception.ResourceNotFoundException;
import com.task.management.exception.ToDoException;
import com.task.management.exception.ToDoExceptionFactory;
import com.task.management.model.Priority;
import com.task.management.model.ToDo;
import com.task.management.model.ToDoStatus;
import com.task.management.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Divineit-Iftekher on 8/12/2017.
 */
@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    ToDoRepository toDoRepository;

    @Value("${com.task.management.base.url}")
    private String baseUrl;

    @Autowired
    EntityManager em;

    @Transactional
    @Override
    public ToDoDto create(ToDo toDo) {

        if (toDo.getId() != null) {
            throw ToDoExceptionFactory.UNSUPPORTED_OPERATION;
        }

        if (toDo.getStatus() != null) {
            throw ToDoExceptionFactory.UNSUPPORTED_OPERATION;
        }

        toDo.setStatus(ToDoStatus.STARTED);

        ToDo todoDb = toDoRepository.save(toDo);
        return ToDoDto.convertToDto(todoDb, baseUrl);
    }

    @Override
    public ToDoDto getToDo(Integer toDoId) {
        Optional<ToDo> toDoOpt = toDoRepository.findById(toDoId);

        if (!toDoOpt.isPresent()) {
            throw ToDoExceptionFactory.RESOURCE_NOT_FOUND_ERROR;
        }

        return ToDoDto.convertToDto(toDoOpt.get(), baseUrl);

    }

    @Override
    public void remove(Integer toDoId) {
        ToDoDto toDoDto = getToDo(toDoId);
        toDoRepository.deleteById(toDoId);
    }

    @Override
    public ToDoDto update(ToDo toDo) {
        if (toDo.getStatus() != null && toDo.getStatus().equals(ToDoStatus.DONE)) {
            throw ToDoExceptionFactory.UNSUPPORTED_OPERATION;
        }
        ToDoDto toDoDto = getToDo(toDo.getId());
        toDo.setStatus(toDoDto.getStatus());

        if (StringUtils.isEmpty(toDo.getTitle())) {
            toDo.setTitle(toDoDto.getTitle());
        }

        if (StringUtils.isEmpty(toDo.getDescription())) {
            toDo.setDescription(toDoDto.getDescription());
        }

        if (toDo.getPriority() == null) {
            toDo.setPriority(Priority.valueOf(toDoDto.getPriority()));
        }

        ToDo todoDb = toDoRepository.save(toDo);
        return ToDoDto.convertToDto(todoDb, baseUrl);
    }

    @Override
    public List<ToDoDto> getToDos() {
        List<ToDo> toDos = toDoRepository.findAll();
        Collections.sort(toDos);
        return toDos.stream()
                .map(toDo -> ToDoDto.convertToDto(toDo, baseUrl))
                .collect(Collectors.toList());
    }

    @Override
    public ToDoDto markAsDone(Integer id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> ToDoExceptionFactory.RESOURCE_NOT_FOUND_ERROR);
        toDo.setStatus(ToDoStatus.DONE);
        ToDo doneToDo = toDoRepository.save(toDo);
        return ToDoDto.convertToDto(doneToDo, baseUrl);
    }

    @Override
    public List<ToDoDto> searchToDo(ToDoSearchField seachField) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToDo> cq = cb.createQuery(ToDo.class);

        List<Predicate> predicates = getPredicates(seachField, cb, cq);

        cq.where(predicates.toArray(new Predicate[0]));

        List<ToDo> todos = em.createQuery(cq).getResultList();
        Collections.sort(todos);

        return ToDoDto.convertToDto(todos, baseUrl);
    }

    private List<Predicate> getPredicates(ToDoSearchField seachField,
                                          CriteriaBuilder cb,
                                          CriteriaQuery cq) {
        List<Predicate> predicates = new ArrayList<>();
        Root<ToDo> todo = cq.from(ToDo.class);


        if (!StringUtils.isEmpty(seachField.getStatus())) {
            predicates.add(cb.equal(todo.get("status"), ToDoStatus.valueOf(seachField.getStatus())));
        }

        return predicates;
    }


}
