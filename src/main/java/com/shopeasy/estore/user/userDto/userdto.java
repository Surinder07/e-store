package com.shopeasy.estore.user.userDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="userdto")
public class userdto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public Long userId;
    public String firstname;
    public String lastname;
    public String email;
}
