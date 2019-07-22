package info.cafeit.accountotp.repository;


import info.cafeit.accountotp.model.SmsCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCodes, Integer> {

    @Query("select sms from SmsCodes sms join sms.user u where u.id = ?1")
    SmsCodes findByUserId(Integer user);

}
