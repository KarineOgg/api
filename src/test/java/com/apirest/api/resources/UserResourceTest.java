package com.apirest.api.resources;

import com.apirest.api.domain.User;
import com.apirest.api.domain.dto.UserDTO;
import com.apirest.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID      = 1;
    public static final Integer INDEX   = 0;
    public static final String NAME     = "Karine";

    public static final String EMAIL    = "ka@mail.com";
    public static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @InjectMocks
    private UserResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserServiceImpl service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @DisplayName("deve retornar com sucesso o findById")
    @Test
    void testarRetornofindByIdComSucesso() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());

    }

   @DisplayName("deve retornar todos Usuarios DTO")
    @Test
    void testarBuscarTodosUserDTO() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

       ResponseEntity<List<UserDTO>> response = resource.findAll();

       assertNotNull(response);
       assertNotNull(response.getBody());
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(ResponseEntity.class, response.getClass());
       assertEquals(ArrayList.class, response.getBody().getClass());
       assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

       assertEquals(ID, response.getBody().get(INDEX).getId());
       assertEquals(NAME, response.getBody().get(INDEX).getName());
       assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
       assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());

    }

    @DisplayName("deve criar UserDTO")
    @Test
    void testarCriarUserDTO() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response= resource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));


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