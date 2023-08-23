package com.example.homebanking.controllers;

import com.example.homebanking.configurations.WebAuthentication;
import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path="/clients", method = RequestMethod.POST)
    public ResponseEntity<Client> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password){

                if (firstName.isEmpty()|| lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
                    return new ResponseEntity("Missing data", HttpStatus.FORBIDDEN);
                }

                if (clientRepository.findByEmail(email) != null){
                    return new ResponseEntity("Email ya existente", HttpStatus.FORBIDDEN);
                }

                clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
                return new ResponseEntity(HttpStatus.CREATED);
            }


    @RequestMapping("clients")
    public List<ClientDTO> getClients(){

        List<Client> listClient = clientRepository.findAll();
        List<ClientDTO> listClientDTO = listClient
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
        return listClientDTO;
    }


    @RequestMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));

    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }
}
