package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.UsernameNotFoundException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repos;
    private final UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.repos = userRepository;

        this.userMapper = userMapper;
    }

    public String saveUser(UserInputDto userInputDto) {

        User newUser = userMapper.userInputDtoToUser(userInputDto);

        return repos.save(newUser).getUsername();

    }

    public User getUser(String id){
        return repos.getReferenceById(id);
    }

    public Iterable<UserDto> getAllUsers(){
        List<User> reposUserList = repos.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user :
                reposUserList) {

            userDtos.add(userMapper.userToUserDto(user));

        }
        return userDtos;
    }

    public User updateUser(String username, UserInputDto userInputDto){

        if(repos.existsById(username) && username.equals(userInputDto.getUsername())) {
            return repos.getReferenceById(saveUser(userInputDto));
        } else {
            throw new UsernameNotFoundException("Username does not exist");
        }

    }

    public void deleteUser(String user){
        repos.deleteById(user);
    }
}
