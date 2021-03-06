package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.PersonDTO;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping
    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        PersonDTO personToSave = PersonDTO.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .birthDate(personDTO.getBirthDate())
                .phones(personDTO.getPhones())
                .build();

        PersonDTO savedPerson = personRepository.save(personDTO);
        return MessageResponseDTO
                .builder()
                .message("Created personDTO with ID " + savedPerson.getId())
                .build();
    }

    public List<PersonDTO> listAll() {
        List<PersonDTO> allPeople = personRepository.findAll();
        return (List<PersonDTO>) allPeople.stream();
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return PersonDTO.builder().build();
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);

        personRepository.deleteById(id);

    }

    private PersonDTO verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
       verifyIfExists(id);

        return MessageResponseDTO
                .builder()
                .message("Updated person with ID")
                .build();

    }
}
