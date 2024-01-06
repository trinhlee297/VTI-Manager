package com.vti.vtiacademy.service;


import com.vti.vtiacademy.dto.AccountCreateRequest;
import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.AccountUpdateRequest;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.form.AccountFilterForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    List<Account> getAll();

    List<Account> getAllMentor();


    Page<Account> search(AccountFilterForm form);

    Account findById(Long id);

    void deleteById(Long id);

    Account create(AccountCreateRequest request);

    Account update(AccountUpdateRequest request);

    AccountDTO accountEntityToDto(Account account);
}
