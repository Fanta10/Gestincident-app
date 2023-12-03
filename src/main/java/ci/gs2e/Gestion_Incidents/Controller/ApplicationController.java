package ci.gs2e.Gestion_Incidents.Controller;

import ci.gs2e.Gestion_Incidents.Modele.Environnement;
import ci.gs2e.Gestion_Incidents.Modele.Logiciel;
import ci.gs2e.Gestion_Incidents.Modele.LogicielPage;
import ci.gs2e.Gestion_Incidents.Modele.LogicielSearchCriteria;
import ci.gs2e.Gestion_Incidents.Service.Application.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/logiciel")
@CrossOrigin(origins = "*",allowedHeaders = "*")
//@AllArgsConstructor
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @PostMapping
    public ResponseEntity<Logiciel> create(@RequestBody Logiciel application){

        return new ResponseEntity<>(applicationService.create(application), HttpStatus.CREATED);
    }
    @PutMapping("/{idApp}")
    public ResponseEntity<Logiciel> edit(@PathVariable int idApp, @RequestBody Logiciel logiciel){

        return new ResponseEntity<Logiciel>(applicationService.edit(idApp, logiciel), HttpStatus.OK);
    }
    @DeleteMapping("/{idApp}")
    public void delete(@PathVariable("idApp") int idApp){
        applicationService.delete(idApp);

    }
    @GetMapping
    public ResponseEntity<List<Logiciel>> getAll(){
        return new ResponseEntity<>(applicationService.listAll(),HttpStatus.OK);
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Logiciel>> getRex(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, LogicielPage logicielPage, LogicielSearchCriteria logicielSearchCriteria){
        logicielPage.setPageNumber(pageNumber);
        logicielPage.setPageSize(pageSize);
        return new ResponseEntity<>(applicationService.getRex(logicielPage,logicielSearchCriteria),
        HttpStatus.OK);
    }


    @GetMapping("/recherche")
    public  ResponseEntity<Optional<Logiciel>> search(@RequestParam(name = "keyword") String libelleApp){
        return  new ResponseEntity<Optional<Logiciel>>(applicationService.listByLibelle(libelleApp),HttpStatus.OK);
    }

}
