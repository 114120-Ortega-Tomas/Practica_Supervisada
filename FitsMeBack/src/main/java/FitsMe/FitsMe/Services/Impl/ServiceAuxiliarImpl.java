package FitsMe.FitsMe.Services.Impl;



import FitsMe.FitsMe.Dtos.UsuariosDtos.LogInRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioRequest;
import FitsMe.FitsMe.Dtos.UsuariosDtos.UsuarioResponse;
import FitsMe.FitsMe.Entities.*;
import FitsMe.FitsMe.Repositories.*;
import FitsMe.FitsMe.Services.ServiceAuxiliar;
import FitsMe.FitsMe.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceAuxiliarImpl implements ServiceAuxiliar {

    @Autowired
    private EstilosJpaRepository estilosJpaRepository;
    @Autowired
    private TipoPrendaJpaRepository tipoPrendaJpaRepository;
    @Autowired
    private PrendaJpaRepository prendaJpaRepository;
    @Autowired
    private ClimaJpaRepository climaJpaRepository;
    @Autowired
    private ColorJpaRepository colorJpaRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ClimaEntity> getAllClimas() {
        List<ClimaEntity> climas = climaJpaRepository.findAll();
        return climas;
    }

    @Override
    public List<EstilosEntity> getAllEstilos() {
        List<EstilosEntity> estilos = estilosJpaRepository.findAll();
        return estilos;
    }

    @Override
    public List<ColorEntity> getAllColores() {
        List<ColorEntity> colores = colorJpaRepository.findAll();
        return colores;
    }

    @Override
    public List<TipoPrendaEntity> getAllTipoPrendas() {
        List<TipoPrendaEntity> tipoPrendas = tipoPrendaJpaRepository.findAll();
        return tipoPrendas;
    }
}
