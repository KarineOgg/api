package com.apirest.api.services.impl;

import com.apirest.api.domain.User;
import com.apirest.api.domain.dto.UserDTO;
import com.apirest.api.repositories.UserRepository;
import com.apirest.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
 public static final Integer ID      = 1;
 public static final String NAME     = "Karine";

 public static final String EMAIL    = "ka@email.com";
 public static final String PASSWORD = "123";

 @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
     MockitoAnnotations.openMocks(this);
     startUser();
    }

    @DisplayName("deve buscar Id e retornar a instancia de usuario")
    @Test
    void testarBuscarPorIdRetornandoInstanciaDeUsuario() {
       when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);

        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());


    }
   @DisplayName("deve buscar Id e retornar objeto nao encontrado")
    @Test
    void testarExceptionDeObjNaoEncontrado() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.findById(ID);
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }


    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
     user = new User(ID, NAME, EMAIL, PASSWORD);
     userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
     optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

    }
}