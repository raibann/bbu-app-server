package com.dinsaren.bbuappserver.security.services;

import com.dinsaren.bbuappserver.constants.Constants;
import com.dinsaren.bbuappserver.models.User;
import com.dinsaren.bbuappserver.repository.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;
    var userName = userRepository.findByUsernameAndStatus(username, Constants.ACTIVE_STATUS);
    if(username.isEmpty()){
      user = userRepository.findByPhoneAndStatus(username, Constants.ACTIVE_STATUS)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone: " + username));
    }else{
      user = userName.get();
    }

    return UserDetailsImpl.build(user);
  }

}
