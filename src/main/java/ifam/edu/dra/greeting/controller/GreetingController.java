package ifam.edu.dra.greeting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifam.edu.dra.greeting.model.Greeting;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	List<Greeting> greeting = new ArrayList<>();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Greeting newGreeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
		greeting.add(newGreeting);
		return newGreeting;

	}

	@GetMapping("/greetings")
	public List<Greeting> getGreetings() {
		return greeting;
	}
	
	private boolean isValidIndex(int id) {
		return id >= 0 && id < greeting.size();
	}

	@GetMapping("/greetings/{id}")
	public Greeting getGreetingById(@PathVariable int id) {

		if (isValidIndex(id)) {
			return greeting.get(id);
		} else {
			throw new IndexOutOfBoundsException("Índice Inválido");
		}

	}

	@PutMapping("/greetings/update/{id}")
	public Greeting updateGreetingById(@PathVariable int id, @RequestBody String newName) {
		if (isValidIndex(id)) {
			Greeting greetingToUpdate = greeting.get((int) id);
			greetingToUpdate.setName(newName);
			return greetingToUpdate;
		} else {
			throw new IndexOutOfBoundsException("Índice Inválido");
		}
	}
	
	@DeleteMapping("/greetings/delete/{id}")
	public String deleteGreetingById(@PathVariable int id) {
	    if (isValidIndex(id)) {
	        greeting.remove((int) id);
	        return "Deletado a saudação com o ID: " + id;
	    } else {
	        throw new IndexOutOfBoundsException("Índice Inválido");
	    }
	}
}
