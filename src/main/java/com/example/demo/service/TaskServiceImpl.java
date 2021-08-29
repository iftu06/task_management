package com.example.demo.service;

import com.example.demo.Utillity.DateTimeUtil;
import com.example.demo.Utillity.TaskSeachField;
import com.example.demo.Utillity.UserUtil;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Divineit-Iftekher on 8/12/2017.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EntityManager em;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    @Transactional
    public TaskDto save(Task task) {
        String userName = UserUtil.getUserName();
        if (task.getId() == null) {
            task.setCreatedBy(userName);
            task.setUpdatedBy(userName);
        } else {
            task.setCreatedBy(userName);
            Optional<Task> taskOpt = taskRepository.findById(task.getId());
            if (taskOpt.isPresent()) {
                Task taskDb = taskOpt.get();
                if (!taskDb.getCreatedBy().equals(userName) && !UserUtil.isAdmin()) {
                    throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
                }
            }
        }
        Task persistenceTask = taskRepository.save(task);
        return TaskDto.convertToDto(persistenceTask);
    }

    public TaskDto getTask(Integer taskId) {
        String userName = UserUtil.getUserName();
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if (UserUtil.isAdmin()) {
            return TaskDto.convertToDto(task.get());
        } else {
            String taskCreatedBy = task.get().getCreatedBy();
            if (!taskCreatedBy.equals(userName)) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        return TaskDto.convertToDto(task.get());
    }


    public List<TaskDto> getAllTasks() {
        String userName = UserUtil.getUserName();
        List<Task> tasks = new ArrayList<>();
        if (UserUtil.isAdmin()) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByCreatedBy(userName);
        }

        return !tasks.isEmpty() ? TaskDto.convertToDto(tasks) : new ArrayList<TaskDto>();

    }


    public void remove(Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if (!UserUtil.isAdmin()) {
            String taskCreatedBy = task.get().getCreatedBy();
            String userName = UserUtil.getUserName();
            if (!taskCreatedBy.equals(userName)) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskDto> getTasksByProject(Integer projectId) {
        List<Task> tasks = new ArrayList<>();
        if (UserUtil.isAdmin()) {
            tasks = taskRepository.findByProjectId(projectId);
        } else {
            String userName = UserUtil.getUserName();
            tasks = taskRepository.findByCreatedByAndProjectId(userName, projectId);
        }
        return !tasks.isEmpty() ? TaskDto.convertToDto(tasks) : new ArrayList<TaskDto>();
    }

    private List<Predicate> getPredicates(TaskSeachField seachField,
                                          CriteriaBuilder cb,
                                          CriteriaQuery cq) {
        List<Predicate> predicates = new ArrayList<>();
        Root<Task> task = cq.from(Task.class);

        if (!UserUtil.isAdmin()) {
            String userName = UserUtil.getUserName();
            predicates.add(cb.equal(task.get("createdBy"), userName));
        }

        if (!StringUtils.isEmpty(seachField.getStatus())) {
            predicates.add(cb.equal(task.get("status"), seachField.getStatus()));
        }
        if (seachField.getProjectId() != null) {
            predicates.add(cb.equal(task.get("project").get("id"), seachField.getProjectId()));
        }

        if (!StringUtils.isEmpty(seachField.getDueDate())) {
            final LocalDate dueDate = DateTimeUtil.parseDate(DateTimeFormatter.ofPattern("yyyy-MM-dd"), seachField.getDueDate());
            predicates.add(cb.lessThan(task.get("dueDate"), dueDate));
        }
        return predicates;
    }

    public List<TaskDto> searchTask(TaskSeachField seachField) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);

        List<Predicate> predicates = getPredicates(seachField, cb, cq);

        cq.where(predicates.toArray(new Predicate[0]));

        List<Task> tasks = em.createQuery(cq).getResultList();

        return TaskDto.convertToDto(tasks);

    }

}
