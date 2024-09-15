package com.demo.user.service;

import com.demo.user.VO.Department;
import com.demo.user.VO.ResponseTemplateVO;
import com.demo.user.entity.User;
import com.demo.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment method of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findById(userId).get();

        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/".concat(user.getDepartmentId()),
                Department.class);

        vo.setUser(user);
        vo.setDepartment(department);

        return vo;
    }
}
