package com.store.productStore.services;

import com.store.productStore.models.Administrator;
import com.store.productStore.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdminRepository adminRepository;

    public Iterable<Administrator> getAllAdmins(){
        Iterable<Administrator> admins=adminRepository.findAll();
        return admins;
    }

    public void addAdmin(String name, String password){
        Administrator admin=new Administrator(name, password);
        adminRepository.save(admin);
    }

    public void deleteAdminById(Long id){
        adminRepository.deleteById(id);
    }

    public Administrator getAdministratorByName(String name){
        return adminRepository.findByName(name);
    }
}
