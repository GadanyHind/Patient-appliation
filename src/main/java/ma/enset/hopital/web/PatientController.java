package ma.enset.hopital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.hopital.entities.Patient;
import ma.enset.hopital.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
@GetMapping("/user/index")
    public String index(Model model, @RequestParam(name="p",defaultValue ="0") int page ,
                        @RequestParam(name="s",defaultValue ="5") int size,
                        @RequestParam(name="keyword",defaultValue ="") String kw){
    Page<Patient> patientpage=patientRepository.findByNomContains(kw,PageRequest.of(page,size ));
    model.addAttribute("ListePatients",patientpage.getContent());
    model.addAttribute("currentpage",page);
    model.addAttribute("pages",new int[patientpage.getTotalPages()]);
    model.addAttribute("keyword",kw);
        return "patients";
    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword , int page){
       patientRepository.deleteById(id);
       return "redirect:/user/index?p="+page+"&keyword="+keyword;

    }
    @GetMapping("/")
    public String home(){
      return "redirect:/user/index";
    }
    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public  String formPatients(Model model){
       model.addAttribute("patient",new Patient());
       return "formPatients";
    }
     @PostMapping(path = "/admin/save")
     @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(defaultValue = "") String keyword,@RequestParam(defaultValue = "0")int page){
    if (bindingResult.hasErrors()) return "formPatients";
      patientRepository.save(patient);
      return "redirect:/user/index?p="+page+"&keyword="+keyword;
     }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public  String editPatient(Model model, Long id,String keyword,int page){
    Patient patient=patientRepository.findById(id).orElse(null);
    if (patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}

