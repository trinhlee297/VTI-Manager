package com.vti.vtiacademy.service.impl;

import com.vti.vtiacademy.dto.AccountCreateRequest;
import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.AccountUpdateRequest;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.Role;
import com.vti.vtiacademy.exepction.CustomException;
import com.vti.vtiacademy.exepction.ErrorResponseEnum;
import com.vti.vtiacademy.form.AccountFilterForm;
import com.vti.vtiacademy.repository.AccountRepository;
import com.vti.vtiacademy.repository.ClassEntityRepository;
import com.vti.vtiacademy.service.AccountService;
import com.vti.vtiacademy.specification.AccountSpecification;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAllMentor() {
        return accountRepository.findAllByRole(Role.MENTOR);
    }


    @Override
    public Page<Account> search(AccountFilterForm form) {
        PageRequest pageRequest = Utils.buildPageRequest(form);
        Specification<Account> specification = AccountSpecification.buildCondition(form);
        return accountRepository.findAll(specification, pageRequest);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account create(AccountCreateRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        String regex = "^[a-zA-Z0-9]*$";
//        boolean checkedUserName = request.getUserName().matches(regex);
//
//        if (checkedUserName) {
//            throw new CustomException(ErrorResponseEnum.USERNAME_NOT_VALID);
//        }
        // đã custom anotation bên accountCreateRequest nên đoạn này ko cần if để check acc đã tồn tại hay chưa

        Account account = new Account();
        BeanUtils.copyProperties(request, account);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        // kiem tra xem acc co role la student ko, neu ko -> ktra co truyen classEntityId -> ban loi
        // neu co -> classentity theo classEntity tu request
        if (account.getRole() != Role.STUDENT) {
            // ktra classEntity co gia tri hay ko
            if (request.getClassEntityId() != null) {
                // ban ra loi (hoc sau)
            } else {
                // neu acc la student, tim doi tuong classEntity theo id truyen vao
                classEntityRepository.findById(request.getClassEntityId());
            }

        }
        return accountRepository.save(account);
    }


    @Override
    public Account update(AccountUpdateRequest request) {
        Account account = accountRepository.findById(request.getId()).get();
        String currentUserName = account.getUsername();
        String updateUserName = request.getUsername();
        if (!currentUserName.equals(updateUserName)) {
            if (accountRepository.existsByUsername(request.getUsername())) {
                throw new CustomException(ErrorResponseEnum.USERNAME_EXISTED);
            }
        }
        BeanUtils.copyProperties(request, account);
        // kiem tra xem acc co role la student ko, neu ko -> ktra co truyen classEntityId -> ban loi
        // neu co -> classentity theo classEntity tu request
        if (account.getRole() != Role.STUDENT) {
            // ktra classEntity co gia tri hay ko
            if (request.getClassEntityId() != null) {
                // ban ra loi (hoc sau)
            } else {
                // neu acc la student, tim doi tuong classEntity theo id truyen vao
                classEntityRepository.findById(request.getClassEntityId());
            }

        }
        return accountRepository.save(account);
    }

    @Override
    public AccountDTO accountEntityToDto(Account account) {
        AccountDTO accountDTO =  new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setDateOfBirth(account.getDateOfBirth());
        accountDTO.setAddress(account.getAddress());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setFacebook(account.getFullName());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setRole(account.getRole());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setPhoneNumber(account.getPhoneNumber());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setFacebook(account.getFacebook());
        accountDTO.setInformation(account.getInformation());
        accountDTO.setClassName(account.getClassEntity().getClassName());
        return accountDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()){
            List<GrantedAuthority> authorities = new ArrayList<>();
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(accountOptional.get().getRole().toString());
            authorities.add(accountOptional.get().getRole());
            return new User(username, accountOptional.get().getPassword(), authorities);
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
