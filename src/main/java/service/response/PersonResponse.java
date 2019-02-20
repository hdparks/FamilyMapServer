package service.response;

import domain.Person;

import java.util.List;

/**
 * represents a response to a request made to the /person endpoint
 */
public class PersonResponse extends Response{

    /**
     * A list of Person objects
     */
    public List<Person> persons;

    /**
     * Creates a successful PersonResponse object
     * @param persons the Person objects which will be returned
     */
    public PersonResponse(List<Person> persons){
        super(true);
        this.persons = persons;
    }

    /**
     * Creates a failing PersonResponse object with a description of what failed
     * @param errorMessage a message detailing how the Person service failed
     */
    public PersonResponse(String errorMessage){
        super(errorMessage,false);
    }

}
