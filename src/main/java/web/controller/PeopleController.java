package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.PersonDao;
import web.model.Person;

import javax.validation.Valid;

@Controller
//@RequestMapping("/people")

public class PeopleController {

    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping()
    public String indexOfAllModel(Model model) {

        model.addAttribute("allPeople", personDao.upindex());
        return "people/peoples";
    }

    @GetMapping("/{id}")
    public String showId(@PathVariable("id") int id, Model model) {
        model.addAttribute("showPerson", personDao.show(id));
        return "people/show";

    }

    @GetMapping("/new")
    public String newPerson(Model model) {

        model.addAttribute("personCreated", new Person());

        return"people/new";
}


    @PostMapping()
    public String create(@ModelAttribute("personCreated") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.save(person);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("personEdit", personDao.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("personEdit") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDao.update(id, person);
        return "redirect:/";
    }

@DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/";
    }

}
