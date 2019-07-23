package info.cafeit.accountotp.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "sms_codes")
@ToString
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public boolean equalsCode(String code) {
        return code.equals(this.code);
    }

}
