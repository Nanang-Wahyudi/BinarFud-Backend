//package binar.challenge7.binarfud_v7.service;
//
//import binar.challenge7.binarfud_v7.model.Roles;
//import binar.challenge7.binarfud_v7.model.Users;
//import binar.challenge7.binarfud_v7.model.enumeration.ERole;
//import binar.challenge7.binarfud_v7.model.response.UsersResponse;
//import binar.challenge7.binarfud_v7.repository.UsersRepository;
//import binar.challenge7.binarfud_v7.service.impl.UsersServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class UsersServiceTest {
//
//    @InjectMocks
//    private UsersServiceImpl usersService;
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//
////    @Test
//    void deleteUserByUsername_TestSuccess() {
//        String username = "Sample Username";
//
//        boolean result = usersService.deleteUserByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).deleteUserByUsernameWithQuery(username);
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void deleteUserByUsername_TestFailure() {
//        String username = "Sample Username";
//
//        Mockito.doThrow(new RuntimeException("Simulated Error")).when(usersRepository).deleteUserByUsernameWithQuery(username);
//
//        boolean result = usersService.deleteUserByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).deleteUserByUsernameWithQuery(username);
//
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void updateUserByUsername_TestSuccess() {
//        String oldUsername = "Sample Old Username";
//        Users users = Users.builder()
//                .userName("Sample New Username")
//                .email("Sample Email")
//                .password("Sample Password")
//                .build();
//
//        boolean result = usersService.updateUserByUsername(oldUsername, users);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).updateUserByUsernameWithQuery(oldUsername, users.getUserName(), users.getEmail(), passwordEncoder.encode(users.getPassword()));
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void updateUserByUsername_TestFailure() {
//        String oldUsername = "Sample Username";
//        Users users = Users.builder()
//                .userName("Sample New Username")
//                .email("Sample Email")
//                .build();
//
//        Mockito.doThrow(new RuntimeException("Simulated Error")).when(usersRepository).updateUserByUsernameWithQuery(oldUsername, users.getUserName(), users.getEmail(), users.getPassword());
//
//        boolean result = usersService.updateUserByUsername(oldUsername, users);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).updateUserByUsernameWithQuery(oldUsername, users.getUserName(), users.getEmail(), users.getPassword());
//
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void getAllUser_TestSuccess() {
//        Set<Roles> rolesSet = new HashSet<>();
//        rolesSet.add(Roles.builder()
//                .roleName(ERole.ADMIN)
//                .build());
//
//        Users users1 = Users.builder()
//                .userName("Sample Username1")
//                .email("Sample Email1")
//                .password("Sample Password1")
//                .roles(rolesSet)
//                .build();
//
//        Users users2 = Users.builder()
//                .userName("Sample Username2")
//                .email("Sample Email2")
//                .password("Sample Password2")
//                .roles(rolesSet)
//                .build();
//
//        List<Users> usersList = Arrays.asList(users1, users2);
//
//        Mockito.when(usersRepository.findAll()).thenReturn(usersList);
//
//        List<UsersResponse> responseList = usersService.getAllUser();
//
//        Mockito.verify(usersRepository, Mockito.times(1)).findAll();
//
//        Assertions.assertEquals(2, responseList.size());
//
//        UsersResponse response1 = responseList.get(0);
//        Assertions.assertEquals("Sample Username1", response1.getDtoUsername());
//        Assertions.assertEquals("Sample Email1", response1.getDtoEmail());
//        Assertions.assertEquals("Sample Password1", response1.getDtoPassword());
//        Assertions.assertEquals("[Roles(roleId=null, roleName=ADMIN)]", response1.getDtoRole());
//
//        UsersResponse response2 = responseList.get(1);
//        Assertions.assertEquals("Sample Username2", response2.getDtoUsername());
//        Assertions.assertEquals("Sample Email2", response2.getDtoEmail());
//        Assertions.assertEquals("Sample Password2", response2.getDtoPassword());
//        Assertions.assertEquals("[Roles(roleId=null, roleName=ADMIN)]", response2.getDtoRole());
//    }
//
//    @Test
//    void getUserDetailByUsername_TestFound() {
//        Set<Roles> rolesSet = new HashSet<>();
//        rolesSet.add(Roles.builder()
//                .roleName(ERole.ADMIN)
//                .build());
//
//        String username = "Sample Username";
//        Users users = Users.builder()
//                .userName(username)
//                .email("Sample Email")
//                .password("Sample Password")
//                .roles(rolesSet)
//                .build();
//
//        Mockito.when(usersRepository.findByUserName(username)).thenReturn(Optional.ofNullable(users));
//
//        UsersResponse response = usersService.getUserDetailByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).findByUserName(username);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(username, response.getDtoUsername());
//        Assertions.assertEquals("Sample Email", response.getDtoEmail());
//        Assertions.assertEquals("Sample Password", response.getDtoPassword());
//        Assertions.assertEquals("[Roles(roleId=null, roleName=ADMIN)]", response.getDtoRole());
//    }
//
//    @Test
//    void getUserDetailByUsername_TestNotFound() {
//        String username = "Non-Existent User";
//
//        Mockito.when(usersRepository.findByUserName(username)).thenReturn(Optional.empty());
//
//        UsersResponse response = usersService.getUserDetailByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).findByUserName(username);
//
//        Assertions.assertNull(response);
//    }
//
//    @Test
//    void findByUsername_TestFound() {
//        String username = "Sample Username";
//
//        Users users = Users.builder()
//                .userName(username)
//                .email("Sample Email")
//                .password("Sample Password")
//                .build();
//
//        Mockito.when(usersRepository.findByUserName(username)).thenReturn(Optional.ofNullable(users));
//
//        Optional<Users> result = usersService.findByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).findByUserName(username);
//
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(username, result.get().getUserName());
//    }
//
//    @Test
//    void findByUsername_TestNotFound() {
//        String username = "Non-Existent User";
//
//        Mockito.when(usersRepository.findByUserName(username)).thenReturn(Optional.empty());
//
//        Optional<Users> result = usersService.findByUsername(username);
//
//        Mockito.verify(usersRepository, Mockito.times(1)).findByUserName(username);
//
//        Assertions.assertFalse(result.isPresent());
//    }

//}
