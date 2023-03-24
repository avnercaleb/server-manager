package com.avner.serves.services.implementation;

import com.avner.serves.enums.Status;
import com.avner.serves.models.Server;
import com.avner.serves.repos.ServerRepo;
import com.avner.serves.services.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    @Autowired
    private final ServerRepo repo;

    @Override
    public Server create(Server server) {
        log.info("Salvando um novo servidor: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return repo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pingando servidor IP: {}", ipAddress);
        Server server = repo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        return repo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Listando todos os servidores");
        return repo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Selecionando servidor com ID: {}", id);
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Server update(Server server) {
        log.info("Atualizando o servidor: {}", server.getName());
        return repo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deletando o servidor de ID: {}", id);
        repo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] images = {"server1.png", "server2.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + images[new Random().nextInt(2)])
                .toUriString();
    }
}
