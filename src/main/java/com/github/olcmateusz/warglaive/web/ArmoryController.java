package com.github.olcmateusz.warglaive.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.dto.PlayableCharacterDto;
import com.github.olcmateusz.warglaive.service.PlayableCharacterService;

@Controller
@RequestMapping("/armory")
public class ArmoryController {
	
	private PlayableCharacterService playableCharacterServcie;
	

	public ArmoryController(PlayableCharacterService playableCharacterServcie) {
		this.playableCharacterServcie = playableCharacterServcie;
	}


	@GetMapping("")
	public String armoryMain(ModelMap model) {
		
		List<PlayableCharacter> characters = playableCharacterServcie.getAll();
		
		List<PlayableCharacterDto> charactersDto = characters.stream()
				.map(character -> new PlayableCharacterDto(character.getId(), character.getName(), character.getRealm().getName()))
				.collect(Collectors.toList());
		

		model.put("characters", charactersDto);
		return "armory";
	}
	
	@GetMapping("characters/{characterId}")
	public String playerInfo(@PathVariable Long characterId, ModelMap model) {
		Optional<PlayableCharacter> characterOpt = playableCharacterServcie.getById(characterId);
		if (characterOpt.isPresent()) {
			PlayableCharacter character = characterOpt.get();
			model.put("character", character);
			
		}else {
			//TODO
			//error when no playableCharacter found
		}
		return "character";
	}
	
	@PostMapping("")
	public String searchPlayer(@RequestParam Long id) {
		return "redirect:/armory/characters/" + id;
	}
	
	
}
