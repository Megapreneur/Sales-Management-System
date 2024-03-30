package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.ClientDto;
import com.uncledemy.salesmanagementsystem.dto.ClientReportDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Client;

import java.util.List;

public interface ClientService {
    void addClient(Long userId, ClientDto clientDto) throws InvalidPhoneNumberException;
    boolean updateClient(String phoneNumber, ClientDto clientDto) throws SalesManagementException, InvalidPhoneNumberException;
    Client findClientByPhoneNumber(String phoneNumber) throws SalesManagementException;
    List<Client> getAllClient();
    ClientReportDto generateClientReport();
    boolean deleteClient(String phoneNumber) throws SalesManagementException;
}
