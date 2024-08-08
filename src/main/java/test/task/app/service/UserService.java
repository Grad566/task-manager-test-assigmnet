package test.task.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.task.app.dto.userDTO.UserCreatedDTO;
import test.task.app.dto.userDTO.UserDTO;
import test.task.app.dto.userDTO.UserUpdatedDTO;
import test.task.app.exception.ResourceNotFoundException;
import test.task.app.mapper.UserMapper;
import test.task.app.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public UserDTO show(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
        return mapper.map(user);
    }

    public UserDTO create(UserCreatedDTO data) {
        var user = mapper.map(data);
        repository.save(user);
        return mapper.map(user);
    }

    public UserDTO update(UserUpdatedDTO data, Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
        mapper.update(data, user);
        repository.save(user);
        return mapper.map(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
