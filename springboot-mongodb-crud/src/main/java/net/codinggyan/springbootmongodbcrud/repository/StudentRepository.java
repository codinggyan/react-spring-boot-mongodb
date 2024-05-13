package net.codinggyan.springbootmongodbcrud.repository;

import net.codinggyan.springbootmongodbcrud.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

}
