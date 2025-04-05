package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    public Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
        koalas.put(1,new Koala(1, "ahmet", 30.0, 15, "male"));

    }

    @GetMapping
    public List<Koala> getAll(){
        return this.koalas.values().stream().toList();
    }

    @GetMapping("{id}")
    public Koala getKoala(@PathVariable("id") int id){
        checkValidity(id);
        Koala k = this.koalas.get(id);
        return k;
    }

    @PostMapping
    public Koala addKoala(@RequestBody Koala koala){
        checkObjectValidity(koala);
        this.koalas.put(koala.getId(), koala);
        return this.koalas.get(koala.getId());
    }

    @PutMapping("{id}")
    public Koala updateKoala(@PathVariable("id") int id, @RequestBody Koala koala){
        checkValidity(id);
        this.koalas.put(koala.getId(), koala);
        return this.koalas.get(id);
    }

    @DeleteMapping("{id}")
    public void deleteKoala(@PathVariable("id") int id){
        checkValidity(id);
        this.koalas.remove(id);
    }

    private void checkValidity(int id){
        if(id<=0 || (Integer) id==null)
            throw new ZooException("Invalid id", HttpStatus.BAD_REQUEST);
        if(!this.koalas.containsKey(id))
            throw new ZooException("No such koala", HttpStatus.NOT_FOUND);
    }

    private void checkObjectValidity(Koala koala){
        if(koala.getName()==null || koala.getWeight()<=0 || koala.getGender()==null || koala.getSleepHour()<=0)
            throw  new ZooException("Invalid request", HttpStatus.BAD_REQUEST);
    }

}
