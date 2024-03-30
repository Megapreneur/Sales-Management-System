package com.uncledemy.salesmanagementsystem.clientServiceImplTest;

import com.uncledemy.salesmanagementsystem.dto.ClientDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Authority;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.ClientRepository;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import com.uncledemy.salesmanagementsystem.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addClient_ValidClient_Success() throws InvalidPhoneNumberException {
        User user = new User();
        user.setId(1L);
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setPhoneNumber("+1234567890");
        clientDto.setAuthority(Authority.ADMIN.name());
        clientDto.setAddress("123 Street, City");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(clientRepository.save(any())).thenReturn(new Client());
        assertDoesNotThrow(() -> clientService.addClient(1L, clientDto));
    }

    @Test
    void addClient_InvalidPhoneNumber_ThrowsInvalidPhoneNumberException() {
        User user = new User();
        user.setId(1L);
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("John");
        clientDto.setLastName("Doe");
        clientDto.setEmail("john.doe@example.com");
        clientDto.setPhoneNumber("1234567890"); // Invalid phone number without '+'
        clientDto.setAuthority(Authority.ADMIN.name());
        clientDto.setAddress("123 Street, City");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        assertThrows(InvalidPhoneNumberException.class, () -> clientService.addClient(1L, clientDto));
    }

    @Test
    void updateClient_ValidPhoneNumber_Success() throws SalesManagementException, InvalidPhoneNumberException {
        Client client = new Client();
        client.setPhoneNumber("+1234567890");
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("UpdatedFirstName");
        clientDto.setLastName("UpdatedLastName");
        clientDto.setEmail("updated.email@example.com");
        clientDto.setPhoneNumber("+1234567890");
        clientDto.setAddress("Updated Address");
        when(clientRepository.findByPhoneNumber("+1234567890")).thenReturn(java.util.Optional.of(client));
        assertTrue(clientService.updateClient("+1234567890", clientDto));
    }

    @Test
    void updateClient_InvalidPhoneNumber_ThrowsInvalidPhoneNumberException() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("UpdatedFirstName");
        clientDto.setLastName("UpdatedLastName");
        clientDto.setEmail("updated.email@example.com");
        clientDto.setPhoneNumber("1234567890"); // Invalid phone number without '+'
        clientDto.setAddress("Updated Address");
        assertThrows(InvalidPhoneNumberException.class, () -> clientService.updateClient("+1234567890", clientDto));
    }

    @Test
    void findClientByPhoneNumber_ValidPhoneNumber_ReturnsClient() throws SalesManagementException {
        Client client = new Client();
        client.setPhoneNumber("+1234567890");
        when(clientRepository.findByPhoneNumber("+1234567890")).thenReturn(java.util.Optional.of(client));
        assertNotNull(clientService.findClientByPhoneNumber("+1234567890"));
    }

    @Test
    void findClientByPhoneNumber_InvalidPhoneNumber_ThrowsSalesManagementException() {
        when(clientRepository.findByPhoneNumber("+1234567890")).thenReturn(java.util.Optional.empty());
        assertThrows(SalesManagementException.class, () -> clientService.findClientByPhoneNumber("+1234567890"));
    }

    @Test
    void getAllClient_ReturnsListOfClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        when(clientRepository.findAll()).thenReturn(clients);
        assertEquals(clients, clientService.getAllClient());
    }

}