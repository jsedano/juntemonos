package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/juntemonos")
@RequiredArgsConstructor
@Slf4j
public class PageController {

  @Autowired private CommunityRepository communityRepository;

  @RequestMapping("/")
  public String showCommunities(Model model) {
    model.addAttribute("communities", communityRepository.findAll());
    return "communities";
  }

  @RequestMapping("/community/{communityName}")
  public String showCommunity(
      Model model, @PathVariable(value = "communityName") String communityName) {
    log.info(communityName);
    model.addAttribute("community", communityRepository.findByName(communityName));
    return "community";
  }
}
