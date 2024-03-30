package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.ClientDTO;
import com.uncledemy.salesmanagementsystem.dto.ClientDto;
import com.uncledemy.salesmanagementsystem.dto.ClientReportDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Authority;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.ClientRepository;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    @Override
    public void addClient(Long userId,ClientDto clientDto) throws InvalidPhoneNumberException {
        User user = userRepository.findById(userId).orElseThrow(()->
                new UsernameNotFoundException("Invalid User"));
        if (!isValidPhoneNumber(clientDto.getPhoneNumber())){
            throw new InvalidPhoneNumberException("The Phone number is not valid");
        }
        Client newClient = new Client();
        newClient.setFirstName(clientDto.getFirstName());
        newClient.setLastName(clientDto.getLastName());
        newClient.setEmail(clientDto.getEmail());
        newClient.setPhoneNumber(clientDto.getPhoneNumber());
        newClient.setAuthority(Authority.valueOf(clientDto.getAuthority()));
        newClient.setAddress(clientDto.getAddress());
        newClient.setTotalSpent(BigDecimal.ZERO);
        logger.info("{} {} was registered by {}", newClient.getFirstName(),newClient.getLastName(),user.getUsername() );
        clientRepository.save(newClient);
    }

    @Override
    public boolean updateClient(String phoneNumber, ClientDto clientDto) throws SalesManagementException, InvalidPhoneNumberException {
        Client savedClient = clientRepository.findByPhoneNumber(phoneNumber).
                orElseThrow(()-> new SalesManagementException("Client with this phone number does not exist"));
        if (!isValidPhoneNumber(clientDto.getPhoneNumber())){
            throw new InvalidPhoneNumberException("The Phone number is not valid");
        }
        savedClient.setFirstName(clientDto.getFirstName());
        savedClient.setLastName(clientDto.getLastName());
        savedClient.setEmail(clientDto.getEmail());
        savedClient.setPhoneNumber(clientDto.getPhoneNumber());
        savedClient.setAddress(clientDto.getAddress());
        clientRepository.save(savedClient);
        logger.info("{} {} details was updated ", savedClient.getFirstName(),savedClient.getLastName());

        return true;
    }

    @Override
    public Client findClientByPhoneNumber(String phoneNumber) throws SalesManagementException {
        return clientRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new SalesManagementException("Client with this phone number does not exist"));
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public ClientReportDto generateClientReport() {
        ClientReportDto clientReport = new ClientReportDto();

        List<Client> allClients = clientRepository.findAll();

        clientReport.setTotalClients(allClients.size());

        List<ClientDTO> topSpendingClients = allClients.stream()
                .sorted(Comparator.comparing(Client::getTotalSpent).reversed())
                .map(client -> {
                    ClientDTO clientDTO = new ClientDTO();
                    clientDTO.setId(client.getId());
                    clientDTO.setFirstName(client.getFirstName());
                    clientDTO.setLastName(client.getLastName());
                    clientDTO.setTotalSpent(client.getTotalSpent());
                    return clientDTO;
                })
                .limit(10)
                .collect(Collectors.toList());
        clientReport.setTopSpendingClients(topSpendingClients);

        Map<String, Integer> clientActivity = new HashMap<>();
        allClients.forEach(client -> {
            String lastInteractionMonth = client.getLastPurchaseDate().getMonth().toString();
            clientActivity.put(lastInteractionMonth, clientActivity.getOrDefault(lastInteractionMonth, 0) + 1);
        });
        clientReport.setClientActivity(clientActivity);

        Map<String, Integer> locationStatistics = new HashMap<>();
        allClients.forEach(client -> {
            locationStatistics.put(client.getAddress(), locationStatistics.getOrDefault(client.getAddress(), 0) + 1);
        });
        clientReport.setLocationStatistics(locationStatistics);
        logger.info("Client Report was viewed by an ADMIN");
        return clientReport;
    }

    @Override
    public boolean deleteClient(String phoneNumber) throws SalesManagementException {
        Client client = findClientByPhoneNumber(phoneNumber);
        logger.info("Client {} {}'s record as been deleted. ", client.getFirstName(), client.getLastName());
        clientRepository.delete(client);

        return true;
    }



    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression to match phone numbers containing only digits and '+'
        String regex = "^[0-9+]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


}
