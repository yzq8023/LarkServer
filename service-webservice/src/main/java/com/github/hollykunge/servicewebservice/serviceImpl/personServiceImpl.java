package com.github.hollykunge.servicewebservice.serviceImpl;

import com.github.hollykunge.servicewebservice.dao.personMapperDao;
import com.github.hollykunge.servicewebservice.model.Person;
import com.github.hollykunge.servicewebservice.service.personService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class personServiceImpl implements personService {
    @Autowired
    personMapperDao personMapperDao;

    @Override
    public Person queryPersonByName() {
        Person person = personMapperDao.findByName();
        return person;
    }
}
