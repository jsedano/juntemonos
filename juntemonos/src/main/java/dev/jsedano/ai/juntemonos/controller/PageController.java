package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.dto.CommunityDTO;
import dev.jsedano.ai.juntemonos.service.JuntemonosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/juntemonos")
@RequiredArgsConstructor
@Slf4j
public class PageController {

  private final JuntemonosService juntemonosService;

  @RequestMapping("/")
  public String showCommunities(Model model) {
    model.addAttribute("communities", juntemonosService.getCommunities());
    return "communities";
  }

  @RequestMapping("/community/{communityName}")
  public String showCommunity(
      Model model, @PathVariable(value = "communityName") String communityName) {
    log.info(communityName);
    model.addAttribute(
        "community",
        juntemonosService.showCommunity(communityName).orElse(CommunityDTO.builder().build()));
    return "community";
  }
}
