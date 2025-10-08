package com.example.gujeuck_server.domain.log.entity;

<<<<<<< HEAD
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
=======
import com.example.gujeuck_server.domain.admin.dto.CompanionDto;
import com.example.gujeuck_server.domain.user.entity.User;
>>>>>>> origin/feat/admin-CreateUseList
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> origin/feat/admin-CreateUseList

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    private String name;
    private String age;
    private String phone;
=======
    @Column(length = 30, nullable = false)
    private String leaderName;

    @Column(length = 10, nullable = false)
    private String leaderAge;

    private String purpose;

    private List<CompanionDto> companionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User companion;
>>>>>>> origin/feat/admin-CreateUseList

    @Column(name = "male_count", nullable = false)
    private int maleCount;

    @Column(name = "female_count", nullable = false)
    private int femaleCount;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purpose_id", nullable = false)
    private Purpose purpose;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

=======
    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    private boolean privacyAgreed;

>>>>>>> origin/feat/admin-CreateUseList
    @PrePersist
    public void prePersist() {
        if(visitDate == null) {
            visitDate = LocalDate.now();
        }
    }
}

