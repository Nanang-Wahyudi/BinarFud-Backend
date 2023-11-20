//package binar.challenge7.binarfud_v7.controller;
//
//import binar.challenge7.binarfud_v7.model.Users;
//import binar.challenge7.binarfud_v7.model.enumeration.ERole;
//import binar.challenge7.binarfud_v7.model.response.UsersResponse;
//import binar.challenge7.binarfud_v7.service.impl.UsersServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class UsersControllerTest {
//
//    @InjectMocks
//    private UsersController usersController;
//
//    @Mock
//    private UsersServiceImpl usersService;


//    @Test
//    void deleteUser_testSuccess() {
//        String username = "Sample Username";
//
//        Users users = Users.builder()
//                .userName(username)
//                .email("Sample Email")
//                .password("Sample Password")
//                .build();
//
//        Mockito.when(usersService.findByUsername(username)).thenReturn(Optional.of(users));
//        Mockito.when(usersService.deleteUserByUsername(username)).thenReturn(true);
//
//        ResponseEntity<Object> response = usersController.deleteUser(username);
//
//        Mockito.verify(usersService).findByUsername(username);
//        Mockito.verify(usersService).deleteUserByUsername(username);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void updateUser_TestSuccess() {
//        String newUsername = "Sample New Username";
//        String oldUsername = "Sample Old Username";
//
//        Users updateUsers = Users.builder()
//                .userName(newUsername)
//                .email("Sample Email")
//                .password("Sample Password")
//                .build();
//
//        Mockito.when(usersService.updateUserByUsername(oldUsername, updateUsers)).thenReturn(true);
//
//        ResponseEntity<Object> response = usersController.updateUser(oldUsername, updateUsers);
//
//        Mockito.verify(usersService).updateUserByUsername(oldUsername, updateUsers);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getAllUser_TestSuccess() {
//        List<UsersResponse> responseList = new ArrayList<>();
//
//        UsersResponse usersResponse1 = UsersResponse.builder()
//                .dtoUsername("Sample Username1")
//                .dtoEmail("Sample Email1")
//                .dtoPassword("Sample Password1")
//                .dtoRole(ERole.ADMIN.name())
//                .build();
//
//        UsersResponse usersResponse2 = UsersResponse.builder()
//                .dtoUsername("Sample Username2")
//                .dtoEmail("Sample Email2")
//                .dtoPassword("Sample Password2")
//                .dtoRole(ERole.CUSTOMER.name())
//                .build();
//
//        responseList.add(usersResponse1);
//        responseList.add(usersResponse2);
//
//        Mockito.when(usersService.getAllUser()).thenReturn(responseList);
//
//        ResponseEntity<Object> response = usersController.getAllUser();
//
//        Mockito.verify(usersService).getAllUser();
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getUserDetail_TestSuccess() {
//        String username = "Sample Username";
//
//        UsersResponse usersResponse = UsersResponse.builder()
//                .dtoUsername(username)
//                .dtoEmail("Sample Email")
//                .dtoPassword("Sample Password")
//                .dtoRole(ERole.CUSTOMER.name())
//                .build();
//
//        Mockito.when(usersService.getUserDetailByUsername(username)).thenReturn(usersResponse);
//
//        ResponseEntity<Object> response = usersController.getUserDetail(username);
//
//        Mockito.verify(usersService).getUserDetailByUsername(username);
//
//        Assertions.assertNotNull(response);
//
//        if (usersResponse != null) {
//            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        } else {
//            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//    }

//}
