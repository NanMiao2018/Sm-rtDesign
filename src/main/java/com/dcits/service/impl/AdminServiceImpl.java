package com.dcits.service.impl;

import com.dcits.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author nanmiaoa
 * @data 2020/10/30 15:51
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Override
    @Transactional
    public void toLogin(String name, String password) {

    }
}
