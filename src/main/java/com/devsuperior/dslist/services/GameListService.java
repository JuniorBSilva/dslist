package com.devsuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameListService {
	
	@Autowired
	private GameListRepository gameListRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll(){
		List<GameList> result = gameListRepository.findAll();
		List<GameListDTO> dto = result.stream().map(x -> new GameListDTO(x)).toList();
		return dto;
	}
	
	@Transactional
	public void mover(Long listId, int indexOrigem, int indexDestino) {
		
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		
		GameMinProjection obj = list.remove(indexOrigem);
		list.add(indexDestino, obj);
		
		int menorPosicao = indexOrigem < indexDestino ? indexOrigem : indexDestino;
		int maiorPosicao = indexOrigem < indexDestino ? indexDestino : indexOrigem;
		
		for(int i = menorPosicao; i <= maiorPosicao; i++) {
			gameListRepository.atualizaPosicao(listId, list.get(i).getId(), i);
		}
		
	}
	
}
