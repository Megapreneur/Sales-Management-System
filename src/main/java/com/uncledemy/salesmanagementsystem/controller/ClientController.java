package com.uncledemy.salesmanagementsystem.controller;

import com.uncledemy.salesmanagementsystem.constants.StatusConstant;
import com.uncledemy.salesmanagementsystem.dto.ClientDto;
import com.uncledemy.salesmanagementsystem.dto.ClientReportDto;
import com.uncledemy.salesmanagementsystem.dto.ResponseDto;
import com.uncledemy.salesmanagementsystem.dto.StaffDto;
import com.uncledemy.salesmanagementsystem.exception.*;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createClient(@Valid @RequestParam Long userId,@Valid @RequestBody ClientDto clientDto) throws InvalidPhoneNumberException {
        clientService.addClient(userId,clientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201));
    }
    @GetMapping("/client")
    public ResponseEntity<Client> findAClient(@RequestParam String phoneNumber) throws SalesManagementException {
        Client client = clientService.findClientByPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateClient(@Valid @RequestParam String phoneNumber, @Valid @RequestBody ClientDto clientDto) throws SalesManagementException, InvalidPhoneNumberException {
        boolean isUpdated = clientService.updateClient(phoneNumber, clientDto);
        if (isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_UPDATE));
        }
    }
    @GetMapping("/allClients")
    public ResponseEntity<List<Client>> getAllClient(){
        List<Client> clients = clientService.getAllClient();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }
    @GetMapping("/reports/client")
    public ResponseEntity<ClientReportDto> getClientReport() {
        ClientReportDto clientReport = clientService.generateClientReport();
        return ResponseEntity.status(HttpStatus.OK).body(clientReport);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteClient(@RequestParam String phoneNumber) throws SalesManagementException {
        boolean isDeleted = clientService.deleteClient(phoneNumber);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_DELETE));
        }
    }


}
