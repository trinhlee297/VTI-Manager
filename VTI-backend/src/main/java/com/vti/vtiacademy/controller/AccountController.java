package com.vti.vtiacademy.controller;

import com.vti.vtiacademy.dto.AccountCreateRequest;
import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.AccountUpdateRequest;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.form.AccountFilterForm;
import com.vti.vtiacademy.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@Validated
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<Account> findAll() {
        return accountService.getAll();
    }

    @GetMapping("/getAll-mentor")
    public List<AccountDTO> getAllMentor() {
        List<Account> accountList = accountService.getAllMentor();
        List<AccountDTO> accountDTOList = new ArrayList<AccountDTO>();
        for (Account acc : accountList
        ) {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(acc, accountDTO);
            accountDTOList.add(accountDTO);
        }


        return accountDTOList;
    }

    @PostMapping("/search")
    public Page<AccountDTO> findAll(@RequestBody AccountFilterForm form) {
        Page<Account> accountPage = accountService.search(form);
        Page<AccountDTO> accountDtoPage = accountPage.map(entity -> {
            AccountDTO dto = accountService.accountEntityToDto(entity);
            return dto;
        });
        return accountDtoPage;
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return accountService.findById(id);

    }

    @PostMapping("/create")
    public Account create(@RequestBody @Valid AccountCreateRequest request) {
        return accountService.create(request);
    }

    @PutMapping("/update")
    public Account update(@RequestBody AccountUpdateRequest request) {
        return accountService.update(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        accountService.deleteById(id);
    }

}
