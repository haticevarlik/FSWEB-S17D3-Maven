package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
        kangaroos.put(1,new Kangaroo(1, "alice", 30.0, 8, "female", false));

    }

    @GetMapping
    public List<Kangaroo> getAll(){
        return this.kangaroos.values().stream().toList();
    }

    @GetMapping("{id}")
    public Kangaroo getKangaroo(@PathVariable("id") int id){
        checkValidity(id);
        Kangaroo k = this.kangaroos.get(id);
        return k;
    }

    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo){
        checkObjectValidity(kangaroo);
        this.kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("{id}")
    public Kangaroo updateKangaroo(@PathVariable("id") int id, @RequestBody Kangaroo kangaroo){
        checkValidity(id);
        kangaroo.setId(id);
        this.kangaroos.put(kangaroo.getId(), kangaroo);
        return this.kangaroos.get(id);
    }

    @DeleteMapping("{id}")
    public Kangaroo deleteKangaroo(@PathVariable("id") int id){
        checkValidity(id);
        Kangaroo deletedKangaroo = kangaroos.get(id);
        this.kangaroos.remove(id);
        return deletedKangaroo;
    }

    private void checkValidity(int id){
        if(id<=0 || (Integer) id==null)
            throw new ZooException("Invalid id", HttpStatus.BAD_REQUEST);
        if(!this.kangaroos.containsKey(id))
            throw new ZooException("No such koala", HttpStatus.NOT_FOUND);
    }

    private void checkObjectValidity(Kangaroo kangaroo){
        if(kangaroo.getName()==null || kangaroo.getHeight()<=0 || kangaroo.getWeight()<=0 || kangaroo.getGender()==null)
            throw  new ZooException("Invalid request", HttpStatus.BAD_REQUEST);
    }

}
