package de.lordlazor.bachelorarbeit.controller;


import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GetController {


  private final ControllerUtilities controllerUtilities;

  public GetController(ControllerUtilities controllerUtilities) {
    this.controllerUtilities = controllerUtilities;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/upload/file")
  public String upload(Model model) {
    model.addAttribute("type", "file");
    return "upload";
  }

  @GetMapping("/upload/folder")
  public String uploadFolder(Model model) {
    model.addAttribute("type", "folder");
    return "upload";
  }

  @GetMapping("/view")
  public String view(Model model){
    model.addAttribute("filenames", controllerUtilities.allFiles());
    return "view";
  }

}
