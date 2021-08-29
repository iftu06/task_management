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
        UserDetails user = (UserDetails) authentication.getPrincipal();
        if(task.getId() == null) {
            task.setCreatedBy(user.getUsername());
            task.setUpdateddBy(user.getUsername());
        }else{
            task.setCreatedBy(user.getUsername());
            String userName = UserUtil.getUserName();
            Optional<Task> taskOpt = taskRepository.findById(task.getId());
            if(taskOpt.isPresent()){
                Task taskDb = taskOpt.get();
                if(!taskDb.getCreatedBy().equals(userName) && !UserUtil.isAdmin()){
                    throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
                }
            }
        }
        Task persistenceTask = taskRepository.save(task);
        return TaskDto.convertToDto(persistenceTask);
    }

    public TaskDto getTask(Integer taskId){
        String userName = UserUtil.getUserName();
        List<String> authorities = UserUtil.getAuthorities();
        Optional<Task> task = taskRepository.findById(taskId);

        if(!task.isPresent()){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        if(authorities.contains("ADMIN")){
            return TaskDto.convertToDto(task.get());
        }else{
            String taskCreatedBy = task.get().getCreatedBy();
            if(!taskCreatedBy.equals(userName)){
                new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }

        return TaskDto.convertToDto(task.get());
    }


    public List<TaskDto> getAllTasks() {
        String userName = UserUtil.getUserName();
        List<String> authorities = UserUtil.getAuthorities();
        List<Task> tasks = new ArrayList<>();
        if(authorities.contains("ADMIN")){
            tasks = taskRepository.findAll();
        }else{
            tasks = taskRepository.findByCreatedBy(userName);
        }

        return !tasks.isEmpty() ? TaskDto.convertToDto(tasks) : new ArrayList<TaskDto>();

    }

    public void remove(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDto> getTasksByProject(Integer proectId) {
        List<Task> tasks = taskRepository.findByProjectId(proectId);
        return !tasks.isEmpty() ? TaskDto.convertToDto(tasks) : new ArrayList<TaskDto>();
    }

    public Object getSearchedTask(TaskSeachField seachField) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);

        Root<Task> task = cq.from(Task.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(seachField.getStatus())) {
            predicates.add(cb.equal(task.get("status"), seachField.getStatus()));
        }
        if (seachField.getProjectId() != null) {
            predicates.add(cb.equal(task.get("project.id"), seachField.getProjectId()));
        }

        if (!StringUtils.isEmpty(seachField.getDueDate())) {
            final LocalDate dueDate = DateTimeUtil.parseDate(DateTimeFormatter.ofPattern("yyyy-MM-dd"), seachField.getDueDate());
            predicates.add(cb.lessThan(task.get("dueDate"), dueDate));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();

    }

}
