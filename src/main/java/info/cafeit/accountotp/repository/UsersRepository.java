package info.cafeit.accountotp.repository;


import info.cafeit.accountotp.model.SmsCodes;
import info.cafeit.accountotp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {


    @Query("select u from Users u where u.mobile = ?1")
    Users findUserbyPhoneNumber(String phoneNum);

}
